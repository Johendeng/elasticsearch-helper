package org.pippi.elasticsearch.helper.core.handler;

import org.apache.http.util.Asserts;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;
import org.pippi.elasticsearch.helper.core.beans.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

import java.util.Objects;

/**
 * TermQueryHandle
 *
 *  @EsQueryIndex(index = "test_index"， model = QueryModel.bool)
 *  class Param {
 *
 *      @EsQueryField(name = "user_name", logicConnector = EsConnector.SHOULD, meta = EsMeta.KEYWORD, boost = 2.0)
 *      private String userName;
 *
 *  }
 * @author dengtianjia
 * @date 2021/8/17
 */
@EsQueryHandle(handleEnum = QueryType.TERM)
public class TermQueryHandler extends AbstractQueryHandler {

    /**
     *
     * @param queryDes
     * @param searchHelper
     * @return
     */
    @Override
    public AbstractEsRequestHolder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {
        QueryBuilder termQueryBuilder = null;
        if (Objects.nonNull(queryDes.getValue())) {
            termQueryBuilder = QueryBuilders.termQuery(queryDes.getColumn(), queryDes.getValue());
        }
        if (Objects.nonNull(queryDes.getValues())) {
            termQueryBuilder = QueryBuilders.termsQuery(queryDes.getColumn(), queryDes.getValues());
        }
        if (Objects.nonNull(queryDes.getBoost())) {
            termQueryBuilder.boost(queryDes.getBoost());
        }
        Asserts.notNull(termQueryBuilder, "un-generate useful termQuery");
        searchHelper.chain(termQueryBuilder);
        return searchHelper;
    }

}
