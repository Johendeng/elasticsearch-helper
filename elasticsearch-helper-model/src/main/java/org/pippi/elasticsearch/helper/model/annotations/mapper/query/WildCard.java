package org.pippi.elasticsearch.helper.model.annotations.mapper.query;


import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * WildCard
 *  real-fuzzy for elasticsearch, un-use analyzer, similar mysql's like %holder%
 *  WildCard query's value tag:
 *  '*': multi-words holder
 *  '?': single-words holder
 *
 * @author     JohenTeng
 * @date      2021/9/25
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface WildCard {

    Base value()  default @Base;

    /**
     * 大小写不敏感，默认 false
     */
    boolean caseInsensitive() default false;
}
