package org.pippi.elasticsearch.helper.beans.annotation.query;



import org.pippi.elasticsearch.helper.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.beans.enums.EsMeta;
import org.pippi.elasticsearch.helper.beans.enums.QueryType;

import java.lang.annotation.*;

/**
 * 描述 持有es查询相关的对象实例，进行统一调用和管理
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/8
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryField {

    /**
     *  es索引字段，如果不定义则默认使用当前 Field 名
     * @return
     */
    String name() default "";

    /**
     *  查询类型-使用枚举定义
     * @return
     */
    QueryType queryType();

    /**
     *  查询类型-使用字符串定义
     *  （ queryType和queryKey只需要定义一个）
     * @return
     */
    String queryKey() default "";


    /**
     *  查询逻辑连接符
     * @return
     */
    EsConnector logicConnector() default EsConnector.MUST;

    /**
     *  元数据类型
     * @return
     */
    EsMeta meta();

    /**
     *  字符串化定义元数据类型
     * @return
     */
    String metaStringify() default "";

    /**
     *  查询脚本
     * @return
     */
    String script() default "";

    /**
     *  对于查询的自定义描述，需要根据不同查询实现不同的解析器
     * @return
     */
    String extendDefine() default "";

    /**
     *  自定义匹配分值
     * @return
     */
    float boost() default 1;

}
