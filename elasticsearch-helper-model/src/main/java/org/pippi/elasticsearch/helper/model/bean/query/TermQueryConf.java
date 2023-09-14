package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.TermQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * TermQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class TermQueryConf extends QueryConf<TermQueryBuilder> {

    @Override
    public void configQueryBuilder(TermQueryBuilder queryBuilder) { }
}
