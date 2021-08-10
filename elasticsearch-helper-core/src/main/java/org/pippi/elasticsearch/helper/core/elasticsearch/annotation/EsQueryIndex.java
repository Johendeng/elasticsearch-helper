package org.pippi.elasticsearch.helper.core.elasticsearch.annotation;

import com.fiture.content.search.common.elasticsearch.enums.EsQueryModel;

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
     * @see EsQueryModel
     * @return
     */
    EsQueryModel model();

}
