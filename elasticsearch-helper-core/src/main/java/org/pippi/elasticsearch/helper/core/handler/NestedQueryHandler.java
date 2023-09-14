package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.QueryAnnParser;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.pippi.elasticsearch.helper.core.session.BoolEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Nested;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.NestedQueryConf;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.List;

/**
 * nested-query need a query-bean #annotation-by
 *    {@link EsAnnQueryIndex}
 *    For Example:
 *    @EsQueryIndex(index = "test", model = QueryModel.BOOL)
 *    class SampleParam {
 *        @Ranger
 *        private int score;
 *        @Nested
 *        private InnerParam inner;

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
public class NestedQueryHandler extends AbstractQueryHandler<NestedQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<NestedQueryConf> queryDes, AbstractEsSession searchHelper) {
        NestedQueryConf extBean = queryDes.getExtBean();
        Object value = queryDes.getValue();
        List<EsQueryFieldBean> paramFieldBeans = null;
        if (value != null) {
            QueryAnnParser annParser = QueryAnnParser.instance();
            paramFieldBeans = annParser.read(extBean.getPath(), value);
        } else {
            /**
             * xxx: 为了适配 lambda 类型的查询，进行该判断，
             *
             */
            paramFieldBeans = queryDes.getNestedQueryDesList();
        }
        if (CollectionUtils.isEmpty(paramFieldBeans)) {
            return null;
        }
        BoolEsSession session = BoolEsSession.buildSimpleSession();
        for (EsQueryFieldBean queryDesCell : paramFieldBeans) {
            String queryKey = queryDesCell.getQueryType();
            AbstractQueryHandler queryHandle = QueryHandlerFactory.getTargetHandleInstance(queryKey);
            queryHandle.execute(queryDesCell, session);
        }
        QueryBuilder queryBUilder = session.getQueryBuilder().boost(queryDes.getBoost());
        return QueryBuilders.nestedQuery(extBean.getPath(), queryBUilder, extBean.getScoreMode());
     }
}
