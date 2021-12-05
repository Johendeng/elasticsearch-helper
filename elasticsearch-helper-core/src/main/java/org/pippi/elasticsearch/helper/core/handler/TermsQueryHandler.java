package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Terms;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.TermsQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * TermsQueryHandler
 *
 * author     JohenTeng
 * date      2021/9/27
 */
@EsQueryHandle(Terms.class)
public class TermsQueryHandler extends AbstractQueryHandler<TermsQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<TermsQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery(queryDes.getField(), queryDes.getValue())
                .boost(queryDes.getBoost());
        searchHelper.chain(queryBuilder);
        return queryBuilder;
    }

}
