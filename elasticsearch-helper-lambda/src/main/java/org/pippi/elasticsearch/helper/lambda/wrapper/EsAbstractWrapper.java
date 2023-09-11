package org.pippi.elasticsearch.helper.lambda.wrapper;

import org.apache.lucene.search.join.ScoreMode;
import org.pippi.elasticsearch.helper.core.wrapper.EsWrapper;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.Bool;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.INested;
import org.pippi.elasticsearch.helper.lambda.wrapper.interfaces.Query;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Nested;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Term;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.NestedQueryBean;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;

import java.util.List;
import java.util.function.Consumer;

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
public abstract class EsAbstractWrapper<T extends EsEntity, F, Children extends EsAbstractWrapper<T, F, Children>>
        extends EsWrapper<T> implements Bool<Children>, INested<Children, Children>, Query<F, Children> {

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
    public Children nested(boolean condition, String path, Consumer<Children> consumer) {
        return nested(condition, path, ScoreMode.Total, false, consumer);
    }

    @Override
    public Children nested(boolean condition, String path, ScoreMode scoreMode, Consumer<Children> consumer) {
        return nested(condition, path, scoreMode, false, consumer);
    }

    @Override
    public Children nested(boolean condition, String path, boolean ignoreUnmapped, Consumer<Children> consumer) {
        return nested(condition, path, ScoreMode.Total, ignoreUnmapped, consumer);
    }

    @Override
    public Children nested(boolean condition, String path, ScoreMode scoreMode, boolean ignoreUnmapped, Consumer<Children> consumer) {
        return maybeDo(condition, () -> {
            final Children instance = instance();
            List<EsQueryFieldBean<?>> queryDes = instance.queryDesList;
            EsQueryFieldBean nestQueryDes = EsQueryFieldBean.newInstance(Nested.class, super.currentConnector, null);
            queryDes.forEach(des -> des.setField(path + "." + des.getField()));
            nestQueryDes.setNestedQueryDesList(queryDes);
            NestedQueryBean nestedQueryBean = new NestedQueryBean();
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
            super.queryDesList.add(term);
        });
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
}
