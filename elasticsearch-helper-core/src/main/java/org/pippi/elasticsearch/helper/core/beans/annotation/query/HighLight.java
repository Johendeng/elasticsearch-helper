package org.pippi.elasticsearch.helper.core.beans.annotation.query;

import java.lang.annotation.*;

/**
 * HighLight
 *
 * @author JohenTeng
 * @date 2021/9/22
 */
@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface HighLight {

    String value() default "default";

}
