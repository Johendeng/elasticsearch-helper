package org.pippi.elasticsearch.helper.model.resp;

import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.model.constant.Common;
import org.pippi.elasticsearch.helper.model.utils.Assert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/9/19
 **/
public class AggRes implements Serializable {

    private String key;

    private Long docCount;

    private Map<String, AggRes> buckets = new LinkedHashMap<>();

    private BigDecimal count;

    private BigDecimal min;

    private BigDecimal max;

    private BigDecimal avg;

    private BigDecimal sum;

    private BigDecimal sumOfSquares;

    private BigDecimal variance;

    private BigDecimal stdDeviation;

    private StdDeviationBounds stdDeviationBounds;

    private Map<String, BigDecimal> percentiles;

    public static class StdDeviationBounds {

        private BigDecimal upper;

        private BigDecimal lower;

        public BigDecimal getUpper() {
            return upper;
        }

        public void setUpper(BigDecimal upper) {
            this.upper = upper;
        }

        public BigDecimal getLower() {
            return lower;
        }

        public void setLower(BigDecimal lower) {
            this.lower = lower;
        }
    }

    /**
     *  source.aggregation(AggregationBuilders.range("_age_range").field("age")
     *          .addRange("r1", 10, 20)
     *          .addRange("r2",20, 30)
     *          .subAggregation(AggregationBuilders.count("_count").field("_id")));
     *
     * for example path = "$._age_range.r1._count"
     */
    public AggRes fetchByPath(String path) {
        Assert.isTrue(StringUtils.isNotBlank(path) && path.startsWith(Common.PATH_HEAD), "error path ex: $._age_range.r1._count");
        String[] pathKey = path.substring(2).split("\\"+Common.SP);
        for (String key : pathKey) {
            this.buckets.get(key);
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getDocCount() {
        return docCount;
    }

    public void setDocCount(Long docCount) {
        this.docCount = docCount;
    }

    public Map<String, AggRes> getBuckets() {
        return buckets;
    }

    public void setBuckets(Map<String, AggRes> buckets) {
        this.buckets = buckets;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getSumOfSquares() {
        return sumOfSquares;
    }

    public void setSumOfSquares(BigDecimal sumOfSquares) {
        this.sumOfSquares = sumOfSquares;
    }

    public BigDecimal getVariance() {
        return variance;
    }

    public void setVariance(BigDecimal variance) {
        this.variance = variance;
    }

    public BigDecimal getStdDeviation() {
        return stdDeviation;
    }

    public void setStdDeviation(BigDecimal stdDeviation) {
        this.stdDeviation = stdDeviation;
    }

    public StdDeviationBounds getStdDeviationBounds() {
        return stdDeviationBounds;
    }

    public void setStdDeviationBounds(StdDeviationBounds stdDeviationBounds) {
        this.stdDeviationBounds = stdDeviationBounds;
    }

    public Map<String, BigDecimal> getPercentiles() {
        return percentiles;
    }

    public void setPercentiles(Map<String, BigDecimal> percentiles) {
        this.percentiles = percentiles;
    }
}
