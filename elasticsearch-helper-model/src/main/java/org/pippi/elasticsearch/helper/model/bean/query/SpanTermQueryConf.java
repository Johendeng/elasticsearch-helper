package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.SpanTermQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * SpanTermQueryBuilder
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
public class SpanTermQueryConf extends QueryConf<SpanTermQueryBuilder> {

    @Override
    public void configQueryBuilder(SpanTermQueryBuilder queryBuilder) {
    }

}
