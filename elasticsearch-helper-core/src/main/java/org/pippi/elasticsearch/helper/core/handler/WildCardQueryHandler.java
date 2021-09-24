package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * WildCardQueryHandler
 *
 * @author JohenTeng
 * @date 2021/9/24
 */
@EsQueryHandle(queryType = QueryType.WILD_CARD)
public class WildCardQueryHandler extends AbstractQueryHandler{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {
        return null;
    }
}
