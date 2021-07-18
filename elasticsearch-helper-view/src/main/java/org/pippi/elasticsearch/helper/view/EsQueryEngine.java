package org.pippi.elasticsearch.helper.view;

import org.pippi.elasticsearch.helper.view.handler.AbstractQueryHandle;

import java.util.HashMap;
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

    public void addHandle(String handleName, AbstractQueryHandle handler) {
        QUERY_HANDLE_MAP.put(handleName, handler);
    }

    public void execute(Object queryViewObj) {



    }

}
