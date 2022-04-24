package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.SpanTermQueryBuilder;

/**
 * SpanTermQueryBuilder
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
public class SpanTermQueryBean extends QueryBean<SpanTermQueryBuilder> {

    @Override
    public void configQueryBuilder(SpanTermQueryBuilder queryBuilder) {
    }

}
