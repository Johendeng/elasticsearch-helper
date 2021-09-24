package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * RangeQueryHandler
 *
 * @author JohenTeng
 * @date 2021/8/23
 */
@EsQueryHandle(queryType = QueryType.RANGE)
public class RangeQueryHandler extends AbstractQueryHandler{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {

        return null;
    }
}
