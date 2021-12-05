package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Term;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.TermQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

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
 * author      JohenTeng
 * date      2021/8/17
 */
@EsQueryHandle(Term.class)
public class TermQueryHandler extends AbstractQueryHandler<TermQueryBean> {

    /**
     *
     * @param queryDes
     * @param searchHelper
     * return
     */
    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsRequestHolder searchHelper) {
        QueryBuilder termQueryBuilder = QueryBuilders.termQuery(queryDes.getField(), queryDes.getValue());
        termQueryBuilder.boost(queryDes.getBoost());
        searchHelper.chain(termQueryBuilder);
        return termQueryBuilder;
    }

}
