package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryField;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsComplexParam;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.utils.TypeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.view
 * date:    2021/7/18
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class QueryAnnParser {

    private volatile static QueryAnnParser INSTANCE ;

    private QueryAnnParser(){}

    public static QueryAnnParser instance(){
        if (INSTANCE == null) {
            synchronized (INSTANCE) {
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

        return new EsQueryIndexBean(index, model);

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
            if (field.isAnnotationPresent(EsQueryField.class)) {
                EsQueryFieldBean queryDes = this.mapFieldAnn(field, view);
                queryDesList.add(queryDes);
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

            if (TypeUtils.isBaseType(fieldType)) {
                queryDes.setValue(val);
            }

            if (val instanceof Collection && ! (val instanceof Map)) {

                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Type[] actualTypeArguments = genericType.getActualTypeArguments();

                if (actualTypeArguments.length <= 1 && actualTypeArguments.length >= 1) {
                    Type paramerizeType = actualTypeArguments[0];

                    if (!TypeUtils.isBaseType(paramerizeType.getClass())) {
                        throw new EsHelperQueryException("Just support Collection<@JavaBaseType>");
                    }

                    queryDes.setValues(((Collection) val).toArray());
                } else {
                    throw new EsHelperQueryException("Just support single parameterized-type");
                }

            }

            if (val instanceof EsComplexParam) {
                queryDes.setValue(val);
            }

            this.parseAnn(queryDes, field);
            return queryDes;
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("unable reach target field ", e);
        }
    }

    private void parseAnn(EsQueryFieldBean queryDes, Field field) {
        EsQueryField ann = field.getAnnotation(EsQueryField.class);
        EsConnector esConnector = ann.logicConnector();
        if (esConnector == null) {
            throw new EsHelperQueryException("ES-QUERY-LOGIC-CONNECTOR cant be null");
        }

        String column = ann.name();
        if (StringUtils.isBlank(column)) {
            column = field.getName();
        }

        String query = ann.queryKey();
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

        String script = ann.script();
        String extendDefine = ann.extendDefine();

        queryDes.setColumn(column);
        queryDes.setQueryType(query);
        queryDes.setMeta(meta);
        queryDes.setScript(script);
        queryDes.setExtendDefine(extendDefine);
    }


}
