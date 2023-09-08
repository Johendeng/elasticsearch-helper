package org.pippi.elasticsearch.helper.core.proxy;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.lang.reflect.Method;

/**
 * @author JohenDeng
 * @date 2023/8/30
 **/
public abstract class EsOperationExecutor {

    abstract boolean condition(Class<?> targetInterface, Method method, Object[] args);

    abstract Object operate(Class<?> targetInterface, RequestOptions requestOption, Method method,
                            Object[] args, boolean visitParent, boolean statementLogOut);
}
