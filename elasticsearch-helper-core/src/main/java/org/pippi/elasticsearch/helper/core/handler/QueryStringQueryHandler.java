package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.QueryString;
import org.pippi.elasticsearch.helper.model.bean.query.QueryStringConf;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.Objects;

/**
 * query all field contains 'queryString' 's docs
 *
 * @author JohenTeng
 * @date 2022/4/29
 */
@EsQueryHandle(QueryString.class)
public class QueryStringQueryHandler extends AbstractQueryHandler<QueryStringConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(queryDes.getValue().toString());
        if (Objects.nonNull(queryDes.getField())) {
            queryBuilder.field(queryDes.getField());
        }
        return queryBuilder;
    }
}
