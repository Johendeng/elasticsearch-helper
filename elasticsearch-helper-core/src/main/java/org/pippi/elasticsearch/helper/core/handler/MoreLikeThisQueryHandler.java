package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.param.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MoreLikeThis;
import org.pippi.elasticsearch.helper.model.bean.query.MoreLikeThisQueryConf;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author     JohenTeng
 * @date      2021/9/28
 */
@EsQueryHandle(MoreLikeThis.class)
public class MoreLikeThisQueryHandler extends AbstractQueryHandler<MoreLikeThisQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MoreLikeThisQueryConf> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        String[] fields = queryDes.getExtBean().fields();
        if (value.getClass().equals(MoreLikeThisParam.class)) {
            MoreLikeThisParam params = (MoreLikeThisParam) value;
            MoreLikeThisQueryBuilder moreLikeThisQueryBuilder = null;
            if (ArrayUtils.isEmpty(params.getTexts())) {
                throw new EsHelperQueryException("likeTexts/fields can't be empty");
            }
            if (ArrayUtils.isEmpty(fields)) {
                if (ArrayUtils.isNotEmpty(params.getTexts()) && ArrayUtils.isNotEmpty(params.getItems())) {
                    moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(params.getTexts(), params.getItems());
                } else if (ArrayUtils.isNotEmpty(params.getTexts()) && ArrayUtils.isEmpty(params.getItems())) {
                    moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(params.getTexts());
                } else if (ArrayUtils.isNotEmpty(params.getItems()) && ArrayUtils.isEmpty(params.getTexts())) {
                    moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(params.getItems());
                }
            } else {
                moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(fields, params.getTexts(), params.getItems());
            }
            return moreLikeThisQueryBuilder;
        } else {
            throw new EsHelperQueryException("@MoreLikeThis query's Field value have to be MoreLikeThisParam.class");
        }
    }
}
