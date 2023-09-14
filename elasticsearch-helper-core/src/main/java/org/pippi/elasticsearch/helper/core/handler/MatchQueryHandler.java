package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Match;
import org.pippi.elasticsearch.helper.model.bean.query.MatchQueryConf;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author      JohenTeng
 * @date      2021/8/20
 */
@EsQueryHandle(Match.class)
public class MatchQueryHandler extends AbstractQueryHandler<MatchQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MatchQueryConf> queryDes, AbstractEsSession searchHelper) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(queryDes.getField(), queryDes.getValue()).boost(queryDes.getBoost());
        return matchQuery;
    }

}
