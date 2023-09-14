package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Gte;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.RangeQueryConf;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
@EsQueryHandle(Gte.class)
public class GteQueryHandler extends AbstractQueryHandler<RangeQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<RangeQueryConf> queryDes, AbstractEsSession searchHelper) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(queryDes.getField());
        rangeQuery.gte(queryDes.getValue());
        return rangeQuery;
    }
}
