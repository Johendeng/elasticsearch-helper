package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * WildCardQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/27
 */
public class WildCardQueryConf extends QueryConf<WildcardQueryBuilder> {

    private Boolean caseInsensitive;

    @Override
    public void configQueryBuilder(WildcardQueryBuilder queryBuilder) {
        queryBuilder.caseInsensitive(caseInsensitive);
    }

}
