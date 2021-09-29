package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.TermQueryBuilder;

/**
 * TermQueryBean
 *
 * @author JohenTeng
 * @date 2021/9/24
 */
public class TermQueryBean extends QueryBean<TermQueryBuilder>{

    @Override
    public void configQueryBuilder(TermQueryBuilder queryBuilder) { }
}
