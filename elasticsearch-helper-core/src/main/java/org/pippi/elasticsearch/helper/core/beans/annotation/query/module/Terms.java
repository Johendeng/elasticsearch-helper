package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * Terms
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Terms {

    Base value() default @Base;

}
