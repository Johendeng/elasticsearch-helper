package org.pippi.elasticsearch.helper.view;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.beans.QueryDes;
import org.pippi.elasticsearch.helper.beans.annotation.view.EsQueryFiled;
import org.pippi.elasticsearch.helper.beans.annotation.view.EsQueryIndex;
import org.pippi.elasticsearch.helper.beans.exception.EsHelperQueryException;
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

    public String getIndex(Object view) {

        Class<?> clazz = view.getClass();
        EsQueryIndex ann = clazz.getAnnotation(EsQueryIndex.class);

        if (ann == null) throw new EsHelperQueryException("undefine query-index @EsQueryIndex");


        String index = ann.index();
        return index;

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
            if (field.isAnnotationPresent(EsQueryFiled.class)) {
                QueryDes queryDes = this.mapFieldAnn(field, view);
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

    private QueryDes mapFieldAnn(Field field, Object viewObj) {

        EsQueryFiled ann = field.getAnnotation(EsQueryFiled.class);

        String column = ann.name();
        if (StringUtils.isBlank(column)) column = field.getName();

        String query = ann.query();
        if (StringUtils.isBlank(query)) query =  ann.queryEnum().getQuery();
        if (StringUtils.isBlank(query)) throw new EsHelperQueryException("QUERY-TYPE missing, it's necessary");

        String meta = ann.metaTypeStringify();
        if (StringUtils.isBlank(meta)) meta =  ann.metaType().getType();
        if (StringUtils.isBlank(meta)) throw new EsHelperQueryException("META-TYPE missing, it's necessary");

        String script = ann.script();
        String extendDefine = ann.extendDefine();

        QueryDes queryDes = new QueryDes(column, query, meta, script);
        queryDes.setExtendDefine(extendDefine);

        try {
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            Object val = field.get(viewObj);

            if (TypeUtils.isBaseType(fieldType)) {
                queryDes.setValue(val);
            }

            if (val instanceof Collection && ! (val instanceof Map)) {

                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Type[] actualTypeArguments = genericType.getActualTypeArguments();

                if (actualTypeArguments.length > 1 || actualTypeArguments.length < 1 )
                    throw new EsHelperQueryException("Just support single parameterized-type");

                Type paramerizeType = actualTypeArguments[0];

                if (!TypeUtils.isBaseType(paramerizeType.getClass()))
                    throw new EsHelperQueryException("Just support Collection<@JavaBaseType>");

                queryDes.setValues( ((Collection)val).toArray() );

            }

            if (val instanceof EsQueryComplexDefine) queryDes.setValue(val);

            return queryDes;
        } catch (IllegalAccessException e) {
            throw new EsHelperQueryException("unable reach target field ", e);
        }
    }


}
