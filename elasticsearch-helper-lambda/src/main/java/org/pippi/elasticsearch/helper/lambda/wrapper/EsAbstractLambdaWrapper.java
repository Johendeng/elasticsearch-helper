package org.pippi.elasticsearch.helper.lambda.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.pippi.elasticsearch.helper.core.meta.IndexMeta;
import org.pippi.elasticsearch.helper.core.meta.IndexMetaCache;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.lambda.utils.LambdaUtils;
import org.pippi.elasticsearch.helper.lambda.utils.PropNameUtils;
import org.pippi.elasticsearch.helper.lambda.utils.support.LambdaMeta;
import org.pippi.elasticsearch.helper.lambda.utils.support.SFunction;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
public abstract class EsAbstractLambdaWrapper<T,  Children extends EsAbstractLambdaWrapper<T, Children>>
        extends EsAbstractWrapper<T, SFunction<T, ?>, Children> {

    @Override
    protected String fieldStringify(SFunction<T, ?> field) {
        LambdaMeta fieldMeta = LambdaUtils.extract(field);
        String fieldName = PropNameUtils.methodToProperty(fieldMeta.getImplMethodName());
        IndexMeta indexMeta = IndexMetaCache.loadMetaIfAbsent(fieldMeta.getInstantiatedClass());
        if (indexMeta != null) {
            IndexMeta.FieldMeta fieldMetaBean = indexMeta.getFieldMetaMap().get(fieldName);
            return fieldMetaBean.getType().equals(EsMeta.TEXT) && StringUtils.isNotBlank(fieldMetaBean.getExtension()) ?
                    fieldMetaBean.getField() + "." + fieldMetaBean.getExtension() : fieldMetaBean.getField();
        } else {
            return EsBeanFieldTransUtils.camelify(fieldName);
        }
    }
}
