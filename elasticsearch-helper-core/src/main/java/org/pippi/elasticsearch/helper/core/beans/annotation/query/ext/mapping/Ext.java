package org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.mapping;

/**
 * Ext
 *
 * @author JohenTeng
 * @date 2021/9/24
 */
import org.elasticsearch.index.query.QueryBuilder;

public abstract class Ext<T extends QueryBuilder> {

    /**
     * use this extend-config-bean to config given @QueryBuilder
     * @param queryBuilder (ex: MatchQueryBuilder)
     */
    public abstract void configQueryBuilder(T queryBuilder);

}
