package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
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
@EsQueryHandle(handleEnum = QueryType.MULTI_MATCH)
public class MultiMatchQueryHandler extends AbstractQueryHandler{

    private static final String _SEPARATOR = ",";

    @Override
    public AbstractEsRequestHolder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {

        String column = queryDes.getColumn();
        String[] columns = column.split(_SEPARATOR);

        Object value = queryDes.getValue();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(value, columns);
        if (queryDes.getBoost() != null) {
            multiMatchQueryBuilder.boost(queryDes.getBoost());
        }

        searchHelper.chain(multiMatchQueryBuilder);
        return searchHelper;
    }

}
