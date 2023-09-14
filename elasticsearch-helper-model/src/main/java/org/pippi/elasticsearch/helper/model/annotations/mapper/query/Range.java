package org.pippi.elasticsearch.helper.model.annotations.mapper.query;



import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * Field have to be
 * @see org.pippi.elasticsearch.helper.model.param.RangeParam
 *
 * @author     JohenTeng
 * @date      2021/9/26
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
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

    /**
     * @see org.elasticsearch.common.geo.ShapeRelation
     */
    String relation() default "";

    boolean includeLower() default true;

    boolean includeUpper() default true;

    String timeZone() default "+08:00";
}
