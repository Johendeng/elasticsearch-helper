package org.pippi.elasticsearch.helper.lambda.wrapper.query;

import org.apache.commons.lang3.ArrayUtils;
import org.pippi.elasticsearch.helper.lambda.utils.support.SFunction;
import org.pippi.elasticsearch.helper.lambda.wrapper.EsAbstractLambdaWrapper;

import java.util.Arrays;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
public class EsLambdaQueryWrapper<T> extends EsAbstractLambdaWrapper<T, EsLambdaQueryWrapper<T>>
        implements Query<EsLambdaQueryWrapper<T>, T, SFunction<T, ?>> {

    public EsLambdaQueryWrapper() {

    }

    public EsLambdaQueryWrapper(T entity) {
        super.setEntity(entity);
        super.init();
    }

    public EsLambdaQueryWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.init();
    }

    public EsLambdaQueryWrapper(T entity, Class<T> clazz) {
        super.setEntity(entity);
        super.setEntityClass(clazz);
        init();
    }

    @Override
    public EsLambdaQueryWrapper<T> fetch(SFunction<T, ?>... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            String[] array = Arrays.stream(columns)
                    .map(this::fieldStringify)
                    .toArray(String[]::new);
            this.indexInfo.setFetchFields(array);
        }
        return typedThis;
    }

    @Override
    public EsLambdaQueryWrapper<T> exclude(SFunction<T, ?>... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            String[] array = Arrays.stream(columns)
                    .map(this::fieldStringify)
                    .toArray(String[]::new);
            this.indexInfo.setExcludeFields(array);
        }
        return typedThis;
    }

    @Override
    protected EsLambdaQueryWrapper<T> instance() {
        return new EsLambdaQueryWrapper<>(getEntity(), getEntityClass());
    }
}
