package org.pippi.elasticsearch.helper.model.bean.query;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * MatchPhraseQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
public class MatchPhraseQueryConf extends QueryConf<MatchPhraseQueryBuilder> {

    private int slop = 0;

    private ZeroTermsQueryOption zeroTermsQuery = ZeroTermsQueryOption.NONE;

    private String analyzer;

    public static MatchPhraseQueryConf build() {
        return new MatchPhraseQueryConf();
    }

    @Override
    public void configQueryBuilder(MatchPhraseQueryBuilder queryBuilder) {
        queryBuilder.slop(slop).zeroTermsQuery(zeroTermsQuery);
        if (StringUtils.isNotBlank(analyzer)) {
            queryBuilder.analyzer(analyzer);
        }
    }

    public int slop() {
        return slop;
    }

    public MatchPhraseQueryConf setSlop(int slop) {
        this.slop = slop;
        return this;
    }

    public ZeroTermsQueryOption zeroTermsQuery() {
        return zeroTermsQuery;
    }

    public MatchPhraseQueryConf setZeroTermsQuery(ZeroTermsQueryOption zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
        return this;
    }

    public String analyzer() {
        return analyzer;
    }

    public MatchPhraseQueryConf setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }
}
