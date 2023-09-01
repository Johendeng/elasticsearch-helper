package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.param.RangeParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range;
import org.pippi.elasticsearch.helper.model.bean.query.RangeQueryBean;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.Objects;
import java.util.Optional;

/**
 * RangeQueryHandler
 *
 * @author     JohenTeng
 * @date      2021/8/23
 */
@EsQueryHandle(Range.class)
public class RangeQueryHandler extends AbstractQueryHandler<RangeQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<RangeQueryBean> queryDes, AbstractEsSession searchHelper) {
        if ( !(queryDes.getValue() instanceof RangeParam) ) {
            throw new EsHelperQueryException("@Range annotation query has to define your Field type be [RangeParam.class]");
        }
        RangeParam rangeParam = (RangeParam) queryDes.getValue();
        if (Objects.isNull(rangeParam.getLeft()) && Objects.isNull(rangeParam.getRight())) {
            return null;
        }
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(queryDes.getField());
        RangeQueryBean rangeBean = queryDes.getExtBean();
        switch (rangeBean.getTag()) {
            case Range.LE_GE:
                Optional.ofNullable(rangeParam.getLeft()).ifPresent(rangeQuery::gte);
                Optional.ofNullable(rangeParam.getRight()).ifPresent(rangeQuery::lte);
                break;
            case Range.L_G:
                Optional.ofNullable(rangeParam.getLeft()).ifPresent(rangeQuery::gt);
                Optional.ofNullable(rangeParam.getRight()).ifPresent(rangeQuery::lt);
                break;
            case Range.LE_G:
                Optional.ofNullable(rangeParam.getLeft()).ifPresent(rangeQuery::gte);
                Optional.ofNullable(rangeParam.getRight()).ifPresent(rangeQuery::lt);
                break;
            case Range.L_GE:
                Optional.ofNullable(rangeParam.getLeft()).ifPresent(rangeQuery::gt);
                Optional.ofNullable(rangeParam.getRight()).ifPresent(rangeQuery::lte);
                break;
            case Range.F_T:
                Optional.ofNullable(rangeParam.getLeft()).ifPresent(rangeQuery::from);
                Optional.ofNullable(rangeParam.getRight()).ifPresent(rangeQuery::to);
                rangeQuery.includeLower(rangeBean.isIncludeLower());
                rangeQuery.includeUpper(rangeBean.isIncludeUpper());
                break;
        }
        return rangeQuery;
    }

}
