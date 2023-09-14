package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;

/**
 * ExtFuzzyBean
 *
 * @author     JohenTeng
 * @date      2021/9/23
 */
public class FuzzyQueryConf extends QueryConf<FuzzyQueryBuilder> {

    /**
     * 允许经过几次变更能命中目标字段
     */
    private FuzzinessEnum fuzziness = FuzzinessEnum.AUTO;

    /**
     * 不能被 “模糊化” 的初始字符
     */
    private int prefixLength = 0;

    /**
     * max_expansions 用来限制将产生的模糊选项的总数量
     */
    private int maxExpansions = 50;

    /**
     * 是否允许 字符交换 （ab -> ba）
     */
    private boolean transpositions = false;


    public static FuzzyQueryConf build() {
        return new FuzzyQueryConf();
    }

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

    public FuzzyQueryConf setFuzziness(FuzzinessEnum fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public int getPrefixLength() {
        return prefixLength;
    }

    public FuzzyQueryConf setPrefixLength(int prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public int getMaxExpansions() {
        return maxExpansions;
    }

    public FuzzyQueryConf setMaxExpansions(int maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    public boolean isTranspositions() {
        return transpositions;
    }

    public FuzzyQueryConf setTranspositions(boolean transpositions) {
        this.transpositions = transpositions;
        return this;
    }
}
