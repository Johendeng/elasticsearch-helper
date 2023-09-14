package org.pippi.elasticsearch.helper.model.annotations.meta;

import java.lang.annotation.*;

/**
 * project  elasticsearch-helper
 * packages   org.pippi.elasticsearch.helper.annotation
 * @date     2021/5/12
 * @author    JohenTeng
 * email    1078481395@qq.com
 **/
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsIndex {

    String value() default "";

    // same as value
    String name() default "";

    int shards() default 5;

    int replicas() default 1;

    String clientKey() default "primary";
}
