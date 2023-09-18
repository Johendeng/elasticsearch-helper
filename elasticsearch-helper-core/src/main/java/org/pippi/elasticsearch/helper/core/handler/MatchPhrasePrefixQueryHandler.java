package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MatchPhrasePrefix;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.MatchPhrasePrefixQueryConf;

/**
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(MatchPhrasePrefix.class)
public class MatchPhrasePrefixQueryHandler extends AbstractQueryHandler<MatchPhrasePrefixQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MatchPhrasePrefixQueryConf> queryDes, AbstractEsSession searchHelper) {
        return QueryBuilders.matchPhrasePrefixQuery(queryDes.getField(), queryDes.getValue());
    }
}
