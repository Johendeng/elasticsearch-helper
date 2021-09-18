package org.pippi.elasticsearch.helper.spring.annotation;

import java.lang.annotation.*;

/**
 * EsHelperProxy
 *
 * @author JohenTeng
 * @date 2021/9/17
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsHelperProxy {
}
