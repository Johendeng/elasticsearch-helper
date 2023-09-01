package org.pippi.elasticsearch.helper.model.bean.base;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;

/**
 * @author JohenTeng
 * @date 2022/8/11
 **/
public class FuncScoreBean {

    /**
     * boost_mode则定义如何将这个综合的分值作用在原始query产生的分值上
     */
    private CombineFunction boostMode;

    /**
     * score_mode定义的是如何将各个function的分值合并成一个综合的分值
     */
    private FunctionScoreQuery.ScoreMode scoreMode;

    /**
     * 最大评分
     */
    private float maxBoost;

    /**
     * 最小分数
     */
    private float minScore;

    /**
     * 分数计算权重   weight * score（最终评分）
     */
    private float weight;

    public CombineFunction getBoostMode() {
        return boostMode;
    }

    public void setBoostMode(CombineFunction boostMode) {
        this.boostMode = boostMode;
    }

    public FunctionScoreQuery.ScoreMode getScoreMode() {
        return scoreMode;
    }

    public void setScoreMode(FunctionScoreQuery.ScoreMode scoreMode) {
        this.scoreMode = scoreMode;
    }

    public float getMaxBoost() {
        return maxBoost;
    }

    public void setMaxBoost(float maxBoost) {
        this.maxBoost = maxBoost;
    }

    public float getMinScore() {
        return minScore;
    }

    public void setMinScore(float minScore) {
        this.minScore = minScore;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
