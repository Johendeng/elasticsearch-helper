package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.SpanTermQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.SpanTerm;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.SpanTermQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * SpanTermQueryHandler
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(SpanTerm.class)
public class SpanTermQueryHandler extends AbstractQueryHandler<SpanTermQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<SpanTermQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        SpanTermQueryBuilder spanTermQueryBuilder = new SpanTermQueryBuilder(queryDes.getField(), queryDes.getValue());
        spanTermQueryBuilder.boost(queryDes.getBoost());
        return spanTermQueryBuilder;
    }
}
