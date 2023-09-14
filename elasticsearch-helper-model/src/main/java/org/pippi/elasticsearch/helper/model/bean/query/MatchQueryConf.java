package org.pippi.elasticsearch.helper.model.bean.query;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;

/**
 * ExtMatchBean
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class MatchQueryConf extends QueryConf<MatchQueryBuilder> {

    private int prefixLength = 0;

    private int maxExpansions = 50;

    private boolean fuzzyTranspositions = true;

    private boolean lenient = false;

    private boolean autoGenerateSynonymsPhraseQuery = true;

    private FuzzinessEnum fuzziness = FuzzinessEnum.AUTO;

    private ZeroTermsQueryOption zerTermsQuery = ZeroTermsQueryOption.NONE;

    private String analyzer = "";


    public static MatchQueryConf build() {
        return new MatchQueryConf();
    }

    @Override
    public void configQueryBuilder(MatchQueryBuilder match) {
        match.prefixLength(this.prefixLength)
             .maxExpansions(this.maxExpansions)
             .fuzzyTranspositions(this.fuzzyTranspositions)
             .lenient(this.lenient)
             .autoGenerateSynonymsPhraseQuery(this.autoGenerateSynonymsPhraseQuery)
             .fuzziness(this.fuzziness.getFuzziness())
             .zeroTermsQuery(zerTermsQuery);
        if (StringUtils.isNotBlank(analyzer)) {
            match.analyzer(analyzer);
        }
    }


    public int getPrefixLength() {
        return prefixLength;
    }

    public MatchQueryConf setPrefixLength(int prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public int getMaxExpansions() {
        return maxExpansions;
    }

    public MatchQueryConf setMaxExpansions(int maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    public boolean isFuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public MatchQueryConf setFuzzyTranspositions(boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
        return this;
    }

    public boolean isLenient() {
        return lenient;
    }

    public MatchQueryConf setLenient(boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public boolean isAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public MatchQueryConf setAutoGenerateSynonymsPhraseQuery(boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
        return this;
    }

    public FuzzinessEnum getFuzziness() {
        return fuzziness;
    }

    public MatchQueryConf setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public ZeroTermsQueryOption getZerTermsQuery() {
        return zerTermsQuery;
    }

    public MatchQueryConf setZerTermsQuery(ZeroTermsQueryOption zerTermsQuery) {
        this.zerTermsQuery = zerTermsQuery;
        return this;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public MatchQueryConf setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }
}
