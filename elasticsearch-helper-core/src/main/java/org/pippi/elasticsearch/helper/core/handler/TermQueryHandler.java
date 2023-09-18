package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Term;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.TermQueryConf;

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
 * @author      JohenTeng
 * @date      2021/8/17
 */
@EsQueryHandle(Term.class)
public class TermQueryHandler extends AbstractQueryHandler<TermQueryConf> {

    /**
     *
     * @param queryDes
     * @param searchHelper
     * return
     */
    @Override
    public QueryBuilder handle(EsQueryFieldBean queryDes, AbstractEsSession searchHelper) {
        return QueryBuilders.termQuery(queryDes.getField(), queryDes.getValue());
    }

}
