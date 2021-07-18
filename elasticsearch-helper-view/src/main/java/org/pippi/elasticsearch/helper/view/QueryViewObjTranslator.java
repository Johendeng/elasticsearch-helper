package org.pippi.elasticsearch.helper.view;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.beans.QueryDes;
import org.pippi.elasticsearch.helper.beans.annotation.EsQuery;
import org.pippi.elasticsearch.helper.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.view
 * date:    2021/7/18
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class QueryViewObjTranslator {

    private volatile static QueryViewObjTranslator INSTANCE ;

    private QueryViewObjTranslator(){}

    public static QueryViewObjTranslator instance(){
        if (INSTANCE == null) {
            synchronized (INSTANCE) {
                if (INSTANCE == null) {
                    return new QueryViewObjTranslator();
                }
            }
        }
        return INSTANCE;
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
    public List<QueryDes> read(Object view, boolean visitParent) {
        Class<?> clazz = view.getClass();

        List<Field> fieldList = this.getFields(clazz, visitParent);
        List<QueryDes> queryDesList = Lists.newArrayListWithCapacity(fieldList.size());
        for (Field field : fieldList) {


        }
        return null;
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

    private QueryDes mapFieldAnn(Field field, Object viewObj) {
        EsQuery ann = field.getAnnotation(EsQuery.class);

        String column = ann.name();
        if (StringUtils.isBlank(column)) column = field.getName();

        String query = ann.query();
        if (StringUtils.isBlank(query)) query =  ann.queryEnum().getQuery();
        if (StringUtils.isBlank(query)) throw new EsHelperQueryException("QUERY-TYPE missing, it's necessary");

        String meta = ann.metaTypeStringify();
        if (StringUtils.isBlank(meta)) meta =  ann.metaType().getType();
        if (StringUtils.isBlank(meta)) throw new EsHelperQueryException("META-TYPE missing, it's necessary");

        String script = ann.script();

        QueryDes queryDes = new QueryDes(column, query, meta, script);

        try {
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            Object val = field.get(viewObj);
            if (ReflectUtils.isBaseType(fieldType)) {
                queryDes.setValue(val);
            }
            if (val instanceof Collection) {

                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Type[] actualTypeArguments = genericType.getActualTypeArguments();


            }


            return queryDes;
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("unable reach target field ", e);
        }
    }


}
