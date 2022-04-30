package org.pippi.elasticsearch.helper.core.beans.annotation.query;

import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.lang.annotation.*;


/**
 * base define of a Es-query-bean
 *
 * @author     JohenTeng
 * @date      2021/8/8
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Base {

    /**
     * index-filed name, default is Field's name
     * return
     */
    String name() default "";

    /**
     * query's logic connector (boolQuery: must,must_not,should,filter)
     * return
     */
    EsConnector connect() default EsConnector.MUST;

    /**
     * meta-define may be un-useful
     */
    EsMeta meta() default EsMeta.COMPLEX;

    /**
     * use a string define a meta
     */
    String metaStringify() default "";

    /**
     * user define boost-score
     */
    float boost() default 1.0f;

}
