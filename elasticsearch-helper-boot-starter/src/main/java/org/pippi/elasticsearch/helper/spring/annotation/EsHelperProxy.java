package org.pippi.elasticsearch.helper.spring.annotation;

import java.lang.annotation.*;

/**
 * EsHelperProxy
 *
 * @author     JohenTeng
 * @date      2021/9/17
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EsHelperProxy {

    /**
     * When phrase query-Define bean will visit it's parent
     */
    boolean visitParent() default true;

    /**
     * 定义 es 请求参数，默认为 RequestOptions.Default,
     * 如需要自定义，可在 RequestOptionMap 中新增
     */
    String requestOption() default "default";
}
