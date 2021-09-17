package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.core.web
 * date:    2021/5/3
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public abstract class AbstractQueryHandler {

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
        return handle(handledDes, searchHelper);
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
    public EsQueryFieldBean explainExtendDefine (EsQueryFieldBean queryDes) {
        // do nothing, if need translate QueryDes.extendDefine, you need implement this method
        return queryDes;
    }


}
