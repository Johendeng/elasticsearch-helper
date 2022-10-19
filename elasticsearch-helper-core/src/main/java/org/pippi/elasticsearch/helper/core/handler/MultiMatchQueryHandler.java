package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MultiMatch;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.MultiMatchQueryBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author      JohenTeng
 * @date      2021/8/20
 */
@EsQueryHandle(MultiMatch.class)
public class MultiMatchQueryHandler extends AbstractQueryHandler<MultiMatchQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MultiMatchQueryBean> queryDes, AbstractEsSession searchHelper) {
        MultiMatchQueryBean extBean = queryDes.getExtBean();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(queryDes.getValue(), extBean.getFields())
                .boost(queryDes.getBoost());
        return multiMatchQueryBuilder;
    }
}
