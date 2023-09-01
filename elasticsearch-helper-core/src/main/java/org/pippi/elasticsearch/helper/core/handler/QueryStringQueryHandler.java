package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.QueryString;
import org.pippi.elasticsearch.helper.model.bean.query.QueryStringBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * query all field contains 'queryString' 's docs
 *
 * @author JohenTeng
 * @date 2022/4/29
 */
@EsQueryHandle(QueryString.class)
public class QueryStringQueryHandler extends AbstractQueryHandler<QueryStringBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(queryDes.getValue().toString());
        queryBuilder.field(queryDes.getField());
        return queryBuilder;
    }
}
