package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Terms;
import org.pippi.elasticsearch.helper.core.beans.query.TermsQueryBean;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.utils.ReflectionUtils;

import java.util.Collection;

/**
 * TermsQueryHandler
 *
 * @author     JohenTeng
 * @date      2021/9/27
 */
@EsQueryHandle(Terms.class)
public class TermsQueryHandler extends AbstractQueryHandler<TermsQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<TermsQueryBean> queryDes, AbstractEsSession searchHelper) {
        Collection value = ReflectionUtils.transArrayOrCollection(queryDes.getValue());
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery(queryDes.getField(), value)
                .boost(queryDes.getBoost());
        return queryBuilder;
    }
}
