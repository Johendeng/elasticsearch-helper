package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import org.elasticsearch.index.query.QueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.query.FuzzyQueryConf;
import org.pippi.elasticsearch.helper.model.bean.query.MatchQueryConf;
import org.pippi.elasticsearch.helper.model.bean.query.RangeQueryConf;
import org.pippi.elasticsearch.helper.model.bean.query.WildCardQueryConf;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
public interface SearchFunc<F, Children> extends Serializable {

    /**
     * 控制召回文档数量
     */
    Children size(int size);

    /**
     *
     * @param size 控制召回文档数量
     * @param minScore $ > minScore 的文档才会被召回
     * @param traceScore 是否显示 召回文档 的分数
     */
    Children config(int size, float minScore, boolean traceScore);

    /**
     * free append
     */
    default Children chain(QueryBuilder queryBuilder) {
        return chain(true, queryBuilder);
    }

    Children chain(boolean condition, QueryBuilder queryBuilder);

    /**
     * term
     */
    default Children term(F field, Object val) {
        return term(true, field, val);
    }

    Children term(boolean condition, F field, Object val);

    default Children term(F field, Object val, float boost) {
        return term(true, field, val, boost);
    }

    Children term(boolean condition, F field, Object val, float boost);

    /**
     * terms
     */
    default Children terms(F field, Object... val) {
        return terms(true, field, val);
    }

    Children terms(boolean condition, F field, Object... val);

    default Children terms(F field, float boost, Object... val) {
        return terms(true, field, boost, val);
    }

    Children terms(boolean condition, F field, float boost, Object... val);

    /**
     * match
     */
    default Children match(F field, Object val) {
        return match(true, field, val);
    }

    Children match(boolean condition, F field, Object val);

    default Children match(F field, Object val, float boost) {
        return match(true, field, val, boost);
    }

    Children match(boolean condition, F field, Object val, float boost);

    default Children match(F field, Object val, float boost, MatchQueryConf config) {
        return match(true, field, val, boost, config);
    }

    Children match(boolean condition, F field, Object val, float boost, MatchQueryConf config);

    /**
     * fuzzy
     */
    default Children fuzzy(F field, Object val) {
        return fuzzy(true, field, val);
    }

    Children fuzzy(boolean condition, F field, Object val);

    default Children fuzzy(F field, Object val, float boost) {
        return fuzzy(true, field, val, boost);
    }

    Children fuzzy(boolean condition, F field, Object val, float boost);

    default Children fuzzy(F field, Object val, float boost, FuzzyQueryConf config) {
        return fuzzy(true, field, val, boost, config);
    }

    Children fuzzy(boolean condition, F field, Object val, float boost, FuzzyQueryConf config);

    /**
     * wildCard 对字段模糊查询，跟 mysql like查询
     * 原始 wildCard用法  val 需要自行 增加 [* or ?]
     */
    default Children wildCard(F field, Object val) {
        return wildCard(true, field, val);
    }

    Children wildCard(boolean condition, F field, Object val);

    default Children wildCard(F field, Object val, float boost) {
        return wildCard(true, field, val, boost);
    }

    Children wildCard(boolean condition, F field, Object val, float boost);

    default Children wildCard(F field, Object val, float boost, WildCardQueryConf conf) {
        return wildCard(true, field, val, boost, conf);
    }

    Children wildCard(boolean condition, F field, Object val, float boost, WildCardQueryConf conf);

    /**
     * Range
     * tag: 赋值为
     * @see org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range#L_G
     * 。。。 。。。
     */
    default Children range(String tag, F field, Object from, Object to) {
        return range(true, tag, field, from, to);
    }

    /**
     * Range
     * tag: 赋值为
     * @see org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range#L_G
     * 。。。 。。。
     */
    Children range(boolean condition, String tag, F field, Object from, Object to);

    /**
     * Range
     * tag: 赋值为
     * @see org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range#L_G
     * 。。。 。。。
     */
    default Children range(String tag, float boost, F field, Object from, Object to) {
        return range(true, tag, boost, field, from, to);
    }

    /**
     * Range
     * tag: 赋值为
     * @see org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range#L_G
     * 。。。 。。。
     */
    Children range(boolean condition, String tag, float boost, F field, Object from, Object to);

    /**
     * Range
     * tag: 赋值为
     * @see org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range#L_G
     * 。。。 。。。
     */
    default Children range(String tag, float boost, F field, Object from, Object to, RangeQueryConf config) {
        return range(true, tag, boost, field, from, to, config);
    }

    /**
     * Range
     * tag: 赋值为
     * @see org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range#L_G
     * 。。。 。。。
     */
    Children range(boolean condition, String tag, float boost, F field, Object from, Object to, RangeQueryConf config);

    /**
     * gt  大于
     */
    default Children gt(F field, Object val) {
        return gt(true, field, val);
    }

    Children gt(boolean condition, F field, Object val);

    default Children gt(F field, Object val, float boost) {
        return gt(true, field, val, boost);
    }

    Children gt(boolean condition, F field, Object val, float boost);

    default Children gt(F field, Object val, float boost, RangeQueryConf config) {
        return gt(true, field, val, boost, config);
    }

    Children gt(boolean condition, F field, Object val, float boost, RangeQueryConf config);

    /**
     * lt 小于
     */
    default Children lt(F field, Object val) {
        return lt(true, field, val);
    }

    Children lt(boolean condition, F field, Object val);

    default Children lt(F field, Object val, float boost) {
        return lt(true, field, val, boost);
    }

    Children lt(boolean condition, F field, Object val, float boost);

    default Children lt(F field, Object val, float boost, RangeQueryConf config) {
        return lt(true, field, val, boost, config);
    }

    Children lt(boolean condition, F field, Object val, float boost, RangeQueryConf config);

    /**
     * gte 大于等于
     */
    default Children gte(F field, Object val) {
        return gte(true, field, val);
    }

    Children gte(boolean condition, F field, Object val);

    default Children gte(F field, Object val, float boost) {
        return gte(true, field, val, boost);
    }

    Children gte(boolean condition, F field, Object val, float boost);

    default Children gte(F field, Object val, float boost, RangeQueryConf config) {
        return gte(true, field, val, boost, config);
    }

    Children gte(boolean condition, F field, Object val, float boost, RangeQueryConf config);

    /**
     * lte 小于等于
     */
    default Children lte(F field, Object val) {
        return lte(true, field, val);
    }

    Children lte(boolean condition, F field, Object val);

    default Children lte(F field, Object val, float boost) {
        return lte(true, field, val, boost);
    }

    Children lte(boolean condition, F field, Object val, float boost);

    default Children lte(F field, Object val, float boost, RangeQueryConf config) {
        return lte(true, field, val, boost, config);
    }

    Children lte(boolean condition, F field, Object val, float boost, RangeQueryConf config);




    // todo ...



}
