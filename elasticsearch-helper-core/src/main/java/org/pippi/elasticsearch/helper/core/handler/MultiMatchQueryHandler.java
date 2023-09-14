package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MultiMatch;
import org.pippi.elasticsearch.helper.model.bean.query.MultiMatchQueryConf;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author      JohenTeng
 * @date      2021/8/20
 */
@EsQueryHandle(MultiMatch.class)
public class MultiMatchQueryHandler extends AbstractQueryHandler<MultiMatchQueryConf>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MultiMatchQueryConf> queryDes, AbstractEsSession searchHelper) {
        MultiMatchQueryConf extBean = queryDes.getExtBean();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(queryDes.getValue(), extBean.getFields())
                .boost(queryDes.getBoost());
        return multiMatchQueryBuilder;
    }
}
