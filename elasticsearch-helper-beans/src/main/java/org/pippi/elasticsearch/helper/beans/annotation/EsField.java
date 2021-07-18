package org.pippi.elasticsearch.helper.beans.annotation;

import org.pippi.elasticsearch.helper.beans.enums.Meta;

import java.lang.annotation.*;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.annotation
 * date:    2021/5/12
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsField {

    String name() default "";

    /**
     *  data-type
     * @return
     */
    Meta   type() default Meta.UN_COVER;

    String typeStringify() default "";

    String analyzer() default "";

}
