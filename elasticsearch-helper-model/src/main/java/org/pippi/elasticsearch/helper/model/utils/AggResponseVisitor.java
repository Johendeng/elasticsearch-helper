package org.pippi.elasticsearch.helper.model.utils;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilters;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.metrics.*;
import org.pippi.elasticsearch.helper.model.resp.AggRes;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/9/19
 **/
public class AggResponseVisitor {

    public static AggRes run(Aggregations aggs) {
        AggRes res = new AggRes();
        visit(aggs, res);
        return res;
    }

    public static void visit(Aggregations aggs, AggRes res) {
        Map<String, AggRes> resMap = res.getBuckets();
        aggs.forEach(agg -> {
            AggRes subAgg = new AggRes();
            resMap.put(agg.getName(), subAgg);
            visit(agg, subAgg);
        });
    }

    /**
     * terms 分桶
     */
    public static void visit(ParsedStringTerms terms, AggRes res) {
        res.setKey(terms.getName());
        Map<String, AggRes> subAgg = res.getBuckets();
        terms.getBuckets().forEach(bucket -> {
            AggRes child = new AggRes();
            String bucketKey = bucket.getKeyAsString();
            child.setKey(bucketKey);
            child.setDocCount(bucket.getDocCount());
            subAgg.put(bucketKey, child);
            Map<String, AggRes> subBucketAgg = child.getBuckets();
            bucket.getAggregations().forEach(agg -> {
                AggRes bucketRes = new AggRes();
                subBucketAgg.put(agg.getName(), bucketRes);
                visit(agg, bucketRes);
            });
        });
    }

    /**
     * filter 分桶
     */
    public static void visit(ParsedFilter filter, AggRes res) {
        res.setKey(filter.getName());
        Map<String, AggRes> subAgg = res.getBuckets();
        filter.getAggregations().forEach(agg -> {
            AggRes child = new AggRes();
            String bucketKey = agg.getName();
            child.setKey(bucketKey);
            subAgg.put(bucketKey, child);
            visit(agg, child);
        });
    }

    /**
     * filter 分桶
     */
    public static void visit(ParsedFilters filters, AggRes res) {
        res.setKey(filters.getName());
        Map<String, AggRes> subAgg = res.getBuckets();
        filters.getBuckets().forEach(bucket -> {
            AggRes child = new AggRes();
            String bucketKey = bucket.getKeyAsString();
            child.setKey(bucketKey);
            child.setDocCount(bucket.getDocCount());
            subAgg.put(bucketKey, child);
            Map<String, AggRes> subBucketAgg = child.getBuckets();
            bucket.getAggregations().forEach(agg -> {
                AggRes bucketRes = new AggRes();
                subBucketAgg.put(agg.getName(), bucketRes);
                visit(agg, bucketRes);
            });
        });
    }

    /**
     * range 分桶
     */
    public static void visit(ParsedRange terms, AggRes res) {
        res.setKey(terms.getName());
        Map<String, AggRes> subAgg = res.getBuckets();
        terms.getBuckets().forEach(bucket -> {
            AggRes child = new AggRes();
            String bucketKey = bucket.getKeyAsString();
            child.setKey(bucketKey);
            child.setDocCount(bucket.getDocCount());
            subAgg.put(bucketKey, child);
            Map<String, AggRes> subBucketAgg = child.getBuckets();
            bucket.getAggregations().forEach(agg -> {
                AggRes bucketRes = new AggRes();
                subBucketAgg.put(agg.getName(), bucketRes);
                visit(agg, bucketRes);
            });
        });
    }

    public static void visit(ParsedValueCount count, AggRes res) {
        res.setKey(count.getName());
        res.setCount(BigDecimal.valueOf(count.getValue()));
    }

    public static void visit(ParsedSum sum, AggRes res) {
        res.setKey(sum.getName());
        res.setSum(BigDecimal.valueOf(sum.getValue()));
    }

    public static void visit(ParsedStats stats, AggRes res) {
        res.setKey(stats.getName());
        res.setCount(BigDecimal.valueOf(stats.getCount()));
        res.setMin(BigDecimal.valueOf(stats.getMin()));
        res.setMax(BigDecimal.valueOf(stats.getMax()));
        res.setAvg(BigDecimal.valueOf(stats.getAvg()));
        res.setSum(BigDecimal.valueOf(stats.getSum()));
    }

    public static void visit(ParsedExtendedStats stats, AggRes res) {
        // todo 信息并没有读全
        visit((ParsedStats)stats, res);
        res.setSumOfSquares(BigDecimal.valueOf(stats.getSumOfSquares()));
        res.setVariance(BigDecimal.valueOf(stats.getVariance()));
        res.setStdDeviation(BigDecimal.valueOf(stats.getStdDeviation()));
        AggRes.StdDeviationBounds bounds = new AggRes.StdDeviationBounds();
        bounds.setLower(BigDecimal.valueOf(stats.getStdDeviationBound(ExtendedStats.Bounds.LOWER)));
        bounds.setUpper(BigDecimal.valueOf(stats.getStdDeviationBound(ExtendedStats.Bounds.UPPER)));
        res.setStdDeviationBounds(bounds);
    }

    public static void visit(ParsedTDigestPercentiles percent, AggRes res) {
        res.setKey(percent.getName());
        final Map<String, BigDecimal> percentMap = new LinkedHashMap<>();
        res.setPercentiles(percentMap);
        percent.forEach(p -> percentMap.put(p.getPercent() + "", BigDecimal.valueOf(p.getValue())));
    }

    public static void visit(ParsedTDigestPercentileRanks percent, AggRes res) {
        res.setKey(percent.getName());
        final Map<String, BigDecimal> percentMap = new LinkedHashMap<>();
        res.setPercentiles(percentMap);
        percent.forEach(p -> percentMap.put(p.getValue() + "", BigDecimal.valueOf(p.getPercent())));
    }

    private static void visit(Aggregation agg, AggRes res) {
        if (agg instanceof ParsedStringTerms) {
            visit((ParsedStringTerms) agg, res);
        } else if (agg instanceof ParsedFilter) {
            visit((ParsedFilter) agg, res);
        } else if (agg instanceof ParsedFilters) {
            visit((ParsedFilters) agg, res);
        } else if (agg instanceof ParsedRange) {
            visit((ParsedRange) agg, res);
        } else if (agg instanceof ParsedValueCount) {
            visit((ParsedValueCount) agg, res);
        } else if (agg instanceof ParsedSum) {
            visit((ParsedSum) agg, res);
        } else if (agg instanceof ParsedExtendedStats) {
            visit((ParsedExtendedStats) agg, res);
        } else if (agg instanceof ParsedStats) {
            visit((ParsedStats) agg, res);
        } else if (agg instanceof ParsedTDigestPercentiles) {
            visit((ParsedTDigestPercentiles) agg, res);
        } else if (agg instanceof ParsedTDigestPercentileRanks) {
            visit((ParsedTDigestPercentileRanks) agg, res);
        }
    }
}
