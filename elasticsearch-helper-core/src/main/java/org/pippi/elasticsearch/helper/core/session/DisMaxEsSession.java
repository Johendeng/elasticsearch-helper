package org.pippi.elasticsearch.helper.core.session;

import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * DisMaxEsRequestHolder
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
