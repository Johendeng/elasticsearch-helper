package org.pippi.elasticsearch.helper.model.annotations.meta;



import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;

import java.lang.annotation.*;

/**
 * project  elasticsearch-helper
 * packages   org.pippi.elasticsearch.helper.annotation
 * @date     2021/5/12
 * @author    JohenTeng
 * email    1078481395@qq.com
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsField {

    String name() default "";

    EsMeta type() default EsMeta.TEXT;

    String keyword() default "keyword";

    String typeStringify() default "";

    String analyzer() default "";

    String format() default "";
}
