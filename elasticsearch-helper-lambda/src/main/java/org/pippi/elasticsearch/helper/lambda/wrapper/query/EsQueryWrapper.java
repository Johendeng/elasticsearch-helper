package org.pippi.elasticsearch.helper.lambda.wrapper.query;

import org.pippi.elasticsearch.helper.lambda.wrapper.EsAbstractWrapper;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
public class EsQueryWrapper<T> extends EsAbstractWrapper<T, String, EsQueryWrapper<T>> implements Query<EsQueryWrapper<T>, T, String> {

    public EsQueryWrapper() {
        init();
    }

    public EsQueryWrapper(T entity) {
        super.setEntity(entity);
        init();
    }

    public EsQueryWrapper(Class<T> clazz) {
        super.setEntityClass(clazz);
        init();
    }

    public EsQueryWrapper(T entity, Class<T> clazz) {
        super.setEntity(entity);
        super.setEntityClass(clazz);
        init();
    }

    @Override
    public EsQueryWrapper<T> fetch(String... columns) {
        this.indexInfo.setFetchFields(columns);
        return typedThis;
    }

    @Override
    public EsQueryWrapper<T> exclude(String... columns) {
        this.indexInfo.setExcludeFields(columns);
        return typedThis;
    }

    @Override
    protected EsQueryWrapper<T> instance() {
        return new EsQueryWrapper<>(getEntity(), getEntityClass());
    }
}
