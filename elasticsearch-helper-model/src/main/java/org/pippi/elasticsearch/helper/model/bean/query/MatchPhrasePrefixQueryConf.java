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

    private int slop = 0;

    private int maxExpansions = 50;

    private String analyzer;

    private ZeroTermsQueryOption zeroTermsQuery = ZeroTermsQueryOption.NONE;

    public static MatchPhrasePrefixQueryConf build() {
        return new MatchPhrasePrefixQueryConf();
    }

    @Override
    public void configQueryBuilder(MatchPhrasePrefixQueryBuilder queryBuilder) {
        queryBuilder.slop(slop)
                .zeroTermsQuery(zeroTermsQuery)
                .maxExpansions(maxExpansions);
        if (StringUtils.isNotBlank(analyzer)) {
            queryBuilder.analyzer(analyzer);
        }
    }

    public int slop() {
        return slop;
    }

    public MatchPhrasePrefixQueryConf setSlop(int slop) {
        this.slop = slop;
        return this;
    }

    public int maxExpansions() {
        return maxExpansions;
    }

    public MatchPhrasePrefixQueryConf setMaxExpansions(int maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    public String analyzer() {
        return analyzer;
    }

    public MatchPhrasePrefixQueryConf setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public ZeroTermsQueryOption zeroTermsQuery() {
        return zeroTermsQuery;
    }

    public MatchPhrasePrefixQueryConf setZeroTermsQuery(ZeroTermsQueryOption zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
        return this;
    }
}
