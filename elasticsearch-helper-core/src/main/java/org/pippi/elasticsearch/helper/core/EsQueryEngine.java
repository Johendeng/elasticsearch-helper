package org.pippi.elasticsearch.helper.core;

import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.base.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.handler.AbstractQueryHandler;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.*;

/**
 * @author    JohenTeng
 * @date     2021/7/17
 **/
@SuppressWarnings("all")
public class EsQueryEngine {

    /**
     * @param queryViewObj
     * @param visitParent
     * return
     */
    public static AbstractEsSession execute(Object queryViewObj, boolean visitParent) {
        QueryAnnParser translator = QueryAnnParser.instance();
        EsQueryIndexBean indexQueryBean = translator.getIndex(queryViewObj);
        List<EsQueryFieldBean> queryDesList = translator.read(queryViewObj, visitParent);
        AbstractEsSession helper = AbstractEsSession.builder().config(indexQueryBean).build();
        for (EsQueryFieldBean queryDes : queryDesList) {
            String queryKey = queryDes.getQueryType();
            AbstractQueryHandler queryHandle = QueryHandlerFactory.getTargetHandleInstance(queryKey);
            queryHandle.execute(queryDes, helper);
        }
        return helper;
    }
}
