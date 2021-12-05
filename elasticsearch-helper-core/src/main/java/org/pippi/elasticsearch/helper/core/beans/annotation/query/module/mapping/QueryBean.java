package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

/**
 * Define base-query-information
 *
 * author     JohenTeng
 * date      2021/9/24
 */
import org.elasticsearch.index.query.QueryBuilder;

public abstract class QueryBean<T extends QueryBuilder> {



    /**
     * use this extend-config-bean to config given @QueryBuilder
     * @param queryBuilder (ex: MatchQueryBuilder, MultiMatchQuery ...)
     */
    public abstract void configQueryBuilder(T queryBuilder);



}
