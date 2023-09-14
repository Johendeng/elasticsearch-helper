package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.TermsQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * TermsQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class TermsQueryConf extends QueryConf<TermsQueryBuilder> {

    @Override
    public void configQueryBuilder(TermsQueryBuilder queryBuilder) { }
}
