package org.pippi.elasticsearch.helper.model.bean.query;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * MatchPhrasePrefixQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
public class MatchPhrasePrefixQueryConf extends QueryConf<MatchPhrasePrefixQueryBuilder> {

    private int slop;

    private int maxExpansions;

    private String analyzer;

    private ZeroTermsQueryOption zeroTermsQuery;

    @Override
    public void configQueryBuilder(MatchPhrasePrefixQueryBuilder queryBuilder) {
        queryBuilder.slop(slop)
                .zeroTermsQuery(zeroTermsQuery)
                .maxExpansions(maxExpansions);
        if (StringUtils.isNotBlank(analyzer)) {
            queryBuilder.analyzer(analyzer);
        }
    }
}
