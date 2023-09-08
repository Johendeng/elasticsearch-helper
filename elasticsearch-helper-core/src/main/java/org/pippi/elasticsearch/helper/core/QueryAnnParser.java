package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.model.bean.base.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.model.bean.base.FuncScoreBean;
import org.pippi.elasticsearch.helper.model.bean.base.HighLightBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsCondition;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.HighLight;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.FuncQuery;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.ScriptQuery;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SourceOrder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.UserQuery;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.model.EsConditionHandle;
import org.pippi.elasticsearch.helper.model.param.EsComplexParam;
import org.pippi.elasticsearch.helper.model.utils.ExtAnnBeanMapUtils;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author JohenTeng
 * @date 2021/7/18
 **/
@SuppressWarnings("all")
public class QueryAnnParser {

    private static final String BASE_FILED = "value";

    private QueryAnnParser() {
    }

    private static class QueryAnnParserHolder {
        private static final QueryAnnParser ANN_PARSER = new QueryAnnParser();
    }

    public static QueryAnnParser instance() {
        return QueryAnnParserHolder.ANN_PARSER;
    }

    /**
     * parse Index-information
     *
     * @param view return
     */
    public EsQueryIndexBean getIndex(Object view) {
        Class<?> clazz = view.getClass();
        EsAnnQueryIndex ann = clazz.getAnnotation(EsAnnQueryIndex.class);
        return this.getIndex(ann, clazz.getAnnotation(HighLight.class));
    }

    public EsQueryIndexBean getIndex(Method method) {
        return getIndex(method.getAnnotation(EsAnnQueryIndex.class), method.getAnnotation(HighLight.class));
    }

    public EsQueryIndexBean getIndex(EsAnnQueryIndex ann, HighLight highLightAnn) {
        if (ann == null) {
            throw new EsHelperQueryException("undefine query-index @EsQueryIndex");
        }
        String index = ann.index();
        QueryModel model = ann.model();
        String[] fetchFields = ann.fetch();
        String[] excludeFields = ann.exclude();
        float minScore = ann.minScore();
        boolean traceScore = ann.traceScore();
        int backSize = ann.size();
        EsQueryIndexBean indexBean = new EsQueryIndexBean(index, model, fetchFields, excludeFields, minScore, traceScore);
        indexBean.setSize(backSize);
        if (Objects.nonNull(highLightAnn)) {
            indexBean.setHighLight(HighLightBean.phrase(highLightAnn));
        }
        FuncScoreBean funcScoreBean = (FuncScoreBean) ExtAnnBeanMapUtils.mapping(ann.funcScore(), FuncScoreBean.class);
        indexBean.setFuncScoreBean(funcScoreBean);
        return indexBean;
    }


    /**
     * read query-view-pojo define by user,
     * support java-base type, collections and
     *
     * @param view
     * @param visitParent return
     * @link
     */
    public List<EsQueryFieldBean> read(Object val, boolean visitParent) {
        Class<?> clazz = val.getClass();
        List<Field> fieldList = this.getFields(clazz, visitParent);
        List<EsQueryFieldBean> queryDesList = Lists.newArrayListWithCapacity(fieldList.size());
        for (Field field : fieldList) {
            Set<Annotation> annotationSet = Arrays.stream(field.getAnnotations())
                    .filter(ann -> ann.annotationType().isAnnotationPresent(Query.class) || ann.annotationType().isAnnotationPresent(FuncQuery.class))
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(annotationSet) && checkEsCondition(field, val)) {
                List<EsQueryFieldBean> queryDes = this.mapFieldAnn(field, val, annotationSet);
                queryDesList.addAll(queryDes);
            }
        }
        return queryDesList;
    }

    public List<EsQueryFieldBean> read(String path, Object val) {
        List<EsQueryFieldBean> fieldBean = this.read(val, false);
        fieldBean.forEach(bean -> {
            bean.setField(path + "." + bean.getField());
        });
        return fieldBean;
    }

    public List<EsQueryFieldBean> read(Parameter[] params, Object[] args, Annotation[][] anns) {
        List<EsQueryFieldBean> queryDesList = Lists.newArrayListWithCapacity(params.length);
        for (int index = 0; index < params.length; index ++) {
            Parameter param = params[index];
            Object arg = args[index];
            Annotation[] paramAnn = anns[index];
            Set<Annotation> annotationSet = Arrays.stream(paramAnn)
                    .filter(ann -> ann.annotationType().isAnnotationPresent(Query.class)
                                 || ann.annotationType().isAnnotationPresent(FuncQuery.class))
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(annotationSet) && checkEsCondition(param, arg)) {
                List<EsQueryFieldBean> queryDes = this.mapFieldAnn(param, arg, annotationSet);
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
        EsCondition condition = (EsCondition) optionCondition.get();
        Class<? extends EsConditionHandle> conditionHandleClazz = condition.value();
        EsConditionHandle conditionHandle = ReflectionUtils.newInstance(conditionHandleClazz);
        Object val = ReflectionUtils.getFieldValue(field, view);
        if (Objects.isNull(val)) {
            return false;
        }
        return conditionHandle.test(val);
    }

    private boolean checkEsCondition(Parameter param, Object arg) {
        Optional<Annotation> optionCondition = Arrays.stream(param.getAnnotations())
                .filter(ann -> ann.annotationType().equals(EsCondition.class))
                .findAny();
        if (!optionCondition.isPresent()) {
            return true;
        }
        EsCondition condition = (EsCondition) optionCondition.get();
        Class<? extends EsConditionHandle> conditionHandleClazz = condition.value();
        EsConditionHandle conditionHandle = ReflectionUtils.newInstance(conditionHandleClazz);
        if (Objects.isNull(arg)) {
            return false;
        }
        return conditionHandle.test(arg);
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
            Optional.ofNullable(parseValue(field, viewObj)).ifPresent(queryDes -> {
                this.parseAnn(queryDes, field.getName(), ann);
                res.add(queryDes);
            });
        }
        return res;
    }

    private List<EsQueryFieldBean> mapFieldAnn(Parameter parameter, Object arg, Set<Annotation> annotationSet) {
        final List<EsQueryFieldBean> res = Lists.newArrayList();
        for (Annotation ann : annotationSet) {
            Optional.ofNullable(parseValue(parameter, arg)).ifPresent(queryDes -> {
                this.parseAnn(queryDes, parameter.getName(), ann);
                res.add(queryDes);
            });
        }
        return res;
    }


    private EsQueryFieldBean parseValue(Parameter parameter, Object arg) {
        EsQueryFieldBean queryDes = new EsQueryFieldBean<>();
        Class<?> fieldType = arg.getClass();
        if (
                (fieldType.isAnnotationPresent(ScriptQuery.class) && !fieldType.getAnnotation(ScriptQuery.class).hasParams())
                || (fieldType.isAnnotationPresent(SourceOrder.class))
        ) {
            // 脚本查詢字段如果配置不需要參數的脚本，則無需加載查詢參數
            return queryDes;
        }
        if (ReflectionUtils.isBaseType(fieldType)
            || arg instanceof EsComplexParam
            || arg.getClass().isAnnotationPresent(EsAnnQueryIndex.class)
            || checkCollectionValueType(parameter, arg)) {
            queryDes.setValue(arg);
        } else {
            throw new EsHelperQueryException("config @EsQueryField at an Error-Type Field, Just support " +
                    "Primitive-type (exclude void.class) or their Decorate-type or Collection or Map or EsComplexParam");
        }
        return queryDes;
    }

    private EsQueryFieldBean parseValue(Field field, Object viewObj) {
        try {
            EsQueryFieldBean queryDes = new EsQueryFieldBean<>();
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            Object val = field.get(viewObj);
            // todo 魔法设计 需要修改
            if (
                (field.isAnnotationPresent(ScriptQuery.class) && !field.getAnnotation(ScriptQuery.class).hasParams())
             || (field.isAnnotationPresent(SourceOrder.class))
            ) {
                // 脚本查詢字段如果配置不需要參數的脚本，則無需加載查詢參數
                return queryDes;
            }
            if (Objects.isNull(val)) {
                return null;
            }
            if (ReflectionUtils.isBaseType(fieldType)
                || val instanceof EsComplexParam
                || val.getClass().isAnnotationPresent(EsAnnQueryIndex.class)
                || checkCollectionValueType(field, val)) {
                queryDes.setValue(val);
            } else {
                throw new EsHelperQueryException("config @EsQueryField at an Error-Type Field, Just support " +
                        "Primitive-type (exclude void.class) or their Decorate-type or Collection or Map or EsComplexParam");
            }
            return queryDes;
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("unable reach target field ", e);
        }
    }

    private boolean checkCollectionValueType(Field field, Object val) {
        Predicate<Field> checkCollectionTypePredicate = f -> {
            if (val.getClass().isArray()) {
                return ReflectionUtils.isBaseType(val.getClass().getComponentType());
            }
            ParameterizedType genericType = (ParameterizedType) f.getGenericType();
            Type[] actualType = genericType.getActualTypeArguments();
            return Arrays.stream(actualType).allMatch(type -> ReflectionUtils.isBaseType((Class<?>) type));
        };
        return (val instanceof Collection || val.getClass().isArray() || val instanceof Map ) && checkCollectionTypePredicate.test(field);
    }

    private boolean checkCollectionValueType(Parameter parameter, Object arg) {
        Predicate<Parameter> checkCollectionTypePredicate = param -> {
            if (arg.getClass().isArray()) {
                return ReflectionUtils.isBaseType(arg.getClass().getComponentType());
            }
            ParameterizedType genericType = (ParameterizedType) param.getParameterizedType();
            Type[] actualType = genericType.getActualTypeArguments();
            return Arrays.stream(actualType).allMatch(type -> ReflectionUtils.isBaseType((Class<?>) type));
        };
        return (arg instanceof Collection || arg.getClass().isArray() || arg instanceof Map ) && checkCollectionTypePredicate.test(parameter);
    }


    private void parseAnn(EsQueryFieldBean queryDes, String orgFieldName, Annotation targetAnn) {
        try {
            if (targetAnn.annotationType().isAnnotationPresent(Query.class)) {
                queryDes.setExtAnnotation(targetAnn);
                Method baseMethod = targetAnn.getClass().getDeclaredMethod(BASE_FILED);
                Base ann = (Base) baseMethod.invoke(targetAnn);
                EsConnector esConnector = ann.connect();
                queryDes.setLogicConnector(esConnector);
                String column = EsBeanFieldTransUtils.camelify(orgFieldName);
                if (StringUtils.isNotBlank(ann.value())) {
                    column = ann.value();
                } else if (StringUtils.isNotBlank(ann.name())) {
                    column = ann.name();
                }
                queryDes.setField(column);
                String queryType = ann.queryType();
                if (StringUtils.isBlank(queryType)) {
                    queryType = targetAnn.annotationType().getSimpleName();
                }
                if (StringUtils.isBlank(queryType) || StringUtils.equals(queryType, UserQuery.class.getSimpleName())) {
                    throw new EsHelperQueryException("QUERY-TYPE missing, it's necessary");
                }
                queryDes.setQueryType(queryType);

                String meta = ann.metaStringify();
                if (StringUtils.isBlank(meta)) {
                    meta = ann.meta().getType();
                }
                queryDes.setMeta(meta);
                queryDes.setBoost(ann.boost());
            }
            /**
             * xxx: 待优化, 存在魔法值 结构设计不合理
             */
            if (targetAnn.annotationType().isAnnotationPresent(FuncQuery.class)) {
                queryDes.setFuncScoreAnn(targetAnn);
                queryDes.setQueryType("blank");
                Method baseMethod = targetAnn.getClass().getDeclaredMethod("field");
                String fieldName = (String) baseMethod.invoke(targetAnn);
                if (StringUtils.isBlank(fieldName)) {
                    fieldName = orgFieldName;
                }
                queryDes.setField(fieldName);
            }
        } catch (Exception e) {
            throw new EsHelperConfigException("annotation analysis Error, cause:", e);
        }
    }
}
