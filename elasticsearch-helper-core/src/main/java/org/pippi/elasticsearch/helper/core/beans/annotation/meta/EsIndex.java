package org.pippi.elasticsearch.helper.core.beans.annotation.meta;

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

    // same as value
    String name();

    int shards() default 5;

    int replicas() default 1;



}
