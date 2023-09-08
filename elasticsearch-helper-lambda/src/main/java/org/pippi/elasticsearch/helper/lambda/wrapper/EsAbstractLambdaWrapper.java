package org.pippi.elasticsearch.helper.lambda.wrapper;

import org.pippi.elasticsearch.helper.lambda.utils.SFunction;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
public abstract class EsAbstractLambdaWrapper<T extends EsEntity,  Children extends EsAbstractLambdaWrapper<T, Children>>
        extends EsAbstractWrapper<T, SFunction<T, ?>, Children>{

    @Override
    protected String fieldStringify(SFunction<T, ?> field) {

        return null;
    }
}
