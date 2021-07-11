package com.poet.elasticsearch.helper.beans.annotation;

import com.poet.elasticsearch.helper.beans.enums.Meta;

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

    String value();

    String name();

    /**
     *  data-type
     * @return
     */
    Meta   type();

    String analyzer();

}
