package org.pippi.elasticsearch.helper.core.beans.annotation.query;

import java.lang.annotation.*;

/**
 * HighLight
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HighLight {

    /**
     * highLight fields
     * return
     */
    String[] fields() default {};

    /**
     *  which kind of highLight-key
     * return
     */
    String highLightKey() default "default";

}
