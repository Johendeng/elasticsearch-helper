package org.pippi.elasticsearch.helper.core.beans.annotation.meta;

import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.lang.annotation.*;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.annotation
 * date:    2021/5/12
 * @Author:  JohenTeng
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
    EsMeta type() default EsMeta.UN_COVER;

    String typeStringify() default "";

    String analyzer() default "";

    String format() default "";
}
