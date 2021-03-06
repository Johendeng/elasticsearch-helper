package org.pippi.elasticsearch.helper.core.beans.annotation.query;


import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

import java.lang.annotation.*;

/**
 * Define the esQueryHolder's index-name, queryModel ... information
 *
 * @author     JohenTeng
 * @date      2021/8/8
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryIndex {

    /**
     * query index name (required)
     */
    String index();

    /**
     * query model {@link QueryModel} (required)
     * @see QueryModel
     */
    QueryModel model() default QueryModel.BOOL;

    /**
     * fetch need fields from ES, reduce data-package of I/O
     */
    String[] fetch() default {};

    /**
     * exclude unuseful fields from ES
     */
    String[] exclude() default {};

    /**
     * min fetch doc-score, default is 0.0
     */
    float minScore() default 0f;

    /**
     * force compute doc-hit-score
     */
    boolean traceScore() default false;

    /**
     * return hit's data, default 10
     * page query please use {@code PageAndOrder} or {@code SearchAfter} annotation
     *
     */
    int size() default 10;
}
