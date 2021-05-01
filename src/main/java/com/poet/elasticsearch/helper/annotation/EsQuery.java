package com.poet.elasticsearch.helper.annotation;

import com.poet.elasticsearch.helper.enums.Meta;
import com.poet.elasticsearch.helper.enums.QueryType;

import java.lang.annotation.*;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.annotation
 * date:    2021/4/27
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQuery {

    String name() default "";

    QueryType queryType();

    Meta type();

}
