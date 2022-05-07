package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * source sort will make records force sort by field value,
 * 该注解配置 会强制让文档排序按照指定的方式进行排序
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
    String sort() default "";

    /**
     * script configuration:
     * {@code inline} or {@code painless} and no params
     */
    String script() default "";

    /**
     * {@code string} or {@code number}
     * {@link org.elasticsearch.search.sort.ScriptSortBuilder.ScriptSortType}
     */
    String scriptType() default "";
}
