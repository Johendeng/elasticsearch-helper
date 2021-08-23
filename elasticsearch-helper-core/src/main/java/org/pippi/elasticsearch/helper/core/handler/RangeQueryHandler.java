package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * RangeQueryHandler
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/23
 */
@EsQueryHandle(handleEnum = QueryType.RANGE)
public class RangeQueryHandler extends AbstractQueryHandler{

    @Override
    public AbstractEsRequestHolder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {

        return null;
    }
}
