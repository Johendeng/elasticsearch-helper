package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func;

import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping.FieldValueFactorBean;

import java.lang.annotation.*;

/**
 * @author: JohenTeng
 * @date: 2022/7/19
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FuncQuery(FieldValueFactorBean.class)
public @interface FuncScore_FieldValueFactor {

    String field() default "";

    float factor() default 1.0f;

    float missing() default 0.0f;

    float weight() default 0.0f;

    FieldValueFactorFunction.Modifier modifier() default FieldValueFactorFunction.Modifier.NONE;

}
