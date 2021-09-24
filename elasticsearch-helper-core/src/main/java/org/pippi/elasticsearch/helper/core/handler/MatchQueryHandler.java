package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.ExtMatch;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.mapping.ExtMatchBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * MutchQueryHandler
 *
 * @author dengtianjia
 * @date 2021/8/20
 */
@EsQueryHandle(queryType = QueryType.MATCH)
public class MatchQueryHandler extends AbstractQueryHandler<ExtMatchBean, ExtMatch> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<ExtMatchBean> queryDes, AbstractEsRequestHolder searchHelper) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(queryDes.getField(), queryDes.getValue()).boost(queryDes.getBoost());
        searchHelper.chain(matchQuery);
        return matchQuery;
    }

    @Override
    protected void handleExtConfig(QueryBuilder queryBuilder, EsQueryFieldBean<ExtMatchBean> queryDes) {
        super.annotationMapper(queryDes, ExtMatch.class, queryBuilder);
    }
}
