package org.pippi.elasticsearch.helper.lambda.wrapper.query;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
public interface Query<Children, T, R> extends Serializable {

    Children fetch(R... columns);

    Children exclude(R... columns);
}
