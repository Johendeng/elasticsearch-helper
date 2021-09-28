package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.QueryBean;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.config.GlobalEsQueryConfig;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.utils.ExtAnnBeanMapUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @project: elasticsearch-helper
 * @package: com.poet.elasticsearch.helper.core.web
 * @date:    2021/5/3
 * @@Author:  JohenTeng
 * @email: 1078481395@qq.com
 *
 * @param <T> mapping JavaBean
 **/
public abstract class AbstractQueryHandler<T extends QueryBean> {

    protected static final String _SEPARATOR = ",";

    AbstractQueryHandler(){}

    /**
     *  execute param-explain
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public final AbstractEsRequestHolder execute(EsQueryFieldBean<T> queryDes, AbstractEsRequestHolder searchHelper){
        searchHelper.changeLogicConnector(queryDes.getLogicConnector());
        handleExtBean(queryDes);
        handleHighLight(queryDes, searchHelper);
        QueryBuilder queryBuilder = handle(queryDes, searchHelper);
        if (Objects.nonNull(queryBuilder)) {
            queryDes.getExtBean().configQueryBuilder(queryBuilder);
            handleExtConfig(queryDes, queryBuilder);
        }
        return searchHelper;
    }

    /**
     *  abstract Handle method, you need to implement it
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public abstract QueryBuilder handle (EsQueryFieldBean<T> queryDes, AbstractEsRequestHolder searchHelper);


    /**
     *  explain the extend-params
     *  final handle-process of query-field-reader
     * @param queryDes
     * @return
     */
    protected void handleExtConfig (EsQueryFieldBean<T> queryDes, QueryBuilder queryBuilder) {
        // do nothing, if need translate QueryDes.extendDefine, you need implement this method
    }

    /**
     *  mapping the annotation
     * @param queryDes
     * @return
     */
    protected final EsQueryFieldBean<T> handleExtBean(EsQueryFieldBean<T> queryDes) {
        try {
            Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
            if (actualTypeArguments.length == 0) {
                throw new EsHelperConfigException("the AbstractQueryHandler<T>, T is un-define");
            }
            String fullClassPath = actualTypeArguments[0].getTypeName();
            Class<?> aClass = Class.forName(fullClassPath);
            T extBean = (T) ExtAnnBeanMapUtils.mapping(queryDes.getExtAnnotation(), aClass);
            queryDes.setExtBean(extBean);
            return queryDes;
        } catch (ClassNotFoundException e) {
            throw new EsHelperConfigException("queryHandle-actualType class not found, cause:", e);
        }
    }

    /**
     * define the filed enable highLight
     * @param queryDes
     * @param searchHelper
     */
    protected final void handleHighLight(EsQueryFieldBean<T> queryDes, AbstractEsRequestHolder searchHelper){
        String targetField = queryDes.getField();
        String[] fieldArr = targetField.split(_SEPARATOR);
        if (queryDes.isHighLight()) {
            HighlightBuilder highlightBuilder = GlobalEsQueryConfig.highLight(queryDes.getHighLightKey());
            for (String currentField : fieldArr) {
                searchHelper.getSource().highlighter(highlightBuilder.field(currentField));
            }
        }
    }


}
