package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.WildCard;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.WildCardQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * WildCardQueryHandler
 *
 * author     JohenTeng
 * date      2021/9/24
 */
@EsQueryHandle(WildCard.class)
public class WildCardQueryHandler extends AbstractQueryHandler<WildCardQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<WildCardQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        String value = queryDes.getValue().toString();
        WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery(queryDes.getField(), value);
        queryBuilder.boost(queryDes.getBoost());
        searchHelper.chain(queryBuilder);
        return queryBuilder;
    }

}
