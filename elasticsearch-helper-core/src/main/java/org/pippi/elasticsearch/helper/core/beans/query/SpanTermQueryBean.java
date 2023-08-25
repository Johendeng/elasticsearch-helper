package org.pippi.elasticsearch.helper.core.beans.query;

import org.elasticsearch.index.query.SpanTermQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;

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
