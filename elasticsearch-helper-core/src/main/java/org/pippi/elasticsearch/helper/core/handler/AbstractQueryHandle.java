package org.pippi.elasticsearch.helper.core.handler;

import org.pippi.elasticsearch.helper.beans.mapping.QueryDes;
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
     *  执行方法
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public EsSearchHelper execute(QueryDes queryDes, EsSearchHelper searchHelper){
        QueryDes handledDes = explainExtendDefine(queryDes);
        return handle(handledDes, searchHelper);
    }

    /**
     *
     * @param queryDes
     * @return
     */
    public QueryDes explainExtendDefine (QueryDes queryDes) {
        // do nothing, if need translate QueryDes.extendDefine, you need implement this method
        return queryDes;
    }

    /**
     *
     * @param queryDes
     * @param searchHelper
     * @return
     */
    public abstract EsSearchHelper handle (QueryDes queryDes, EsSearchHelper searchHelper);




}
