package org.pippi.elasticsearch.helper.model.annotations.mapper.base;



import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;

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
     * 自定义查询字段 name，如果不配置默认使用类属性作为查询字段
     */
    String value() default "";

    /**
     * index-filed name, default is Field's name
     * 自定义查询字段 name，如果不配置默认使用类属性作为查询字段
     */
    String name() default "";

    /**
     * query's logic connector (boolQuery: must,must_not,should,filter)
     * 查询的逻辑连接符
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

    /**
     * user define handler, should write this queryType
     */
    String queryType() default "";
}
