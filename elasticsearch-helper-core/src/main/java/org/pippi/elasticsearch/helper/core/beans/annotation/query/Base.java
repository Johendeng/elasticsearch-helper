package org.pippi.elasticsearch.helper.core.beans.annotation.query;

import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;

import java.lang.annotation.*;


/**
 * 描述 持有es查询相关的对象实例，进行统一调用和管理
 *
 * @author JohenTeng
 * @date 2021/8/8
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Base {

    /**
     *  es索引字段，如果不定义则默认使用当前 Field 名
     * @return
     */
    String name() default "";

    /**
     *  查询类型-使用枚举定义
     * @return
     */
    String queryType() default "";

    /**
     *  查询逻辑连接符
     * @return
     */
    EsConnector connect() default EsConnector.MUST;

    /**
     *  TODO META define may be un-useful
     * @return
     */
    EsMeta meta();

    /**
     *  字符串化定义元数据类型
     * @return
     */
    String metaStringify() default "";

    /**
     *  自定义匹配分值
     * @return
     */
    float boost() default 1.0f;

}
