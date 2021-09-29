package org.pippi.elasticsearch.helper.core.beans.annotation.query;


import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

import java.lang.annotation.*;

/**
 * Define the esQueryHolder's index-name, queryModel ... information
 *
 * @author JohenTeng
 * @date 2021/8/8
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryIndex {

    /**
     *  query index name (required)
     * @return
     */
    String index();

    /**
     *  query model {@link QueryModel} (required)
     * @see QueryModel
     * @return
     */
    QueryModel model();

    /**
     *  fetch need fields from ES, reduce data-package of I/O
     * @return
     */
    String[] fetch() default {};

    /**
     * exclude unuseful fields from ES
     * @return
     */
    String[] exclude() default {};

    /**
     * min fetch doc-score, default is 0.0
     * @return
     */
    float minScore() default 0f;
}
