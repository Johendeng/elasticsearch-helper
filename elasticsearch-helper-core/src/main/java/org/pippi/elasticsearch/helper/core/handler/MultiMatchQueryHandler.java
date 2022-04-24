package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MultiMatch;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.MultiMatchQueryBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * @author      JohenTeng
 * @date      2021/8/20
 */
@EsQueryHandle(MultiMatch.class)
public class MultiMatchQueryHandler extends AbstractQueryHandler<MultiMatchQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<MultiMatchQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        String[] fields = queryDes.getExtBean().getFields();
        if (ArrayUtils.isEmpty(fields)) {
            return null;
        }
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(queryDes.getValue(),fields)
                .boost(queryDes.getBoost());
        searchHelper.chain(multiMatchQueryBuilder);
        return multiMatchQueryBuilder;
    }
}
