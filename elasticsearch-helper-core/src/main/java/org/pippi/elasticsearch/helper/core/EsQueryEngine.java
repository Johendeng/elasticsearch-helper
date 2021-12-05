package org.pippi.elasticsearch.helper.core;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.handler.AbstractQueryHandler;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

import java.util.*;

/**
 * project  elasticsearch-helper
 * packages   org.pippi.elasticsearch.helper.view
 * date     2021/7/17
 * author    JohenTeng
 * email    1078481395@qq.com
 **/
public class EsQueryEngine {

    /**
     * @param queryViewObj
     * @param visitParent
     * return
     */
    public static AbstractEsRequestHolder execute(Object queryViewObj, boolean visitParent) {
        QueryAnnParser translator = QueryAnnParser.instance();
        EsQueryIndexBean indexQueryBean = translator.getIndex(queryViewObj);
        List<EsQueryFieldBean> queryDesList = translator.read(queryViewObj, visitParent);
        AbstractEsRequestHolder helper = AbstractEsRequestHolder.builder().config(indexQueryBean).build();
        for (EsQueryFieldBean queryDes : queryDesList) {
            String queryKey = queryDes.getQueryType();
            AbstractQueryHandler queryHandle = QueryHandlerFactory.getTargetHandleInstance(queryKey);
            queryHandle.execute(queryDes, helper);
        }
        return helper;
    }





}
