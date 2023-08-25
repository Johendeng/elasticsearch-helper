package org.pippi.elasticsearch.helper.core.beans.query;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;

/**
 * ExtMatchBean
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class MatchQueryBean extends QueryBean<MatchQueryBuilder> {

    private int prefixLength;

    private int maxExpansions;

    private boolean fuzzyTranspositions;

    private boolean lenient;

    private boolean autoGenerateSynonymsPhraseQuery;

    private FuzzinessEnum fuzziness;

    private ZeroTermsQueryOption zerTermsQuery;

    private String analyzer;

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

    public void setPrefixLength(int prefixLength) {
        this.prefixLength = prefixLength;
    }

    public int getMaxExpansions() {
        return maxExpansions;
    }

    public void setMaxExpansions(int maxExpansions) {
        this.maxExpansions = maxExpansions;
    }

    public boolean isFuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public void setFuzzyTranspositions(boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
    }

    public boolean isLenient() {
        return lenient;
    }

    public void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public boolean isAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public void setAutoGenerateSynonymsPhraseQuery(boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
    }

    public FuzzinessEnum getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
    }

    public ZeroTermsQueryOption getZerTermsQuery() {
        return zerTermsQuery;
    }

    public void setZerTermsQuery(ZeroTermsQueryOption zerTermsQuery) {
        this.zerTermsQuery = zerTermsQuery;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }
}
