package org.pippi.elasticsearch.helper.model.annotations.mapper.func;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;

import java.lang.annotation.*;

/**
 * @author JohenTeng
 * @date 2022/7/18
 **/
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FuncScore {

    /**
     * boost_mode则定义如何将这个综合的分值作用在原始query产生的分值上
     */
    CombineFunction boostMode() default CombineFunction.MULTIPLY;

    /**
     * score_mode定义的是如何将各个function的分值合并成一个综合的分值
     */
    FunctionScoreQuery.ScoreMode scoreMode() default FunctionScoreQuery.ScoreMode.MULTIPLY;

    /**
     * 最大评分
     */
    float maxBoost() default Float.MAX_VALUE;

    /**
     * 最小分数
     */
    float minScore() default 0.0f;

    /**
     * 分数计算权重   weight * score（最终评分）
     */
    float weight() default 0.0f;
}
