package org.pippi.elasticsearch.helper.model.annotations.mapper.base;

import java.lang.annotation.*;

/**
 *  define the query handle
 * project  elasticsearch-helper
 * packages   org.pippi.elasticsearch.helper.beans.annotation
 * @date     2021/7/18
 * @author   JohenTeng
 **/
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsQueryHandle {

    String[] queryType() default {};

    Class<? extends Annotation>[] value() default Annotation.class;
}
