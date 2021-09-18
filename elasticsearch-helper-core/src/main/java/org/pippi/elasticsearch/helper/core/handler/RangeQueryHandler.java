package org.pippi.elasticsearch.helper.core.handler;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * RangeQueryHandler
 *
 * @author JohenTeng
 * @date 2021/8/23
 */
@EsQueryHandle(handleEnum = QueryType.RANGE)
public class RangeQueryHandler extends AbstractQueryHandler{

    @Override
    public AbstractEsRequestHolder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {

        return searchHelper;
    }
}
