package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MultiMatch;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * MultiMutchQueryHandler
 *  @EsQueryIndex(index = "test_index"， model = QueryModel.bool)
 *  class Param {
 *
 *      @EsQueryField(name = "col1,col2,col3", logicConnector = EsConnector.SHOULD, meta = EsMeta.TEXT, boost = 2.0)
 *      private String userName;
 *
 *  }
 * @author dengtianjia
 * @date 2021/8/20
 */
@EsQueryHandle(MultiMatch.class)
public class MultiMatchQueryHandler extends AbstractQueryHandler{

    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {

        String column = queryDes.getField();
        String[] columns = column.split(_SEPARATOR);

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(queryDes.getValue(), columns)
                .boost(queryDes.getBoost());
        searchHelper.chain(multiMatchQueryBuilder);
        return multiMatchQueryBuilder;
    }

}
