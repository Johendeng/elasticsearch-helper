package org.pippi.elasticsearch.helper.core.holder;

import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DisMaxEsRequestHolder
 *
 * author     JohenTeng
 * date      2021/9/29
 */
public class DisMaxEsRequestHolder extends AbstractEsRequestHolder<DisMaxQueryBuilder> {

    @Override
    public AbstractEsRequestHolder changeLogicConnector(EsConnector connector) {
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
