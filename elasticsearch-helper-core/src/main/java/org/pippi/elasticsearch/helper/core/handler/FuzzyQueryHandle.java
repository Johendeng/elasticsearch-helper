package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * FuzzyQueryHandle
 *
 * @author JohenTeng
 * @date 2021/9/22
 */
@EsQueryHandle(queryType = QueryType.FUZZY)
public class FuzzyQueryHandle extends AbstractQueryHandler{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {


        return null;
    }
}
