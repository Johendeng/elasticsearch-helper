package org.pippi.elasticsearch.helper.lambda.wrapper.update;

import org.pippi.elasticsearch.helper.lambda.wrapper.EsAbstractWrapper;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

/**
 * @author JohenDeng
 * @date 2023/9/13
 **/
public class EsUpdateWrapper <T extends EsEntity> extends EsAbstractWrapper<T, String, EsUpdateWrapper<T>>
        implements Update<EsUpdateWrapper<T>, String> {

    @Override
    protected EsUpdateWrapper<T> instance() {
        return new EsUpdateWrapper<>();
    }

    @Override
    public EsUpdateWrapper<T> set(boolean condition, String column, Object val) {
        return maybeDo(condition, () -> super.updateMap.put(column, val));
    }
}
