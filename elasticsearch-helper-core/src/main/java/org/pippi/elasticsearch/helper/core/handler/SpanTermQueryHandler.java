package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.SpanTermQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SpanTerm;
import org.pippi.elasticsearch.helper.model.bean.query.SpanTermQueryBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * SpanTermQueryHandler
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(SpanTerm.class)
public class SpanTermQueryHandler extends AbstractQueryHandler<SpanTermQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<SpanTermQueryBean> queryDes, AbstractEsSession searchHelper) {
        SpanTermQueryBuilder spanTermQueryBuilder = new SpanTermQueryBuilder(queryDes.getField(), queryDes.getValue());
        spanTermQueryBuilder.boost(queryDes.getBoost());
        return spanTermQueryBuilder;
    }
}
