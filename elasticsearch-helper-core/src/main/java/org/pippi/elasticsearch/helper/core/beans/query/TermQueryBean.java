package org.pippi.elasticsearch.helper.core.beans.query;

import org.elasticsearch.index.query.TermQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;

/**
 * TermQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class TermQueryBean extends QueryBean<TermQueryBuilder> {

    @Override
    public void configQueryBuilder(TermQueryBuilder queryBuilder) { }
}
