package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
public interface Query<F, Children> extends Serializable {

    default Children term(F field, Object val) {
        return term(true, field, val);
    }

    Children term(boolean condition, F field, Object val);

    default Children term(F field, Object val, float boost) {
        return term(true, field, val, boost);
    }

    Children term(boolean condition, F field, Object val, float boost);

    // todo ...


}
