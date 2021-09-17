package org.pippi.elasticsearch.helper.core.handler;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * MutchQueryHandler
 *
 * @author dengtianjia
 * @date 2021/8/20
 */
@EsQueryHandle(handleEnum = QueryType.MATCH)
public class MatchQueryHandler extends AbstractQueryHandler {

    @Override
    public AbstractEsRequestHolder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {



        return null;
    }
}
