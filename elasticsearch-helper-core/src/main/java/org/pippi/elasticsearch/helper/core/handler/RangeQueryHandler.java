package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.RangeQueryBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

import java.util.Objects;
import java.util.Optional;

/**
 * RangeQueryHandler
 *
 * author     JohenTeng
 * date      2021/8/23
 */
@EsQueryHandle(Range.class)
public class RangeQueryHandler extends AbstractQueryHandler<RangeQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<RangeQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        RangeParam rangeParam = (RangeParam) queryDes.getValue();
        if (Objects.isNull(rangeParam.getLeft()) && Objects.isNull(rangeParam.getRight())) {
            return null;
        }
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(queryDes.getField());
        RangeQueryBean rangeBean = queryDes.getExtBean();
        if (rangeBean.getTag().equals(Range.LE_GE)) {
            Optional.ofNullable(rangeParam.getLeft()).ifPresent(l -> rangeQuery.gte(l));
            Optional.ofNullable(rangeParam.getRight()).ifPresent(r -> rangeQuery.lte(r));
        } else if (rangeBean.getTag().equals(Range.L_G)) {
            Optional.ofNullable(rangeParam.getLeft()).ifPresent(l -> rangeQuery.gt(l));
            Optional.ofNullable(rangeParam.getRight()).ifPresent(r -> rangeQuery.lt(r));
        } else if (rangeBean.getTag().equals(Range.LE_G)) {
            Optional.ofNullable(rangeParam.getLeft()).ifPresent(l -> rangeQuery.gte(l));
            Optional.ofNullable(rangeParam.getRight()).ifPresent(r -> rangeQuery.lt(r));
        } else if (rangeBean.getTag().equals(Range.L_GE)) {
            Optional.ofNullable(rangeParam.getLeft()).ifPresent(l -> rangeQuery.gt(l));
            Optional.ofNullable(rangeParam.getRight()).ifPresent(r -> rangeQuery.lte(r));
        } else if (rangeBean.getTag().equals(Range.F_T)) {
            Optional.ofNullable(rangeParam.getLeft()).ifPresent(l -> rangeQuery.from(l));
            Optional.ofNullable(rangeParam.getRight()).ifPresent(r -> rangeQuery.to(r));
        }
        searchHelper.chain(rangeQuery);
        return rangeQuery;
    }

}
