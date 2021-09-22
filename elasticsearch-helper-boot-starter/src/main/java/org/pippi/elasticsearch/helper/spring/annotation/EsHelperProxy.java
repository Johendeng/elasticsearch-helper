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

    /**
     * When phrase query-Define bean will visit it's parent
     * @return
     */
    boolean visitParent() default true;

}
