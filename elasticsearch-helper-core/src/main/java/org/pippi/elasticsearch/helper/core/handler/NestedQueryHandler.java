package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.QueryAnnParser;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsQueryBean;
import org.pippi.elasticsearch.helper.core.beans.base.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Nested;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.query.NestedQueryBean;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.List;

/**
 * nested-query need a query-bean #annotation-by
 *    {@link EsQueryBean}
 *    For Example:
 *    @EsQueryIndex(index = "test", model = QueryModel.BOOL)
 *    class SampleParam {
 *        @Ranger
 *        private int score;
 *        @Nested
 *        private InnerParam inner;
 *        @EsQueryIndex(index = "test", model = QueryModel.BOOL)
 *        public static class InnerParam implements EsComplexParam{
 *             @Match
 *             private String name;
 *             @Term
 *             private int age;
 *        }
 *    }
 *
 * @author     JohenTeng
 * @date      2021/9/22
 */
@EsQueryHandle(Nested.class)
public class NestedQueryHandler extends AbstractQueryHandler<NestedQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<NestedQueryBean> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        NestedQueryBean extBean = queryDes.getExtBean();
        QueryBuilder queryBUilder = null;
        if (value.getClass().isAnnotationPresent(EsQueryBean.class)) {
            queryBUilder = this.readNestedQuery(queryDes);
        } else {
            throw new EsHelperQueryException("NestedQuery's field have to be annotation by @EsQueryIndex");
        }
        return QueryBuilders.nestedQuery(extBean.getPath(), queryBUilder, extBean.getScoreMode());
     }


    private QueryBuilder readNestedQuery(EsQueryFieldBean<NestedQueryBean> queryDes) {
        Object value = queryDes.getValue();
        QueryAnnParser annParser = QueryAnnParser.instance();
        EsQueryIndexBean indexInfo = annParser.getIndex(value);
        List<EsQueryFieldBean> paramFieldBeans = annParser.read(value, false);
        AbstractEsSession holder = AbstractEsSession.builder().config(indexInfo).build();
        for (EsQueryFieldBean queryDesCell : paramFieldBeans) {
            String queryKey = queryDesCell.getQueryType();
            AbstractQueryHandler queryHandle = QueryHandlerFactory.getTargetHandleInstance(queryKey);
            queryHandle.execute(queryDesCell, holder);
        }
        return holder.getQueryBuilder().boost(queryDes.getBoost());
    }
}
