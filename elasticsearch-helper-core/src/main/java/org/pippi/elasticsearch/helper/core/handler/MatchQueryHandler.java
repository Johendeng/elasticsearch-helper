package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Match;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.MatchQueryBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * @author      JohenTeng
 * @date      2021/8/20
 */
@EsQueryHandle(Match.class)
public class MatchQueryHandler extends AbstractQueryHandler<MatchQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MatchQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(queryDes.getField(), queryDes.getValue()).boost(queryDes.getBoost());
        searchHelper.chain(matchQuery);
        return matchQuery;
    }

}
