package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MoreLikeThis;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.MoreLikeThisQueryBean;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * MoreLikeThisQueryHandler
 *
 * @author JohenTeng
 * @date 2021/9/28
 */
@EsQueryHandle(MoreLikeThis.class)
public class MoreLikeThisQueryHandler extends AbstractQueryHandler<MoreLikeThisQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MoreLikeThisQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        Object value = queryDes.getValue();
        String[] fields = queryDes.getExtBean().getFields();
        if (value.getClass().equals(MoreLikeThisParam.class)) {
            MoreLikeThisParam params = (MoreLikeThisParam) value;
            MoreLikeThisQueryBuilder moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(
                    ArrayUtils.isEmpty(fields) ? null : fields,
                    params.getTexts(), params.getItemsSupplier().get());
            return moreLikeThisQueryBuilder;
        } else {
            throw new EsHelperQueryException("@MoreLikeThis query's Field value have to be MoreLikeThisParam.class");
        }
    }
}
