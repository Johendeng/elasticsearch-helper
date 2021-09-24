package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.ExtMatch;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.mapping.Ext;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.mapping.ExtMatchBean;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.config.GlobalEsQueryConfig;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.utils.ExtAnnBeanMapUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.core.web
 * date:    2021/5/3
 * @Author:  JohenTeng
 * email: 1078481395@qq.com
 *
 *      * @param <T> mapping JavaBean
 *      * @param <A> relative Annotation @Ext**
 **/
public abstract class AbstractQueryHandler<T extends Ext, A extends Annotation> {

    protected static final String _SEPARATOR = ",";

    AbstractQueryHandler(){}

    /**
     *  define the Query-Type use for finding a Query-handle to handle a field-query-bean
     * @return
     */
    protected final String getQueryType() {
        EsQueryHandle annotation = this.getClass().getAnnotation(EsQueryHandle.class);
        String name = annotation.queryTypeStringify();
        if (StringUtils.isBlank(name)) {
            name = annotation.queryType().getQuery();
        }
        if (StringUtils.isBlank(name)) {
            throw new EsHelperConfigException("ES-Query-handle's name is undefine");
        }
        return name;
    }

    /**
     *  execute param-explain
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public AbstractEsRequestHolder execute(EsQueryFieldBean<T> queryDes, AbstractEsRequestHolder searchHelper){
        searchHelper.changeLogicConnector(queryDes.getLogicConnector());
        handleHighLight(queryDes, searchHelper);
        QueryBuilder queryBuilder = handle(queryDes, searchHelper);
        handleExtConfig(queryBuilder, queryDes);
        return searchHelper;
    }

    protected void handleHighLight(EsQueryFieldBean<T> queryDes, AbstractEsRequestHolder searchHelper){
        EsQueryFieldBean.HighLight highLight = queryDes.getHighLight();
        String targetField = queryDes.getField();
        String[] fieldArr = targetField.split(_SEPARATOR);
        if (highLight != null) {
            HighlightBuilder highlightBuilder = GlobalEsQueryConfig.highLight(highLight.getKey());
            for (String currentField : fieldArr) {
                searchHelper.getSource().highlighter(highlightBuilder.field(currentField));
            }
        }
    }

    /**
     *  abstract Handle method, you need implement it
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public abstract QueryBuilder handle (EsQueryFieldBean<T> queryDes, AbstractEsRequestHolder searchHelper);


    /**
     *  explain the extend-params
     * @param queryDes
     * @return
     */
    protected void handleExtConfig (QueryBuilder queryBuilder, EsQueryFieldBean<T> queryDes) {
        // do nothing, if need translate QueryDes.extendDefine, you need implement this method
    }

    /**
     *  mapping the annotation
     * @param queryDes
     * @param clazz
     * @return
     */
    protected final EsQueryFieldBean<T> annotationMapper(EsQueryFieldBean<T> queryDes, Class<A> clazz, QueryBuilder builder) {
        Set<Annotation> extAnnSet = queryDes.getExtAnnotations();
        T extBean = queryDes.getExtBean();
        Optional<Annotation> annotation = extAnnSet.stream().filter(ann -> ann.annotationType().equals(clazz)).findAny();
        if (annotation.isPresent()) {
            extBean = (T) ExtAnnBeanMapUtils.mapping(annotation.get(), extBean.getClass());
            queryDes.setExtBean(extBean);
            extBean.configQueryBuilder(builder);
        }
        return queryDes;
    }

}
