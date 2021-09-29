package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.WildcardQueryBuilder;

/**
 * WildCardQueryBean
 *
 * @author JohenTeng
 * @date 2021/9/27
 */
public class WildCardQueryBean extends QueryBean<WildcardQueryBuilder> {

    @Override
    public void configQueryBuilder(WildcardQueryBuilder queryBuilder) {
    }

}
