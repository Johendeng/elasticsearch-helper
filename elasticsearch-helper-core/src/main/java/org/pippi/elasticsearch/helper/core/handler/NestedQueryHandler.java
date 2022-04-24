package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.QueryAnnParser;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Nested;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.NestedQueryBean;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

import java.util.List;

/**
 * nested-query need a query-bean #implements
 *    {@link org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam}
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
    public QueryBuilder handle(EsQueryFieldBean<NestedQueryBean> queryDes, AbstractEsRequestHolder searchHelper) {
        Object value = queryDes.getValue();
        NestedQueryBean extBean = queryDes.getExtBean();
        if (value instanceof EsComplexParam) {
            QueryAnnParser annParser = QueryAnnParser.instance();
            EsQueryIndexBean indexInfo = annParser.getIndex(value);
            List<EsQueryFieldBean> paramFieldBeans = annParser.read(value, false);
            AbstractEsRequestHolder holder = AbstractEsRequestHolder.builder().queryModel(indexInfo.getEsQueryModel()).simpleBuild();
            for (EsQueryFieldBean queryDesCell : paramFieldBeans) {
                String queryKey = queryDesCell.getQueryType();
                AbstractQueryHandler queryHandle = QueryHandlerFactory.getTargetHandleInstance(queryKey);
                queryHandle.execute(queryDesCell, holder);
            }
            QueryBuilder queryBuilder = holder.getQueryBuilder().boost(queryDes.getBoost());
            QueryBuilders.nestedQuery(extBean.getPath(), queryBuilder, extBean.getScoreMode());
            return queryBuilder;
        }
        return null;
    }

}
