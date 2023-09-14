package org.pippi.elasticsearch.helper.lambda.wrapper.update;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/9/13
 **/
public interface Update<Children, R> extends Serializable {

    default Children set(R column, Object val) {
        return set(true, column, val);
    }

    Children set(boolean condition, R column, Object val);
}
