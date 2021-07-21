package org.pippi.elasticsearch.helper.beans.annotation.view;

import org.pippi.elasticsearch.helper.beans.enums.QueryType;

import java.lang.annotation.*;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.beans.annotation
 * date:    2021/7/18
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryHandle {

    String name() default "";

    QueryType handleEnum() default QueryType.UN_DEFINE;

}
