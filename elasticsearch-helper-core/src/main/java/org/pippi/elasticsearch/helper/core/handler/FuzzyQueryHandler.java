package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Fuzzy;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.FuzzyQueryBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author     JohenTeng
 * @date      2021/9/22
 */
@EsQueryHandle(Fuzzy.class)
public class FuzzyQueryHandler extends AbstractQueryHandler<FuzzyQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery(queryDes.getField(), queryDes.getValue())
                .boost(queryDes.getBoost());
        return fuzzyQueryBuilder;
    }
}
