package org.pippi.elasticsearch.helper.lambda.wrapper;

import org.apache.lucene.search.join.ScoreMode;
import org.pippi.elasticsearch.helper.core.QueryAnnParser;
import org.pippi.elasticsearch.helper.core.wrapper.EsWrapper;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.Bool;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.INested;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.SearchFunc;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.*;
import org.pippi.elasticsearch.helper.model.annotations.meta.EsIndex;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.*;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.pippi.elasticsearch.helper.model.param.RangeParam;

import java.util.List;

/**
 * T 实体类
 * F 字段
 * Children 子引用
 * Param nested 中的子引用
 *
 * @author JohenDeng
 * @date 2023/9/1
 **/
@SuppressWarnings({"unchecked"})
public abstract class EsAbstractWrapper<T, F, Children extends EsAbstractWrapper<T, F, Children>>
        extends EsWrapper<T> implements Bool<Children>, INested<EsWrapper<?> , Children>, SearchFunc<F, Children> {

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
            EsQueryFieldBean term = EsQueryFieldBean.newInstance(Term.class, super.currentConnector, fieldToString(field));
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
            EsQueryFieldBean terms = EsQueryFieldBean.newInstance(Terms.class, super.currentConnector, fieldToString(field));
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




    protected final String fieldToString(F field) {
        return fieldStringify(field);
    }

    protected String fieldStringify(F field) {
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
