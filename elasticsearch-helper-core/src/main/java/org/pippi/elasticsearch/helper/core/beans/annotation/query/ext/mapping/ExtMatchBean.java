package org.pippi.elasticsearch.helper.core.beans.annotation.query.ext.mapping;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.search.MatchQuery;
import org.pippi.elasticsearch.helper.core.beans.enums.Fuzzy;

/**
 * ExtMatchBean
 *
 * @author JohenTeng
 * @date 2021/9/24
 */
public class ExtMatchBean extends Ext<MatchQueryBuilder> {

    private int prefixLength;

    private int maxExpansions;

    private boolean fuzzyTranspositions;

    private boolean lenient;

    private boolean autoGenerateSynonymsPhraseQuery;

    private Fuzzy fuzziness;

    private MatchQuery.ZeroTermsQuery zerTermsQuery;

    @Override
    public void configQueryBuilder(MatchQueryBuilder match) {
        match.prefixLength(this.prefixLength)
             .maxExpansions(this.maxExpansions)
             .fuzzyTranspositions(this.fuzzyTranspositions)
             .lenient(this.lenient)
             .autoGenerateSynonymsPhraseQuery(this.autoGenerateSynonymsPhraseQuery)
             .fuzziness(this.fuzziness.getFuzziness())
             .zeroTermsQuery(zerTermsQuery);
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

    public Fuzzy getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(Fuzzy fuzziness) {
        this.fuzziness = fuzziness;
    }

    public MatchQuery.ZeroTermsQuery getZerTermsQuery() {
        return zerTermsQuery;
    }

    public void setZerTermsQuery(MatchQuery.ZeroTermsQuery zerTermsQuery) {
        this.zerTermsQuery = zerTermsQuery;
    }
}
