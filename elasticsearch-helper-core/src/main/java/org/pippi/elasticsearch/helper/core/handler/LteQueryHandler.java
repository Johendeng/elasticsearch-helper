package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Lte;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.RangeQueryBean;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
@EsQueryHandle(Lte.class)
public class LteQueryHandler extends AbstractQueryHandler<RangeQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<RangeQueryBean> queryDes, AbstractEsSession searchHelper) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(queryDes.getField());
        rangeQuery.lte(queryDes.getValue());
        return rangeQuery;
    }
}
