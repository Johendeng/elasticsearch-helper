package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MatchPhrasePrefix;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.MatchPhrasePrefixQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(MatchPhrasePrefix.class)
public class MatchPhrasePrefixQueryHandler extends AbstractQueryHandler<MatchPhrasePrefixQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MatchPhrasePrefixQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder = QueryBuilders.matchPhrasePrefixQuery(
                queryDes.getField(), queryDes.getValue()).boost(queryDes.getBoost());
        searchHelper.chain(matchPhrasePrefixQueryBuilder);
        return matchPhrasePrefixQueryBuilder;
    }
}
