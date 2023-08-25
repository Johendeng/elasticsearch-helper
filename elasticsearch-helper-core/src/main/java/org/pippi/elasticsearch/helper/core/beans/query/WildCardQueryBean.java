package org.pippi.elasticsearch.helper.core.beans.query;

import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;

/**
 * WildCardQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/27
 */
public class WildCardQueryBean extends QueryBean<WildcardQueryBuilder> {

    private Boolean caseInsensitive;

    @Override
    public void configQueryBuilder(WildcardQueryBuilder queryBuilder) {
        queryBuilder.caseInsensitive(caseInsensitive);
    }

}
