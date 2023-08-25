package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.param.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MoreLikeThis;
import org.pippi.elasticsearch.helper.core.beans.query.MoreLikeThisQueryBean;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(MoreLikeThis.class)
public class MoreLikeThisQueryHandler extends AbstractQueryHandler<MoreLikeThisQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MoreLikeThisQueryBean> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        String[] fields = queryDes.getExtBean().getFields();
        if (value.getClass().equals(MoreLikeThisParam.class)) {
            MoreLikeThisParam params = (MoreLikeThisParam) value;
            MoreLikeThisQueryBuilder moreLikeThisQueryBuilder = null;
            if (ArrayUtils.isEmpty(params.getTexts())) {
                throw new EsHelperQueryException("likeTexts/fields can't be empty");
            }
            if (ArrayUtils.isEmpty(fields)) {
                moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(params.getTexts(), params.getItems());
            } else {
                moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(fields, params.getTexts(), params.getItems());
            }
            return moreLikeThisQueryBuilder;
        } else {
            throw new EsHelperQueryException("@MoreLikeThis query's Field value have to be MoreLikeThisParam.class");
        }
    }
}
