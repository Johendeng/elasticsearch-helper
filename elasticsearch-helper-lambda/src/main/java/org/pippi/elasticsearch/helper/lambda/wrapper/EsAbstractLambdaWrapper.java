package org.pippi.elasticsearch.helper.lambda.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.meta.IndexMeta;
import org.pippi.elasticsearch.helper.core.meta.IndexMetaCache;
import org.pippi.elasticsearch.helper.lambda.utils.LambaUtils;
import org.pippi.elasticsearch.helper.lambda.utils.PropNameUtils;
import org.pippi.elasticsearch.helper.lambda.utils.support.LambdaMeta;
import org.pippi.elasticsearch.helper.lambda.utils.support.SFunction;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
public abstract class EsAbstractLambdaWrapper<T extends EsEntity,  Children extends EsAbstractLambdaWrapper<T, Children>>
        extends EsAbstractWrapper<T, SFunction<T, ?>, Children> {

    @Override
    protected String fieldStringify(SFunction<T, ?> field) {
        LambdaMeta fieldMeta = LambaUtils.extract(field);
        String fieldName = PropNameUtils.methodToProperty(fieldMeta.getImplMethodName());
        IndexMeta indexMeta = IndexMetaCache.loadMetaIfAbsent(fieldMeta.getInstantiatedClass());
        IndexMeta.FieldMeta fieldMetaBean = indexMeta.getFieldMetaMap().get(fieldName);
        return fieldMetaBean.getType().equals(EsMeta.TEXT) && StringUtils.isNotBlank(fieldMetaBean.getExtension()) ?
                fieldMetaBean.getField() + "." + fieldMetaBean.getExtension() : fieldMetaBean.getField();
    }
}
