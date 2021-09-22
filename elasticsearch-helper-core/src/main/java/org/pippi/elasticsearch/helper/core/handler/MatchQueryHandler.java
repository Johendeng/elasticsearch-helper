package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

import java.util.Objects;

/**
 * MutchQueryHandler
 *
 * @author dengtianjia
 * @date 2021/8/20
 */
@EsQueryHandle(handleEnum = QueryType.MATCH)
public class MatchQueryHandler extends AbstractQueryHandler {

    @Override
    public AbstractEsRequestHolder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(queryDes.getField(), queryDes.getValue());
        Float boost = queryDes.getBoost();
        if (Objects.nonNull(boost)) {
            matchQuery.boost(boost);
        }
        searchHelper.chain(matchQuery);
        return searchHelper;
    }

    @Override
    protected EsQueryFieldBean explainExtendDefine(EsQueryFieldBean queryDes) {
        return super.explainExtendDefine(queryDes);
    }

}
