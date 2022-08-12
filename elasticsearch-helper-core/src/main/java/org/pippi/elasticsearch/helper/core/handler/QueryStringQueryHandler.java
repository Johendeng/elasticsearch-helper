package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.QueryString;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.QueryStringBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * query all field contains 'queryString' 's docs
 *
 * @author JohenTeng
 * @date 2022/4/29
 */
@EsQueryHandle(QueryString.class)
public class QueryStringQueryHandler extends AbstractQueryHandler<QueryStringBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(queryDes.getValue().toString());
        queryBuilder.field(queryDes.getField());
        return queryBuilder;
    }
}
