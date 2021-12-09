package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * <p>
 * source sort will make records force sort by field value,
 *
 * <p/>
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
     * <p>
     *     -asc/desc
     *     {@link org.elasticsearch.search.sort.SortOrder}
     * </p>
     */
    String sort() default "";

    /**
     * script configuration:
     *    inline + painless and no params
     */
    String script() default "";

    /**
     * <p>
     *     -string/number
     *     {@link org.elasticsearch.search.sort.ScriptSortBuilder.ScriptSortType}
     * </p>
     */
    String scriptType() default "";
}
