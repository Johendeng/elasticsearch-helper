package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.HighLight;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.HighLightBean;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.utils.TypeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @project: elasticsearch-helper
 * @package: org.pippi.elasticsearch.helper.view
 * @date:    2021/7/18
 * @Author:  JohenTeng
 * @email: 1078481395@qq.com
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
     * @return
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
        EsQueryIndexBean indexBean = new EsQueryIndexBean(index, model, fetchFields, excludeFields, minScore);
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
     * @return
     */
    public List<EsQueryFieldBean> read(Object view, boolean visitParent) {
        Class<?> clazz = view.getClass();

        List<Field> fieldList = this.getFields(clazz, visitParent);
        List<EsQueryFieldBean> queryDesList = Lists.newArrayListWithCapacity(fieldList.size());
        for (Field field : fieldList) {
            Set<Annotation> annotationSet = Arrays.stream(field.getAnnotations())
                    .filter(ann -> ann.annotationType().isAnnotationPresent(Query.class))
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(annotationSet)){
                List<EsQueryFieldBean> queryDes = this.mapFieldAnn(field, view, annotationSet);
                queryDesList.addAll(queryDes);
            }
        }
        return queryDesList;
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
            if (TypeUtils.isBaseType(fieldType)) {
                queryDes.setValue(val);
            } else if (val instanceof Collection && ! (val instanceof Map)) {
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Type[] actualTypeArguments = genericType.getActualTypeArguments();
                if (actualTypeArguments.length <= 1 && actualTypeArguments.length >= 1) {
                    Type parameterizeType = actualTypeArguments[0];
                    if (!TypeUtils.isBaseType(parameterizeType.getClass())) {
                        throw new EsHelperQueryException("Just support Collection<@JavaBaseType>");
                    }
                    queryDes.setValue(val);
                } else {
                    throw new EsHelperQueryException("Just support single parameterized-type");
                }
            } else if (val instanceof EsComplexParam) {
                queryDes.setValue(val);
            } else {
                throw new EsHelperQueryException("config @EsQueryField at an Error-Type Field, Just support Primitive-type (exclude void.class) or their Decorate-type or Collection or Map or EsComplexParam");
            }
            return queryDes;
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("unable reach target field ", e);
        }
    }

    private void parseAnn(EsQueryFieldBean queryDes, Field field, Annotation targetAnn) {
        try {
            queryDes.setExtAnnotation(targetAnn);
            Method baseMethod = targetAnn.getClass().getDeclaredMethod(BASE_FILED);
            Base ann = (Base) baseMethod.invoke(targetAnn);
            EsConnector esConnector = ann.connect();
            if (esConnector == null) {
                throw new EsHelperQueryException("ES-QUERY-LOGIC-CONNECTOR cant be null");
            }

            String column = ann.name();
            if (StringUtils.isBlank(column)) {
                column = field.getName();
            }

            String query = ann.queryType();
            if (StringUtils.isBlank(query)) {
                query =  targetAnn.annotationType().getSimpleName();
            }
            if (StringUtils.isBlank(query)) {
                throw new EsHelperQueryException("QUERY-TYPE missing, it's necessary");
            }

            String meta = ann.metaStringify();
            if (StringUtils.isBlank(meta)) {
                meta =  ann.meta().getType();
            }
            if (StringUtils.isBlank(meta)) {
                throw new EsHelperQueryException("META-TYPE missing, it's necessary");
            }

            queryDes.setField(column);
            queryDes.setQueryType(query);
            queryDes.setMeta(meta);
            queryDes.setBoost(ann.boost());

            //TODO 处理该异常
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}
