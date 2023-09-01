package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.TermsQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;

/**
 * TermsQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class TermsQueryBean extends QueryBean<TermsQueryBuilder> {

    @Override
    public void configQueryBuilder(TermsQueryBuilder queryBuilder) { }
}
