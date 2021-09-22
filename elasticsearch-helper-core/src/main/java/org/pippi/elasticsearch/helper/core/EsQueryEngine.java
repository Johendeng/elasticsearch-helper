package org.pippi.elasticsearch.helper.core;

import com.google.common.collect.Sets;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.handler.AbstractQueryHandler;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

import java.util.*;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.view
 * date:    2021/7/17
 * @Author:  JohenTeng
 * email: 1078481395@qq.com
 **/
public class EsQueryEngine {

    /**
     * @param queryViewObj
     * @param visitParent
     * @return
     */
    public static AbstractEsRequestHolder execute(Object queryViewObj, boolean visitParent) {

        QueryAnnParser translator = QueryAnnParser.instance();
        EsQueryIndexBean indexQueryBean = translator.getIndex(queryViewObj);
        List<EsQueryFieldBean> queryDesList = translator.read(queryViewObj, visitParent);
        AbstractEsRequestHolder helper = AbstractEsRequestHolder.builder()
                                                                .indexName(indexQueryBean.getIndexName())
                                                                .queryModel(indexQueryBean.getEsQueryModel())
                                                                .build();
        for (EsQueryFieldBean queryDes : queryDesList) {
            String queryKey = queryDes.getQueryType();
            AbstractQueryHandler queryHandle = QueryHandlerFactory.getTargetHandleInstance(queryKey);
            queryHandle.execute(queryDes, helper);
        }
        return helper;
    }





}
