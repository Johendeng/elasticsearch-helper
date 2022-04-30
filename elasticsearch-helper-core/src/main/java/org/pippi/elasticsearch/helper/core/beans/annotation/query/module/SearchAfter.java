package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * SearchAfter
 *
 * @author JohenTeng
 * @date 2022/4/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchAfter {

    /**
     * only the field "name" is effective
     */
    Base value() default @Base;

    /**
     * sort rule
     */
    SortOrder order() default SortOrder.ASC;

    int size() default 10;
}
