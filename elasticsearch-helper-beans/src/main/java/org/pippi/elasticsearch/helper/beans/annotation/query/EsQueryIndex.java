package org.pippi.elasticsearch.helper.beans.annotation.query;


import org.pippi.elasticsearch.helper.beans.enums.QueryModel;

import java.lang.annotation.*;

/**
 * 描述 持有es查询相关的对象实例，进行统一调用和管理
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/8
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryIndex {

    /**
     *  索引 (required)
     * @return
     */
    String index() ;

    /**
     *  查询模式 (required)
     * @see QueryModel
     * @return
     */
    QueryModel model();

}
