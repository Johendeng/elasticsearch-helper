package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Fuzzy;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.FuzzyQueryConf;

/**
 * @author     JohenTeng
 * @date      2021/9/22
 */
@EsQueryHandle(Fuzzy.class)
public class FuzzyQueryHandler extends AbstractQueryHandler<FuzzyQueryConf>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        return QueryBuilders.fuzzyQuery(queryDes.getField(), queryDes.getValue());
    }
}
