package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping.ExponentialDecayBean;

import java.lang.annotation.*;

/**
 * @author: JohenTeng
 * @date: 2022/7/19
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FuncQuery(ExponentialDecayBean.class)
public @interface FuncScore_ExponentialDecay {

    String field() default "";

    String scale();

    String offset() default "";

    double decay() default 0.5;
}
