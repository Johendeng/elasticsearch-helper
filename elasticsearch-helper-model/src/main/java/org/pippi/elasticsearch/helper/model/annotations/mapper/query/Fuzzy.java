package org.pippi.elasticsearch.helper.model.annotations.mapper.query;



import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;

import java.lang.annotation.*;

/**
 * 模糊查询
 * -- 精度不高，且cpu消耗高
 *
 * @author     JohenTeng
 * @date      2021/9/23
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Fuzzy {

    Base value() default @Base;

    /**
     * 允许经过几次变更能命中目标字段
     */
    FuzzinessEnum fuzziness() default FuzzinessEnum.AUTO;

    /**
     * 不能被 “模糊化” 的初始字符
     */
    int prefixLength() default 0;

    /**
     * max_expansions 用来限制将产生的模糊选项的总数量
     */
    int maxExpansions() default 50;

    /**
     * 是否允许 字符交换 （ab -> ba）
     */
    boolean transpositions() default false;
}
