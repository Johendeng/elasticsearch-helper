package org.pippi.elasticsearch.helper.view.handler;

import org.pippi.elasticsearch.helper.beans.QueryDes;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;
import org.pippi.elasticsearch.helper.view.EsQueryEngine;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.core.web
 * date:    2021/5/3
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public abstract class AbstractQueryHandle {

    {
        Class<? extends AbstractQueryHandle> targetClazz = this.getClass();

        //        EsQueryEngine.addHandleClazz();
    }

    private AbstractQueryHandle(){}


    public abstract EsSearchHelper handle (QueryDes queryDes, EsSearchHelper searchHelper);



}
