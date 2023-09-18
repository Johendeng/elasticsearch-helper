package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.WildCard;
import org.pippi.elasticsearch.helper.model.bean.query.WildCardQueryConf;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * WildCardQueryHandler
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
@EsQueryHandle(WildCard.class)
public class WildCardQueryHandler extends AbstractQueryHandler<WildCardQueryConf>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<WildCardQueryConf> queryDes, AbstractEsSession searchHelper) {
        String value = queryDes.getValue().toString();
        return QueryBuilders.wildcardQuery(queryDes.getField(), value);
    }
}
