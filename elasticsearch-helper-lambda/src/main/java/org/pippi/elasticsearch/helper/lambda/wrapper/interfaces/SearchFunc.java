package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.ScriptType;
import org.pippi.elasticsearch.helper.model.bean.query.*;
import org.pippi.elasticsearch.helper.model.param.GeoDistanceParam;
import org.pippi.elasticsearch.helper.model.param.GeometryParam;
import org.pippi.elasticsearch.helper.model.param.MoreLikeThisParam;

import java.io.Serializable;
import java.util.Map;

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

    /**
     * script
     */
    default Children script(String idOrCode) {
        return script(true, idOrCode);
    }

    Children script(boolean condition, String idOrCode);

    default Children script(String idOrCode, Map<String, Object> param) {
        return script(true, idOrCode, param);
    }

    Children script(boolean condition, String idOrCode, Map<String, Object> param);

    default Children script(ScriptType type, String idOrCode) {
        return script(true, type, idOrCode);
    }

    Children script(boolean condition, ScriptType type, String idOrCode);

    default Children script(ScriptType type, String idOrCode, Map<String, Object> param) {
        return script(true, type, idOrCode, param);
    }

    Children script(boolean condition, ScriptType type, String idOrCode, Map<String, Object> param);

    default Children script(String lang, String idOrCode) {
        return script(true, lang, idOrCode);
    }

    Children script(boolean condition, String lang, String idOrCode);

    default Children script(String lang, String idOrCode, Map<String, Object> param) {
        return script(true,  lang, idOrCode, param);
    }
    Children script(boolean condition, String lang, String idOrCode, Map<String, Object> param);

    default Children script(ScriptType type, String lang, String idOrCode) {
        return script(true, type, lang, idOrCode);
    }

    Children script(boolean condition, ScriptType type, String lang, String idOrCode);

    default Children script(ScriptType type, String lang, String idOrCode, Map<String, Object> param) {
        return script(true, type, lang, idOrCode, param);
    }

    Children script(boolean condition, ScriptType type, String lang, String idOrCode, Map<String, Object> param);

    /**
     * geo_bounding_box 盒模型）找出落在指定矩形框中的坐标点，用于可视化范围筛选,
     * 例如：整个屏幕范围内的数据筛选（左上角坐标点和右下角坐标点）
     *
     */
    default Children geoBoundingBox(F field, GeoBoundingBoxParam<?> val) {
        return geoBoundingBox(true, field, val);
    }

    Children geoBoundingBox(boolean condition, F field, GeoBoundingBoxParam<?> val);


    default Children geoBoundingBox(F field, GeoBoundingBoxParam<?> val, float boost) {
        return geoBoundingBox(true, field, val, boost);
    }

    Children geoBoundingBox(boolean condition, F field, GeoBoundingBoxParam<?> val, float boost);

    default Children geoBoundingBox(F field, GeoBoundingBoxParam<?> val, GeoValidationMethod geoValidationMethod, float boost) {
        return geoBoundingBox(true, field, val, boost);
    }

    Children geoBoundingBox(boolean condition, F field, GeoBoundingBoxParam<?> val, GeoValidationMethod geoValidationMethod, float boost);

    /**
     * geo_distance
     */

    default Children geoDistance(F field, GeoDistanceParam val, DistanceUnit unit) {
        return geoDistance(true, field, val, unit);
    }
    Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit);

    default Children geoDistance(F field, GeoDistanceParam val, DistanceUnit unit, float boost) {
        return geoDistance(true, field, val, unit, boost);
    }
    Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit, float boost);

    default Children geoDistance(F field, GeoDistanceParam val, DistanceUnit unit, GeoDistanceQueryConf config) {
        return geoDistance(true, field, val, unit, config);
    }
    Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit, GeoDistanceQueryConf config);

    default Children geoDistance(F field, GeoDistanceParam val, DistanceUnit unit, float boost, GeoDistanceQueryConf config) {
        return geoDistance(true, field, val, unit, boost, config);
    }
    Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit, float boost, GeoDistanceQueryConf config);

    /**
     * geo_polygon
     */
    default Children geoPolygon(F field, GeometryParam val) {
        return geoPolygon(true, field, val);
    }
    Children geoPolygon(boolean condition, F field, GeometryParam val);
    default Children geoPolygon(F field, GeometryParam val, float boost) {
        return geoPolygon(true, field, val, boost);
    }
    Children geoPolygon(boolean condition, F field, GeometryParam val, float boost);
    default Children geoPolygon(F field, GeometryParam val, GeoPolygonQueryConf config) {
        return geoPolygon(true, field, val, config);
    }
    Children geoPolygon(boolean condition, F field, GeometryParam val, GeoPolygonQueryConf config);
    default Children geoPolygon(F field, GeometryParam val, float boost, GeoPolygonQueryConf config) {
        return geoPolygon(true, field, val, boost, config);
    }
    Children geoPolygon(boolean condition, F field, GeometryParam val, float boost, GeoPolygonQueryConf config);

    default Children geoPolygon(F field, String val) {
        return geoPolygon(true, field, val);
    }
    Children geoPolygon(boolean condition, F field, String val);
    default Children geoPolygon(F field, String val, float boost) {
        return geoPolygon(true, field, val, boost);
    }
    Children geoPolygon(boolean condition, F field, String val, float boost);
    default Children geoPolygon(F field, String val, GeoPolygonQueryConf config) {
        return geoPolygon(true, field, val, config);
    }
    Children geoPolygon(boolean condition, F field, String val, GeoPolygonQueryConf config);
    default Children geoPolygon(F field, String val, float boost, GeoPolygonQueryConf config) {
        return geoPolygon(true, field, val, boost, config);
    }
    Children geoPolygon(boolean condition, F field, String val, float boost, GeoPolygonQueryConf config);

    /**
     * geo_shape
     */
    default Children geoShape(F field, GeometryParam val, GeoShapeQueryConf config) {
        return geoShape(true, field, val, config);
    }
    Children geoShape(boolean condition, F field, GeometryParam val, GeoShapeQueryConf config);

    default Children geoShape(F field, GeometryParam val, float boost, GeoShapeQueryConf config) {
        return geoShape(true, field, val, boost, config);
    }
    Children geoShape(boolean condition, F field, GeometryParam val, float boost, GeoShapeQueryConf config);

    default Children geoShape(F field, String val, GeoShapeQueryConf config) {
        return geoShape(true, field, val, config);
    }
    Children geoShape(boolean condition, F field, String val, GeoShapeQueryConf config);

    default Children geoShape(F field, String val, float boost, GeoShapeQueryConf config) {
        return geoShape(true, field, val, boost, config);
    }
    Children geoShape(boolean condition, F field, String val, float boost, GeoShapeQueryConf config);

    /**
     * match_phrase_prefix
     */
    default Children matchPhrasePrefix(F field, Object val) {
        return matchPhrasePrefix(true, field, val);
    }
    Children matchPhrasePrefix(boolean condition, F field, Object val);

    default Children matchPhrasePrefix(F field, Object val, float boost) {
        return matchPhrasePrefix(true, field, val, boost);
    }
    Children matchPhrasePrefix(boolean condition, F field, Object val, float boost);

    default Children matchPhrasePrefix(F field, Object val, MatchPhrasePrefixQueryConf config) {
        return matchPhrasePrefix(true, field, val, config);
    }
    Children matchPhrasePrefix(boolean condition, F field, Object val, MatchPhrasePrefixQueryConf config);

    default Children matchPhrasePrefix(F field, Object val, float boost, MatchPhrasePrefixQueryConf config) {
        return matchPhrasePrefix(true, field, val, boost, config);
    }
    Children matchPhrasePrefix(boolean condition, F field, Object val, float boost, MatchPhrasePrefixQueryConf config);

    /**
     * match_phrase
     */
    default Children matchPhrase(F field, Object val) {
        return matchPhrase(true, field, val);
    }
    Children matchPhrase(boolean condition, F field, Object val);

    default Children matchPhrase(F field, Object val, float boost) {
        return matchPhrase(true, field, val, boost);
    }
    Children matchPhrase(boolean condition, F field, Object val, float boost);

    default Children matchPhrase(F field, Object val, MatchPhraseQueryConf config) {
        return matchPhrase(true, field, val, config);
    }
    Children matchPhrase(boolean condition, F field, Object val, MatchPhraseQueryConf config);

    default Children matchPhrase(F field, Object val, float boost, MatchPhraseQueryConf config) {
        return matchPhrase(true, field, val, boost, config);
    }
    Children matchPhrase(boolean condition, F field, Object val, float boost, MatchPhraseQueryConf config);

    /**
     * more_like_this
     */
    default Children moreLikeThis(MoreLikeThisParam val) {
        return moreLikeThis(true, val);
    }
    Children moreLikeThis(boolean condition, MoreLikeThisParam val);

    default Children moreLikeThis(MoreLikeThisParam val, float boost) {
        return moreLikeThis(true, val, boost);
    }
    Children moreLikeThis(boolean condition, MoreLikeThisParam val, float boost);

    default Children moreLikeThis(MoreLikeThisParam val, F... fields) {
        return moreLikeThis(true, val, fields);
    }
    Children moreLikeThis(boolean condition, MoreLikeThisParam val, F... fields);

    default Children moreLikeThis(MoreLikeThisParam val, float boost, F... fields) {
        return moreLikeThis(true, val, boost, fields);
    }
    Children moreLikeThis(boolean condition, MoreLikeThisParam val, float boost, F... fields);

    default Children moreLikeThis(MoreLikeThisQueryConf config, MoreLikeThisParam val, float boost, F... fields) {
        return moreLikeThis(true, config, val, boost, fields);
    }
    Children moreLikeThis(boolean condition, MoreLikeThisQueryConf config, MoreLikeThisParam val, float boost, F... fields);



    // todo ...



}
