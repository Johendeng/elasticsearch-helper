package org.pippi.elasticsearch.helper.core.session;

import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * DisMaxEsRequestHolder
 *  分离最大化查询（Disjunction Max Query） 。分离（Disjunction）的意思是 或（or） ，
 *  这与可以把结合（conjunction）理解成 与（and） 相对应。分离最大化查询（Disjunction Max Query）指的是：
 *  将任何与任一查询匹配的文档作为结果返回，但只将最佳匹配的评分作为查询的评分结果返回
 *
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
@SuppressWarnings("all")
public class DisMaxEsSession extends AbstractEsSession<DisMaxQueryBuilder> {

    @Override
    public AbstractEsSession changeLogicConnector(EsConnector connector) {
        return this;
    }

    @Override
    protected void defineDefaultLogicConnector() {
        super.setCurrentQueryBuilderList(super.getQueryBuilder().innerQueries());
    }

    @Override
    protected void defineQueryBuilder() {
        super.setQueryBuilder(QueryBuilders.disMaxQuery());
    }
}
