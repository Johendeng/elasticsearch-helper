package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping.LinearDecayBean;

import java.lang.annotation.*;

/**
 * @author: JohenTeng
 * @date: 2022/7/19
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FuncQuery(LinearDecayBean.class)
public @interface FuncScore_LinearDecay {

    String field() default "";

    String origin() default "";

    String scale();

    String offset() default "";

    double decay() default 0.5;
}
