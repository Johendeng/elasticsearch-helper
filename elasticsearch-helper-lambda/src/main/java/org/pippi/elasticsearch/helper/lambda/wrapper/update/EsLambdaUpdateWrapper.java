package org.pippi.elasticsearch.helper.lambda.wrapper.update;

import org.pippi.elasticsearch.helper.core.meta.IndexMeta;
import org.pippi.elasticsearch.helper.core.meta.IndexMetaCache;
import org.pippi.elasticsearch.helper.lambda.utils.LambdaUtils;
import org.pippi.elasticsearch.helper.lambda.utils.PropNameUtils;
import org.pippi.elasticsearch.helper.lambda.utils.support.LambdaMeta;
import org.pippi.elasticsearch.helper.lambda.utils.support.EsFunction;
import org.pippi.elasticsearch.helper.lambda.wrapper.EsAbstractLambdaWrapper;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

/**
 * @author JohenDeng
 * @date 2023/9/13
 **/
public class EsLambdaUpdateWrapper <T extends EsEntity> extends EsAbstractLambdaWrapper<T, EsLambdaUpdateWrapper<T>>
        implements Update<EsLambdaUpdateWrapper<T>, EsFunction<T, ?>>  {

    @Override
    protected EsLambdaUpdateWrapper<T> instance() {
        return new EsLambdaUpdateWrapper<>();
    }

    @Override
    public EsLambdaUpdateWrapper<T> set(boolean condition, EsFunction<T, ?> column, Object val) {
        maybeDo(condition, ()-> {
            String columnStr = super.fieldStringify(column);
            super.updateMap.put(columnStr, val);
        });
        return typedThis;
    }

    @Override
    protected String fieldStringify(EsFunction<T, ?> field) {
        LambdaMeta fieldMeta = LambdaUtils.extract(field);
        String fieldName = PropNameUtils.methodToProperty(fieldMeta.getImplMethodName());
        IndexMeta indexMeta = IndexMetaCache.loadMetaIfAbsent(fieldMeta.getInstantiatedClass());
        IndexMeta.FieldMeta fieldMetaBean = indexMeta.getFieldMetaMap().get(fieldName);
        return fieldMetaBean.getField();
    }
}
