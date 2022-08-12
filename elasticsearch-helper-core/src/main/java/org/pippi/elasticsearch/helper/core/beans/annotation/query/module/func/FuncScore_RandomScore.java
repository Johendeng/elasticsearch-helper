package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping.RandomScoreBean;

import java.lang.annotation.*;

/**
 * random_score 函数会输出一个 0 到 1 之间的数，当种子 seed 值相同时，生成的随机结果是一致的，例如，将用户的会话 ID 作为 seed ：
 * random_score 语句没有任何过滤器 filter ，所以会被应用到所有文档。 将用户的会话 ID 作为种子 seed ，让该用户的随机始终保持一致，
 * 相同的种子 seed 会产生相同的随机结果。 当然，如果增加了与查询匹配的新文档，无论是否使用一致随机，其结果顺序都会发生变化
 *
 * @author: JohenTeng
 * @date: 2022/7/19
 **/
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FuncQuery(RandomScoreBean.class)
public @interface FuncScore_RandomScore {

    String field() default "";

    float weight() default 0.0f;

    String seedStringify() default "";

    long seedLong() default -1L;

    int seedInt() default -1;
}
