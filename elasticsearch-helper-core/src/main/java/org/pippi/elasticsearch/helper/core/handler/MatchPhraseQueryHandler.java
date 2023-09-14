package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MatchPhrase;
import org.pippi.elasticsearch.helper.model.bean.query.MatchPhraseQueryConf;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(MatchPhrase.class)
public class MatchPhraseQueryHandler extends AbstractQueryHandler<MatchPhraseQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MatchPhraseQueryConf> queryDes, AbstractEsSession searchHelper) {
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery(queryDes.getField(), queryDes.getValue())
                .boost(queryDes.getBoost());
        return matchPhraseQueryBuilder;
    }

}
