package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.*;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.HighLightBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.UserQuery;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.handler.EsConditionHandle;
import org.pippi.elasticsearch.helper.core.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @date     2021/7/18
 * @author    JohenTeng
 **/
public class QueryAnnParser {

    private static final String BASE_FILED = "value";

    private volatile static QueryAnnParser INSTANCE ;

    private QueryAnnParser(){}

    public static QueryAnnParser instance(){
        if (INSTANCE == null) {
            synchronized (QueryAnnParser.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QueryAnnParser();
                    return INSTANCE;
                }
            }
        }
        return INSTANCE;
    }

    /**
     *  parse Index-information
     * @param view
     * return
     */
    public EsQueryIndexBean getIndex(Object view) {
        Class<?> clazz = view.getClass();
        EsQueryIndex ann = clazz.getAnnotation(EsQueryIndex.class);
        if (ann == null) {
            throw new EsHelperQueryException("undefine query-index @EsQueryIndex");
        }
        String index = ann.index();
        QueryModel model = ann.model();
        String[] fetchFields = ann.fetch();
        String[] excludeFields = ann.exclude();
        float minScore = ann.minScore();
        boolean traceScore = ann.traceScore();
        EsQueryIndexBean indexBean = new EsQueryIndexBean(index, model, fetchFields, excludeFields, minScore, traceScore);
        HighLight highLightAnn = clazz.getAnnotation(HighLight.class);
        if (Objects.nonNull(highLightAnn)) {
            indexBean.setHighLight(HighLightBean.phrase(highLightAnn));
        }
        return indexBean;
    }

    /**
     *  read query-view-pojo define by user,
     *  support java-base type, collections and
     *  @link
     *
     * @param view
     * @param visitParent
     * return
     */
    public List<EsQueryFieldBean> read(Object view, boolean visitParent) {
        Class<?> clazz = view.getClass();
        List<Field> fieldList = this.getFields(clazz, visitParent);
        List<EsQueryFieldBean> queryDesList = Lists.newArrayListWithCapacity(fieldList.size());
        for (Field field : fieldList) {
            Set<Annotation> annotationSet = Arrays.stream(field.getAnnotations())
                    .filter(ann -> ann.annotationType().isAnnotationPresent(Query.class))
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(annotationSet) && checkEsCondition(field, view) ){
                List<EsQueryFieldBean> queryDes = this.mapFieldAnn(field, view, annotationSet);
                queryDesList.addAll(queryDes);
            }
        }
        return queryDesList;
    }


    private boolean checkEsCondition(Field field, Object view) {
        Optional<Annotation> optionCondition = Arrays.stream(field.getAnnotations())
                .filter(ann -> ann.annotationType().equals(EsCondition.class))
                .findAny();
        if (!optionCondition.isPresent()) {
            return true;
        }
        EsCondition condition  = (EsCondition) optionCondition.get();
        Class<? extends EsConditionHandle> conditionHandleClazz = condition.value();
        EsConditionHandle conditionHandle = ReflectionUtils.newInstance(conditionHandleClazz);
        Object val = ReflectionUtils.getFieldValue(field, view);
        if (Objects.isNull(val)) {
            return false;
        }
        return conditionHandle.test(val);
    }

    private List<Field> getFields(Class<?> clazz, boolean visitParent) {
        if (visitParent) {
            return getFields(clazz, Lists.newArrayList());
        }
        Field[] fieldArr = clazz.getDeclaredFields();
        return Lists.newArrayList(fieldArr);
    }

    private List<Field> getFields(Class<?> clazz, List<Field> callBackList) {
        Field[] fieldArr = clazz.getDeclaredFields();
        callBackList.addAll(Arrays.asList(fieldArr));
        if (!(clazz = clazz.getSuperclass()).equals(Object.class)) {
            getFields(clazz, callBackList);
        }
        return callBackList;
    }

    private List<EsQueryFieldBean> mapFieldAnn(Field field, Object viewObj, Set<Annotation> annotationSet) {
        final List<EsQueryFieldBean> res = Lists.newArrayList();
        for (Annotation ann : annotationSet) {
            Optional.ofNullable(parseValue(field, viewObj)).ifPresent(queryDes ->{
                this.parseAnn(queryDes, field, ann);
                res.add(queryDes);
            });
        }
        return res;
    }

    private EsQueryFieldBean parseValue(Field field, Object viewObj){
        try {
            EsQueryFieldBean queryDes = new EsQueryFieldBean<>();
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            Object val = field.get(viewObj);
            if (Objects.isNull(val)) {
                return null;
            }
            if (ReflectionUtils.isBaseType(fieldType) || val instanceof EsComplexParam || checkCollectionValueType(field, val)) {
                queryDes.setValue(val);
            } else {
                throw new EsHelperQueryException("config @EsQueryField at an Error-Type Field, Just support Primitive-type (exclude void.class) or their Decorate-type or Collection or Map or EsComplexParam");
            }
            return queryDes;
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("unable reach target field ", e);
        }
    }

    private boolean checkCollectionValueType (Field field, Object val) {
        Predicate<Field> checkCollectionTypePredicate = f -> {
            ParameterizedType genericType = (ParameterizedType) f.getGenericType();
            Type[] actualType = genericType.getActualTypeArguments();
            return actualType.length == 1 && ReflectionUtils.isBaseType(actualType[0].getClass());
        };
        return (val instanceof Collection) && checkCollectionTypePredicate.test(field);
    }

    private void parseAnn(EsQueryFieldBean queryDes, Field field, Annotation targetAnn) {
        try {
            queryDes.setExtAnnotation(targetAnn);
            Method baseMethod = targetAnn.getClass().getDeclaredMethod(BASE_FILED);
            Base ann = (Base) baseMethod.invoke(targetAnn);
            EsConnector esConnector = ann.connect();
            queryDes.setLogicConnector(esConnector);
            String column = ann.name();
            if (StringUtils.isBlank(column)) {
                column = field.getName();
            }
            queryDes.setField(column);
            String queryType = ann.queryType();
            if (StringUtils.isBlank(queryType)) {
                queryType =  targetAnn.annotationType().getSimpleName();
            }
            // todo 用户自定义扩展逻辑有问题
            if (StringUtils.isBlank(queryType) || StringUtils.equals(queryType, UserQuery.class.getSimpleName()) ) {
                throw new EsHelperQueryException("QUERY-TYPE missing, it's necessary");
            }
            queryDes.setQueryType(queryType);

            String meta = ann.metaStringify();
            if (StringUtils.isBlank(meta)) {
                meta =  ann.meta().getType();
            }
            queryDes.setMeta(meta);
            queryDes.setBoost(ann.boost());
        } catch (Exception e) {
            throw new EsHelperConfigException("annotation analysis Error, cause:", e);
        }
    }



}
