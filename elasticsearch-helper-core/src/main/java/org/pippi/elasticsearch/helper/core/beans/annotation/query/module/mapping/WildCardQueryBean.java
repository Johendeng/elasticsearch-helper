package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.WildcardQueryBuilder;

/**
 * WildCardQueryBean
 *
 * @author dengtianjia@fiture.com
 * @date 2021/9/27
 */
public class WildCardQueryBean extends QueryBean<WildcardQueryBuilder> {

    @Override
    public void configQueryBuilder(WildcardQueryBuilder queryBuilder) {
    }
}
