package org.pippi.elasticsearch.helper.core.beans.annotation.query;

import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;

import java.lang.annotation.*;

/**
 *  define the query handle
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.beans.annotation
 * date:    2021/7/18
 * @author: JohenTeng
 * email: 1078481395@qq.com
 **/
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryHandle {

    String name() default "";

    QueryType handleEnum() default QueryType.MATCH;

}
