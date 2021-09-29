package org.pippi.elasticsearch.helper.spring.annotation;

import java.lang.annotation.*;

/**
 * EsHelperMethod
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EsHelperMethod {
}
