package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author: JohenTeng
 * @date: 2022/8/12
 **/
@EsQueryHandle(queryType = "blank")
public class BlankQueryHandler extends AbstractQueryHandler{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        return null;
    }
}
