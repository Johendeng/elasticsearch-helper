package org.pippi.elasticsearch.helper.beans.annotation;

import org.pippi.elasticsearch.helper.beans.enums.Meta;
import org.pippi.elasticsearch.helper.beans.enums.QueryType;

import java.lang.annotation.*;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper.annotation
 * date:    2021/4/27
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryFiled {

    String name() default "";

    QueryType queryEnum();

    /**
     *  use for user's define query-handle
     * @return
     */
    String query() default "";

    Meta metaType();

    String metaTypeStringify() default "";

    String script() default "";


}
