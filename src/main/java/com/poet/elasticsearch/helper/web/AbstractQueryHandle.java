package com.poet.elasticsearch.helper.web;

import com.google.common.collect.Maps;
import com.poet.elasticsearch.helper.EsSearchHelper;
import com.poet.elasticsearch.helper.enums.QueryType;

import java.util.Map;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.web
 * date:    2021/5/3
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public abstract class AbstractQueryHandle {


    private static final Map<QueryType, Class<? extends AbstractQueryHandle>> _M_QUERY_HANDLER = Maps.newHashMap();


    protected void handlerRegistering(){



    }



    /**
     *
     */
    private EsSearchHelper searchHelper;


    public AbstractQueryHandle(EsSearchHelper searchHelper) {
        this.searchHelper = searchHelper;

    }

    public abstract void handle (QueryDes queryDes);



}
