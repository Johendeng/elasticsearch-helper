package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.config.GlobalEsQueryConfig;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.core.web
 * date:    2021/5/3
 * @Author:  JohenTeng
 * email: 1078481395@qq.com
 **/
public abstract class AbstractQueryHandler {

    protected static final String _SEPARATOR = ",";

    AbstractQueryHandler(){}

    /**
     *  define the Query-Type use for finding a Query-handle to handle a field-query-bean
     * @return
     */
    protected final String getQueryType() {
        EsQueryHandle annotation = this.getClass().getAnnotation(EsQueryHandle.class);
        String name = annotation.name();
        if (StringUtils.isBlank(name)) {
            name = annotation.handleEnum().getQuery();
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
    public AbstractEsRequestHolder execute(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper){
        EsQueryFieldBean handledDes = explainExtendDefine(queryDes);
        searchHelper.changeLogicConnector(queryDes.getLogicConnector());
        handleHighLight(queryDes, searchHelper);
        return handle(handledDes, searchHelper);
    }

    protected void handleHighLight(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper){
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
     *
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public abstract AbstractEsRequestHolder handle (EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper);


    /**
     *  explain the extend-params
     * @param queryDes
     * @return
     */
    protected EsQueryFieldBean explainExtendDefine (EsQueryFieldBean queryDes) {
        // do nothing, if need translate QueryDes.extendDefine, you need implement this method
        return queryDes;
    }


}
