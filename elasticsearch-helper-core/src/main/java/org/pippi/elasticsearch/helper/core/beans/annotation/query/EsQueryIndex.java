package org.pippi.elasticsearch.helper.core.beans.annotation.query;


import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

import java.lang.annotation.*;

/**
 * 描述 持有es查询相关的对象实例，进行统一调用和管理
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
    String index() ;

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
}
