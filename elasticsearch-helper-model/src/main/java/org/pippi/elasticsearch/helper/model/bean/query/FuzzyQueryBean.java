package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;

/**
 * ExtFuzzyBean
 *
 * @author     JohenTeng
 * @date      2021/9/23
 */
public class FuzzyQueryBean extends QueryBean<FuzzyQueryBuilder> {

    private FuzzinessEnum fuzziness;
    private int prefixLength;
    private int maxExpansions;
    private boolean transpositions;

    /**
     *
     * @param queryBuilder (ex: FuzzyQueryBuilder)
     */
    @Override
    public void configQueryBuilder(FuzzyQueryBuilder queryBuilder) {
        queryBuilder.fuzziness(fuzziness.getFuzziness())
                    .prefixLength(prefixLength)
                    .maxExpansions(maxExpansions)
                    .transpositions(transpositions);
    }

    public FuzzinessEnum getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
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

    public boolean isTranspositions() {
        return transpositions;
    }

    public void setTranspositions(boolean transpositions) {
        this.transpositions = transpositions;
    }
}
