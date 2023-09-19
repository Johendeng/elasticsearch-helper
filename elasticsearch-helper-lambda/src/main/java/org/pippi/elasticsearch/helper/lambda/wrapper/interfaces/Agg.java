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

    /**
     * 原始聚合
     */
    Children agg(AggregationBuilder aggregationBuilder);



}
