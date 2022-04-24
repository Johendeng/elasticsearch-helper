package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * Range
 *
 * @author     JohenTeng
 * @date      2021/9/26
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

    String LE_GE = "[L,G]";
    String L_G = "(L,G)";
    String LE_G = "[L,G)";
    String L_GE = "(L,G]";
    String F_T = "F_T";

    Base value() default @Base;

    /**
     *  range-query both side relation
     *  {@value LE_GE}
     *  {@value L_G}
     *  {@value LE_G}
     *  {@value L_GE}
     *  {@value F_T}
     * return
     */
    String tag() default L_G;

    String format() default "";
    String relation() default "";
    boolean includeLower() default false;
    boolean includeUpper() default false;
    String timeZone() default "";

}
