package org.pippi.elasticsearch.helper.core.beans.annotation.query.func;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.func.mapping.LinearDecayBean;

import java.lang.annotation.*;

/**
 *        y                    origin (字段参数)
 *         |                          |
 *         |                   ...... |......
 *         |                 . |      |      .
 *         |               .   |      |        .
 *         | __decay___  .     |      |          .
 *         |           .       |      |            .
 *         |        .  |       |      |              .
 *         |      .    |       |      |                .
 *         |    .      |scale  |offset|                  .
 *         |_______________________________________________   x
 *
 * origin : 中心点，或是字段可能的最佳值，落在原点(origin)上的文档评分_score为满分1.0，支持数值、时间 以及 "经纬度地理座标点"(最常用) 的字段
 *
 * offset : 从 origin 为中心，为他设置一个偏移量offset覆盖一个范围，在此范围内所有的评分_score也都是和origin一样满分1.0
 *
 * scale : 衰减率，即是一个文档从origin下落时，_score改变的速度
 *
 * decay : 从 origin 衰减到 scale 所得的评分_score，默认为0.5 (一般不需要改变，这个参数使用默认的就好了)
 *
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

    String scale();

    String offset() default "";

    double decay() default 0.5;
}
