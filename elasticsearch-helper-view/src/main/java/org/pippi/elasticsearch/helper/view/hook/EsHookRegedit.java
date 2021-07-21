package org.pippi.elasticsearch.helper.view.hook;

import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.pippi.elasticsearch.helper.core.EsResponseParseHelper;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;
import com.google.common.collect.Maps;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsResponseHookFactory
 * Package Name:org.pippi.elasticsearch.helper.view
 * Date:2021/7/21 00:36
 * Author:JohenTeng
 * Description:
 */
public class EsHookRegedit {

    public static final Map<String, RequestHook> REP_FUNC_REGEDIT = Maps.newHashMap();

    public static final Map<String, ResponseHook> RESP_FUNC_REGEDIT = Maps.newHashMap();

    public static void addReqHook (String key, RequestHook requestHook) {
        REP_FUNC_REGEDIT.put(key, requestHook);
    }

    public static void addRespHook(String key, ResponseHook responseHook) {
        RESP_FUNC_REGEDIT.put(key, responseHook);
    }

    public static <P>EsSearchHelper useReqHook(String key, EsSearchHelper helper, P param) {
        return REP_FUNC_REGEDIT.get(key).apply(helper, param);
    }

    public static <R>R useRespHook (String key, SearchResponse resp) {
        return (R)RESP_FUNC_REGEDIT.get(key).apply(resp);
    }

}
