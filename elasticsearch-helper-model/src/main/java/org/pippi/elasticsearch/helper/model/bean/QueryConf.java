package org.pippi.elasticsearch.helper.model.bean;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;

/**
 * Define base-query-information
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public abstract class QueryConf<T extends QueryBuilder> implements Serializable {

    /**
     * use this extend-config-bean to config given @QueryBuilder
     * @param queryBuilder (ex: MatchQueryBuilder, MultiMatchQuery ...)
     */
    public abstract void configQueryBuilder(T queryBuilder);
}
