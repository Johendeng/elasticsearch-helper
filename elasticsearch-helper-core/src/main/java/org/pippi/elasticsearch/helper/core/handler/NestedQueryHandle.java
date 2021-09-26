package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Nested;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * NestedQueryHandle
 * TODO IT'S DIFFERENT FROM OTHERS
 * @author JohenTeng
 * @date 2021/9/22
 */
@EsQueryHandle(Nested.class)
public class NestedQueryHandle extends AbstractQueryHandler {


    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {


        return null;
    }
}
