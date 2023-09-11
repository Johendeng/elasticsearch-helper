package org.pippi.elasticsearch.helper.lambda.utils.support;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 支持序列化的 Function
 * copy from mybatis-plus
 * @author miemie
 * @since 2018-05-12
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
