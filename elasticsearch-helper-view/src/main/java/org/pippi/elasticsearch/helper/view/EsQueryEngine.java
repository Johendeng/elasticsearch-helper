package org.pippi.elasticsearch.helper.view;

import org.pippi.elasticsearch.helper.beans.QueryDes;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;
import org.pippi.elasticsearch.helper.view.handler.AbstractQueryHandle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.view
 * date:    2021/7/17
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class EsQueryEngine {

    private static final Map<String, AbstractQueryHandle> QUERY_HANDLE_MAP = new HashMap<>();

    public static void addHandle(String handleName, AbstractQueryHandle handler) {
        QUERY_HANDLE_MAP.put(handleName, handler);
    }


    /**
     *
     * @param helper
     * @param queryViewObj
     * @param visitParent
     * @return
     */
    public EsSearchHelper execute(EsSearchHelper helper, Object queryViewObj, boolean visitParent) {

        QueryViewObjTranslator translator = QueryViewObjTranslator.instance();
        List<QueryDes> queryDesList = translator.read(queryViewObj, visitParent);

        for (QueryDes queryDes : queryDesList) {
            String queryKey = queryDes.getQueryType();
            AbstractQueryHandle queryHandle = QUERY_HANDLE_MAP.get(queryKey);
            queryHandle.handle(queryDes, helper);
        }

        return helper;
    }

}
