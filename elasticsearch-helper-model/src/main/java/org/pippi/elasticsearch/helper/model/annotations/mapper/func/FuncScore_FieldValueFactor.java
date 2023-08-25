package org.pippi.elasticsearch.helper.model.annotations.mapper.func;

import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;

import org.pippi.elasticsearch.helper.model.annotations.mapper.base.FuncQuery;
import org.pippi.elasticsearch.helper.model.bean.func.FieldValueFactorBean;

import java.lang.annotation.*;

/**
 * 直接使用文档中的字段值与 当前的 old_score 做运算，获得新的评分
 *
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
