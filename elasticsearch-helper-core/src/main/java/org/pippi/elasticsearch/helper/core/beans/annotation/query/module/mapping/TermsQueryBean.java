package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.TermsQueryBuilder;

/**
 * TermsQueryBean
 *
 * @author JohenTeng
 * @date 2021/9/24
 */
public class TermsQueryBean extends QueryBean<TermsQueryBuilder>{

    @Override
    public void configQueryBuilder(TermsQueryBuilder queryBuilder) { }
}
