package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Terms;
import org.pippi.elasticsearch.helper.model.bean.query.TermsQueryConf;
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
public class TermsQueryHandler extends AbstractQueryHandler<TermsQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<TermsQueryConf> queryDes, AbstractEsSession searchHelper) {
        Collection value = ReflectionUtils.transArrayOrCollection(queryDes.getValue());
        return QueryBuilders.termsQuery(queryDes.getField(), value);
    }
}
