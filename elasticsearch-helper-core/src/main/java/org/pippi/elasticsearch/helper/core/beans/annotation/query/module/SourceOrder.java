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

    String sort() default "";

    String script() default "";

    String scriptType() default "";
}
