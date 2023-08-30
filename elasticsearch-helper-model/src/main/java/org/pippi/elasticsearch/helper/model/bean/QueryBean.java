package org.pippi.elasticsearch.helper.model.bean;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * Define base-query-information
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public abstract class QueryBean<T extends QueryBuilder> {

    /**
     * use this extend-config-bean to config given @QueryBuilder
     * @param queryBuilder (ex: MatchQueryBuilder, MultiMatchQuery ...)
     */
    public abstract void configQueryBuilder(T queryBuilder);
}
