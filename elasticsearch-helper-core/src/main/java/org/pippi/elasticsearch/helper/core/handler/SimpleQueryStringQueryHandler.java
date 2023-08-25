package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SimpleQueryString;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * SimpleQueryStringQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@EsQueryHandle(SimpleQueryString.class)
public class SimpleQueryStringQueryHandler extends AbstractQueryHandler {

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        SimpleQueryStringBuilder queryBuilder = QueryBuilders.simpleQueryStringQuery(queryDes.getValue().toString());
        queryBuilder.field(queryDes.getField());
        return queryBuilder;
    }
}
