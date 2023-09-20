package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 *
 * 分桶 -> 聚合
 *
 * @author JohenDeng
 * @date 2023/9/19
 **/
public interface Agg <F, Children> {

    default Children agg(AggregationBuilder aggregationBuilder) {
        return agg(true, aggregationBuilder);
    }

    /**
     * 原始聚合
     *
     * 聚合得api 就用原始的api 拼接
     *
     */
    Children agg(boolean condition, AggregationBuilder aggregationBuilder);
}
