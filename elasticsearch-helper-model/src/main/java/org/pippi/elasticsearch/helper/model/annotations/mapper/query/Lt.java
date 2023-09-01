package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * @author JohenDeng
 * @date 2023/9/1
 **/
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lt {

    Base value() default @Base;

    String format() default "";

    String relation() default "";

    String timeZone() default "+08:00";
}
