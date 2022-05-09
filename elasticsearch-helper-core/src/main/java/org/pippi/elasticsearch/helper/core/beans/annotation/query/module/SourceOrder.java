package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * source sort will make records force sort by field value,
 * 该注解配置 会强制让文档排序按照指定的方式进行排序
 *   describe doc's sort type,
 *   simple force sort / sort by script
 *   描述召回文档的排序方式
 *   简单强制排序 / 通过脚本进行排序
 * 字段可以不赋值，
 *
 * 规则： 不定义 script 时，将使用 字段强制排序进行排序
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SourceOrder {

    Base value() default @Base;

    /**
     * {@code asc} or {@code desc}
     * {@link org.elasticsearch.search.sort.SortOrder}
     */
    SortOrder sortOrder() default SortOrder.ASC;

    /**
     * script configuration:
     * 关于排序脚本教程参考文档：
     *
     * {@code inline} or {@code painless} and no params
     */
    String script() default "";

    /**
     * {@code string} or {@code number}
     * {@link org.elasticsearch.search.sort.ScriptSortBuilder.ScriptSortType}
     */
    ScriptSortBuilder.ScriptSortType sortType() default ScriptSortBuilder.ScriptSortType.STRING;
}
