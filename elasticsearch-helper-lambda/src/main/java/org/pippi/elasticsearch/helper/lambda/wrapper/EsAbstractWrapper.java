package org.pippi.elasticsearch.helper.lambda.wrapper;

import com.google.common.collect.Lists;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.core.QueryAnnParser;
import org.pippi.elasticsearch.helper.core.wrapper.EsWrapper;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.Agg;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.Bool;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.INested;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.SearchFunc;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.*;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.*;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.pippi.elasticsearch.helper.model.param.*;
import org.pippi.elasticsearch.helper.model.utils.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * T 实体类
 * F 字段
 * Children 子引用
 * Param nested 中的子引用
 *
 * @author JohenDeng
 * @date 2023/9/1
 **/
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class EsAbstractWrapper<T, F, Children extends EsAbstractWrapper<T, F, Children>>
        extends EsWrapper<T> implements Bool<Children>, INested<EsWrapper<?> , Children>, SearchFunc<F, Children>, Agg<F, Children> {

    protected final Children typedThis = (Children) this;

    private T entity;

    private Class<T> entityClass;

    @Override
    public T getEntity() {
        return entity;
    }

    public Children setEntity(T entity) {
        this.entity = entity;
        return typedThis;
    }

    public Class<T> getEntityClass() {
        if (entityClass == null && entity != null) {
            entityClass = (Class<T>) entity.getClass();
        }
        return entityClass;
    }

    public Children setEntityClass(Class<T> entityClass) {
        if (entityClass != null) {
            this.entityClass = entityClass;
        }
        return typedThis;
    }

    @Override
    public Children size(int size) {
        super.indexInfo.setSize(size);
        return typedThis;
    }

    @Override
    public Children config(int size, float minScore, boolean traceScore) {
        super.indexInfo.setSize(size);
        super.indexInfo.setMinScore(minScore);
        super.indexInfo.setTraceScore(traceScore);
        return typedThis;
    }

    @Override
    public Children highLight(boolean condition, HighlightBuilder builder) {
        super.highlightBuilderList().add(builder);
        return typedThis;
    }

    @Override
    public Children chain(boolean condition, QueryBuilder queryBuilder) {
        maybeDo(condition, () -> {
            LinkedList<QueryBuilder> builderList = freeQueries.getOrDefault(super.currentConnector, Lists.newLinkedList());
            builderList.add(queryBuilder);
            freeQueries.put(super.currentConnector, builderList);
        });
        return typedThis;
    }

    @Override
    public Children agg(boolean condition, AggregationBuilder aggregationBuilder) {
        maybeDo(condition, () -> super.aggList.add(aggregationBuilder));
        return typedThis;
    }

    @Override
    public Children must() {
        super.connector(EsConnector.MUST);
        return typedThis;
    }

    @Override
    public Children should() {
        super.connector(EsConnector.SHOULD);
        return typedThis;
    }

    @Override
    public Children filter() {
        super.connector(EsConnector.FILTER);
        return typedThis;
    }

    @Override
    public Children mustNot() {
        super.connector(EsConnector.MUST_NOT);
        return typedThis;
    }

    @Override
    public Children nested(boolean condition, String path, EsWrapper<?>  consumer) {
        return nested(condition, path, ScoreMode.Total, false, consumer);
    }

    @Override
    public Children nested(boolean condition, String path, ScoreMode scoreMode, EsWrapper<?>  consumer) {
        return nested(condition, path, scoreMode, false, consumer);
    }

    @Override
    public Children nested(boolean condition, String path, boolean ignoreUnmapped, EsWrapper<?>  consumer) {
        return nested(condition, path, ScoreMode.Total, ignoreUnmapped, consumer);
    }

    @Override
    public Children nested(boolean condition, String path, ScoreMode scoreMode, boolean ignoreUnmapped, EsWrapper<?>  consumer) {
        return maybeDo(condition, () -> {
            List<EsQueryFieldBean> queryDes = consumer.getQueryDesList();
            EsQueryFieldBean nestQueryDes = EsQueryFieldBean.newInstance(Nested.class, super.currentConnector, null);
            queryDes.forEach(des -> des.setField(path + "." + des.getField()));
            nestQueryDes.setNestedQueryDesList(queryDes);
            NestedQueryConf nestedQueryBean = new NestedQueryConf();
            nestedQueryBean.setPath(path);
            nestedQueryBean.setScoreMode(scoreMode);
            nestedQueryBean.setIgnoreUnmapped(ignoreUnmapped);
            nestQueryDes.setExtBean(nestedQueryBean);
            super.queryDesList.add(nestQueryDes);
        });
    }

    @Override
    public Children term(boolean condition, F field, Object val) {
        return term(condition, field, val, 1.0f);
    }

    @Override
    public Children term(boolean condition, F field, Object val, float boost) {
        return maybeDo(condition, () -> {
            EsQueryFieldBean term = EsQueryFieldBean.newInstance(Term.class, super.currentConnector, fieldToString(field, true));
            term.setBoost(boost);
            term.setValue(val);
            term.setExtBean(new TermQueryConf());
            super.queryDesList.add(term);
        });
    }

    @Override
    public Children terms(boolean condition, F field, Object... val) {
        return terms(condition, field, 1.0f, val);
    }

    @Override
    public Children terms(boolean condition, F field, float boost, Object... val) {
        return maybeDo(condition, ()-> {
            EsQueryFieldBean terms = EsQueryFieldBean.newInstance(Terms.class, super.currentConnector, fieldToString(field, true));
            terms.setBoost(boost);
            terms.setValue(val);
            terms.setExtBean(new TermsQueryConf());
            super.queryDesList.add(terms);
        });
    }


    @Override
    public Children match(boolean condition, F field, Object val) {
        return match(condition, field, val, 1.0f);
    }

    @Override
    public Children match(boolean condition, F field, Object val, float boost) {
        return match(condition, field, val, boost,  MatchQueryConf.build());
    }

    @Override
    public Children match(boolean condition, F field, Object val, float boost, MatchQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean match = EsQueryFieldBean.newInstance(Match.class, super.currentConnector, fieldToString(field));
            match.setValue(val);
            match.setExtBean(config);
            match.setBoost(boost);
            super.queryDesList.add(match);
        });
        return typedThis;
    }


    @Override
    public Children fuzzy(boolean condition, F field, Object val) {
        return fuzzy(condition, field, val, 1.0f);
    }

    @Override
    public Children fuzzy(boolean condition, F field, Object val, float boost) {
        return fuzzy(condition, field, val, boost, FuzzyQueryConf.build());
    }

    @Override
    public Children fuzzy(boolean condition, F field, Object val, float boost, FuzzyQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(Fuzzy.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            conf.setBoost(boost);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children wildCard(boolean condition, F field, Object val) {
        return wildCard(condition, field, val, 1.0f);
    }

    @Override
    public Children wildCard(boolean condition, F field, Object val, float boost) {
        return wildCard(condition, field, val, boost, WildCardQueryConf.build());
    }

    @Override
    public Children wildCard(boolean condition, F field, Object val, float boost, WildCardQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(WildCard.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            conf.setBoost(boost);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children range(boolean condition, String tag, F field, Object from, Object to) {
        return range(condition, tag, 1.0f, field, from, to);
    }

    @Override
    public Children range(boolean condition, String tag, float boost, F field, Object from, Object to) {
        return range(condition, tag, boost, field, from, to, RangeQueryConf.build());
    }

    @Override
    public Children range(boolean condition, String tag, float boost, F field, Object from, Object to, RangeQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(Range.class, super.currentConnector, fieldToString(field));
            RangeParam param = RangeParam.builder().left(from).right(to).build();
            conf.setValue(param);
            conf.setExtBean(config);
            conf.setBoost(boost);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children gt(boolean condition, F field, Object val) {
        return gt(condition, field, val, 1.0f);
    }

    @Override
    public Children gt(boolean condition, F field, Object val, float boost) {
        return gt(condition, field, val, boost, RangeQueryConf.build());
    }

    @Override
    public Children gt(boolean condition, F field, Object val, float boost, RangeQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(Gt.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            conf.setBoost(boost);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children lt(boolean condition, F field, Object val) {
        return lt(condition, field, val, 1.0f);
    }

    @Override
    public Children lt(boolean condition, F field, Object val, float boost) {
        return lt(condition, field, val, boost, RangeQueryConf.build());
    }

    @Override
    public Children lt(boolean condition, F field, Object val, float boost, RangeQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(Lt.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            conf.setBoost(boost);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children gte(boolean condition, F field, Object val) {
        return gte(condition, field, val, 1.0f);
    }

    @Override
    public Children gte(boolean condition, F field, Object val, float boost) {
        return gte(condition, field, val, boost, RangeQueryConf.build());
    }

    @Override
    public Children gte(boolean condition, F field, Object val, float boost, RangeQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(Gte.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            conf.setBoost(boost);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children lte(boolean condition, F field, Object val) {
        return lte(condition, field, val, 1.0f);
    }

    @Override
    public Children lte(boolean condition, F field, Object val, float boost) {
        return lte(condition, field, val, boost, RangeQueryConf.build());
    }

    @Override
    public Children lte(boolean condition, F field, Object val, float boost, RangeQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(Lte.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            conf.setBoost(boost);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children script(boolean condition, String idOrCode) {
        return script(condition, ScriptType.INLINE, "painless", idOrCode);
    }

    @Override
    public Children script(boolean condition, String idOrCode, Map<String, Object> param) {
        return script(condition, ScriptType.INLINE, "painless", idOrCode, param);
    }

    @Override
    public Children script(boolean condition, ScriptType type, String idOrCode) {
        return script(condition, type, "painless", idOrCode);
    }

    @Override
    public Children script(boolean condition, ScriptType type, String idOrCode, Map<String, Object> param) {
        return script(condition, type, "painless", idOrCode, param);
    }

    @Override
    public Children script(boolean condition, String lang, String idOrCode) {
        return script(condition, ScriptType.INLINE, lang, idOrCode);
    }

    @Override
    public Children script(boolean condition, String lang, String idOrCode, Map<String, Object> param) {
        return script(condition, ScriptType.INLINE, lang, idOrCode, param);
    }

    @Override
    public Children script(boolean condition, ScriptType type, String lang, String idOrCode) {
        return script(condition, type, lang, idOrCode, null);
    }

    @Override
    public Children script(boolean condition, ScriptType type, String lang, String idOrCode, Map<String, Object> param) {
        maybeDo(condition, () -> {
            ScriptQueryConf config = ScriptQueryConf.build()
                    .setScriptType(type)
                    .setLang(lang)
                    .setHasParams(CollectionUtils.isNotEmpty(param))
                    .setIdOrCode(idOrCode);
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(ScriptQuery.class, super.currentConnector, null);
            conf.setValue(param);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children geoBoundingBox(boolean condition, F field, GeoBoundingBoxParam<?> val) {
        return geoBoundingBox(condition, field, val, 1.0f);
    }

    @Override
    public Children geoBoundingBox(boolean condition, F field, GeoBoundingBoxParam<?> val, float boost) {
        return geoBoundingBox(condition, field, val, GeoValidationMethod.STRICT, boost);
    }

    @Override
    public Children geoBoundingBox(boolean condition, F field, GeoBoundingBoxParam<?> val, GeoValidationMethod geoValidationMethod, float boost) {
        maybeDo(true, () -> {
            GeoBoundingBoxQueryConf config = GeoBoundingBoxQueryConf.build().setGeoValidationMethod(geoValidationMethod);
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(GeoBoundingBox.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }


    @Override
    public Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit) {
        return geoDistance(condition, field, val, unit, 1.0f);
    }

    @Override
    public Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit, float boost) {
        return geoDistance(condition, field, val, unit, boost, GeoDistanceQueryConf.build(unit));
    }

    @Override
    public Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit, GeoDistanceQueryConf config) {
        return geoDistance(condition, field, val, unit, 1.0f, config);
    }

    @Override
    public Children geoDistance(boolean condition, F field, GeoDistanceParam val, DistanceUnit unit, float boost, GeoDistanceQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(GeoDistance.class, super.currentConnector, fieldToString(field));
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children geoPolygon(boolean condition, F field, GeometryParam val) {
        return geoPolygon(condition, field, val, 1.0f);
    }

    @Override
    public Children geoPolygon(boolean condition, F field, GeometryParam val, float boost) {
        return geoPolygon(condition, field, val, boost, GeoPolygonQueryConf.build());
    }

    @Override
    public Children geoPolygon(boolean condition, F field, GeometryParam val, GeoPolygonQueryConf config) {
        return geoPolygon(condition, field, val, 1.0f, config);
    }

    @Override
    public Children geoPolygon(boolean condition, F field, GeometryParam val, float boost, GeoPolygonQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(GeoPolygon.class, super.currentConnector, fieldToString(field));
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children geoPolygon(boolean condition, F field, String val) {
        return geoPolygon(condition, field, val, 1.0f);
    }

    @Override
    public Children geoPolygon(boolean condition, F field, String val, float boost) {
        return geoPolygon(condition, field, val, boost, GeoPolygonQueryConf.build());
    }

    @Override
    public Children geoPolygon(boolean condition, F field, String val, GeoPolygonQueryConf config) {
        return geoPolygon(condition, field, val, 1.0f, config);
    }

    @Override
    public Children geoPolygon(boolean condition, F field, String val, float boost, GeoPolygonQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(GeoPolygon.class, super.currentConnector, fieldToString(field));
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children geoShape(boolean condition, F field, GeometryParam val, GeoShapeQueryConf config) {
        return geoShape(condition, field, val, 1.0f, config);
    }

    @Override
    public Children geoShape(boolean condition, F field, GeometryParam val, float boost, GeoShapeQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(GeoShape.class, super.currentConnector, fieldToString(field));
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children geoShape(boolean condition, F field, String val, GeoShapeQueryConf config) {
        return geoShape(condition, field, val, 1.0f, config);
    }

    @Override
    public Children geoShape(boolean condition, F field, String val, float boost, GeoShapeQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(GeoShape.class, super.currentConnector, fieldToString(field));
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children matchPhrasePrefix(boolean condition, F field, Object val) {
        return matchPhrasePrefix(condition, field, val, 1.0f);
    }

    @Override
    public Children matchPhrasePrefix(boolean condition, F field, Object val, float boost) {
        return matchPhrasePrefix(condition, field, val, boost, MatchPhrasePrefixQueryConf.build());
    }

    @Override
    public Children matchPhrasePrefix(boolean condition, F field, Object val, MatchPhrasePrefixQueryConf config) {
        return matchPhrasePrefix(condition, field, val, 1.0f, config);
    }

    @Override
    public Children matchPhrasePrefix(boolean condition, F field, Object val, float boost, MatchPhrasePrefixQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(MatchPhrasePrefix.class, super.currentConnector, fieldToString(field));
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children matchPhrase(boolean condition, F field, Object val) {
        return matchPhrase(condition, field, val, 1.0f);
    }

    @Override
    public Children matchPhrase(boolean condition, F field, Object val, float boost) {
        return matchPhrase(condition, field, val, boost, MatchPhraseQueryConf.build());
    }

    @Override
    public Children matchPhrase(boolean condition, F field, Object val, MatchPhraseQueryConf config) {
        return matchPhrase(condition, field, val, 1.0f, config);
    }

    @Override
    public Children matchPhrase(boolean condition, F field, Object val, float boost, MatchPhraseQueryConf config) {
        maybeDo(condition, () -> {
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(MatchPhrase.class, super.currentConnector, fieldToString(field));
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children moreLikeThis(boolean condition, MoreLikeThisParam val) {
        return moreLikeThis(condition, val, 1.0f);
    }

    @Override
    public Children moreLikeThis(boolean condition, MoreLikeThisParam val, float boost) {
        return moreLikeThis(condition, val, boost, (F) null);
    }

    @Override
    public Children moreLikeThis(boolean condition, MoreLikeThisParam val, F... fields) {
        return moreLikeThis(condition, val, 1.0f, fields);
    }

    @Override
    public Children moreLikeThis(boolean condition, MoreLikeThisParam val, float boost, F... fields) {
        return moreLikeThis(condition, MoreLikeThisQueryConf.build(), val, 1.0f, fields);
    }

    @Override
    public Children moreLikeThis(boolean condition, MoreLikeThisQueryConf config, MoreLikeThisParam val, float boost, F... fields) {
        String[] fieldStrArr;
        if (fields != null && fields.length > 0) {
            fieldStrArr = Arrays.stream(fields)
                    .filter(Objects::nonNull)
                    .map(this::fieldStringify)
                    .toArray(String[]::new);
        } else {
            fieldStrArr = null;
        }
        maybeDo(condition, () -> {
            config.setFields(fieldStrArr);
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(MoreLikeThis.class, super.currentConnector, null);
            conf.setBoost(boost);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children multiMatch(boolean condition, Object val, F... fields) {
        return multiMatch(condition, val, 1.0f, fields);
    }

    @Override
    public Children multiMatch(boolean condition, Object val, float boost, F... fields) {
        return multiMatch(condition, MultiMatchQueryConf.build(), val, boost, fields);
    }

    @Override
    public Children multiMatch(boolean condition, MultiMatchQueryConf config, Object val, F... fields) {
        return multiMatch(condition, config, val, 1.0f, fields);
    }

    @Override
    public Children multiMatch(boolean condition, MultiMatchQueryConf config, Object val, float boost, F... fields) {
        Map<F, Float> fieldMap = Arrays.stream(fields)
                .collect(Collectors.toMap(f -> f, f -> boost));
        return multiMatch(condition, config, val, fieldMap);
    }

    @Override
    public Children multiMatch(boolean condition, Object val, Map<F, Float> fields) {
        return multiMatch(condition, MultiMatchQueryConf.build(), val, fields);
    }

    @Override
    public Children multiMatch(boolean condition, MultiMatchQueryConf config, Object val, Map<F, Float> fields) {
        maybeDo(condition && CollectionUtils.isNotEmpty(fields) , () -> {
            Map<String, Float> fieldStringifyMap = fields.entrySet().stream()
                    .collect(Collectors.toMap(e -> this.fieldToString(e.getKey()), Map.Entry::getValue));
            config.setFields(fieldStringifyMap.keySet().toArray(new String[0]));
            config.setFieldAndBoostMap(fieldStringifyMap);
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(MultiMatch.class, super.currentConnector, null);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children queryString(boolean condition, String val, F... fields) {
        return queryString(condition, val, 1.0f, fields);
    }

    @Override
    public Children queryString(boolean condition, Object val, float boost, F... fields) {
        return queryString(condition, QueryStringConf.build(), val, boost, fields);
    }

    @Override
    public Children queryString(boolean condition, QueryStringConf config, Object val, F... fields) {
        return queryString(condition, config, val, 1.0f, fields);
    }

    @Override
    public Children queryString(boolean condition, QueryStringConf config, Object val, float boost, F... fields) {
        Map<F, Float> fieldMap = Arrays.stream(fields)
                .collect(Collectors.toMap(f -> f, f -> boost));
        return queryString(condition, config, val, fieldMap);
    }

    @Override
    public Children queryString(boolean condition, Object val, Map<F, Float> fields) {
        return queryString(condition, QueryStringConf.build(), val, fields);
    }

    @Override
    public Children queryString(boolean condition, QueryStringConf config, Object val, Map<F, Float> fields) {
        maybeDo(condition && CollectionUtils.isNotEmpty(fields) , () -> {
            Map<String, Float> fieldStringifyMap = fields.entrySet().stream()
                    .collect(Collectors.toMap(e -> this.fieldToString(e.getKey()), Map.Entry::getValue));
            config.setFieldAndBoostMap(fieldStringifyMap);
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(QueryString.class, super.currentConnector, null);
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    @Override
    public Children searchAfter(boolean condition, F field, Object val) {
        return searchAfter(condition, field, val, 10);
    }

    @Override
    public Children searchAfter(boolean condition, F field, Object val, SortOrder order) {
        return searchAfter(condition, field, val, order, 10);
    }

    @Override
    public Children searchAfter(boolean condition, F field, Object val, int size) {
        return searchAfter(condition, field, val, SortOrder.ASC, size);
    }

    @Override
    public Children searchAfter(boolean condition, F field, Object val, SortOrder order, int size) {
        maybeDo(condition, () -> {
            SearchAfterQueryConf config = SearchAfterQueryConf.build();
            config.setOrder(order).setSize(size);
            EsQueryFieldBean conf = EsQueryFieldBean.newInstance(SearchAfter.class, super.currentConnector, fieldToString(field));
            conf.setValue(val);
            conf.setExtBean(config);
            super.queryDesList.add(conf);
        });
        return typedThis;
    }

    /**
     * ==================================================================================================================
     */
    protected final String fieldToString(F field) {
        return fieldStringify(field, false);
    }

    protected final String fieldToString(F field, boolean visiteExt) {
        return fieldStringify(field, visiteExt);
    }

    protected String fieldStringify(F field) {
        return fieldStringify(field, false);
    }

    protected String fieldStringify(F field, boolean visitExt) {
        return (String) field;
    }

    /**
     * 子类返回一个自己的新对象
     */
    protected abstract Children instance();

    protected final Children maybeDo(boolean condition, DoSomething something) {
        if (condition) {
            something.doIt();
        }
        return typedThis;
    }

    /**
     * 做事函数
     */
    @FunctionalInterface
    public interface DoSomething {

        void doIt();
    }

    protected void init() {
        // todo
        String indexName = this.getEntityClass().getSimpleName();
        EsIndex indexAnn = this.getEntityClass().getAnnotation(EsIndex.class);
        super.indexInfo = QueryAnnParser.instance().parseIndexAnn(indexName, indexAnn);
    }
}
