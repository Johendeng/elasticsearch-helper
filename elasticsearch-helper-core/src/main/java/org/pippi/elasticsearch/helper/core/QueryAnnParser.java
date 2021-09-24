package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryField;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.HighLight;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.MultiQueryField;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.Ext;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.utils.TypeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.view
 * date:    2021/7/18
 * @Author:  JohenTeng
 * email: 1078481395@qq.com
 **/
public class QueryAnnParser {

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

        return new EsQueryIndexBean(index, model, fetchFields, excludeFields);

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
            if (field.isAnnotationPresent(EsQueryField.class) || field.isAnnotationPresent(MultiQueryField.class)){
                EsQueryFieldBean queryDes = this.mapFieldAnn(field, view);
                if (Objects.nonNull(queryDes)) {
                    Set<Annotation> annotationList = Arrays.stream(field.getAnnotations())
                            .filter(ann -> ann.annotationType().isAnnotationPresent(Ext.class))
                            .collect(Collectors.toSet());
                    queryDes.setExtAnnotations(annotationList);
                    queryDesList.add(queryDes);
                }
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

    private EsQueryFieldBean mapFieldAnn(Field field, Object viewObj) {
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
                    queryDes.setValues(((Collection) val).toArray());
                } else {
                    throw new EsHelperQueryException("Just support single parameterized-type");
                }
            } else if (val instanceof EsComplexParam) {
                queryDes.setValue(val);
            } else {
                throw new EsHelperQueryException("config @EsQueryField at an Error-Type Field, Just support Primitive-type (exclude void.class) or their Decorate-type or Collection or Map or EsComplexParam");
            }
            this.parseAnn(queryDes, field);
            return queryDes;
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("unable reach target field ", e);
        }
    }

    private void parseAnn(EsQueryFieldBean queryDes, Field field) {
        MultiQueryField multiQueryField = field.getAnnotation(MultiQueryField.class);
        if (multiQueryField != null) {
            EsQueryField[] queryFieldArr = multiQueryField.value();
            for (EsQueryField esQueryField : queryFieldArr) {
                this.readSingleAnn(queryDes, esQueryField, field);
            }
        } else {
            EsQueryField esQueryField = field.getAnnotation(EsQueryField.class);
            this.readSingleAnn(queryDes, esQueryField, field);
        }
    }


    private void readSingleAnn(EsQueryFieldBean queryDes, EsQueryField ann, Field field) {
        EsConnector esConnector = ann.connect();
        if (esConnector == null) {
            throw new EsHelperQueryException("ES-QUERY-LOGIC-CONNECTOR cant be null");
        }

        String column = ann.name();
        if (StringUtils.isBlank(column)) {
            column = field.getName();
        }

        String query = ann.queryTypeStringify();
        if (StringUtils.isBlank(query)) {
            query =  ann.queryType().getQuery();
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

        Float  boost = ann.boost();

        queryDes.setField(column);
        queryDes.setQueryType(query);
        queryDes.setMeta(meta);
        queryDes.setBoost(boost);

        this.readHighLight(queryDes, field);
    }

    private void readHighLight(EsQueryFieldBean queryDes, Field field){
        HighLight highLightAnn = field.getAnnotation(HighLight.class);
        if (Objects.nonNull(highLightAnn)) {
            queryDes.setHighLight(true, highLightAnn.value());
        }
    }


}
