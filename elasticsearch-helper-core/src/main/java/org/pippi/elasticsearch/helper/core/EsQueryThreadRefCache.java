package org.pippi.elasticsearch.helper.core;

import org.pippi.elasticsearch.helper.core.EsSearchHelper;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.view
 * date:    2021/7/17
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class EsQueryThreadRefCache {

    public static final ThreadLocal<EsSearchHelper> THREAD_LOCAL_QUERY_HANDLE_CACHE = new ThreadLocal<>();


    public static void load(EsSearchHelper helper) {
        THREAD_LOCAL_QUERY_HANDLE_CACHE.set(helper);
    }

    public static EsSearchHelper get(){
        return THREAD_LOCAL_QUERY_HANDLE_CACHE.get();
    }

    public static void remove(){
        THREAD_LOCAL_QUERY_HANDLE_CACHE.remove();
    }

}
