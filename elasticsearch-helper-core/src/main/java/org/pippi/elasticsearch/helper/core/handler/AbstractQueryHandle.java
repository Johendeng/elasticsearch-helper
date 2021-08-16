package org.pippi.elasticsearch.helper.core.handler;

import org.pippi.elasticsearch.helper.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.core.web
 * date:    2021/5/3
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public abstract class AbstractQueryHandle {

    AbstractQueryHandle(){}


    /**
     *  define the Query-Type
     * @return
     */
    public abstract String getQueryType();

    /**
     *  execute param-explain
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public EsSearchHelper execute(EsQueryFieldBean queryDes, EsSearchHelper searchHelper){
        EsQueryFieldBean handledDes = explainExtendDefine(queryDes);
        return handle(handledDes, searchHelper);
    }

    /**
     *
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public abstract EsSearchHelper handle (EsQueryFieldBean queryDes, EsSearchHelper searchHelper);


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
