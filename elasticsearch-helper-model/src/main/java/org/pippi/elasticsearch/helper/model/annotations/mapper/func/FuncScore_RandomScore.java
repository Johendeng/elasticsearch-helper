package org.pippi.elasticsearch.helper.model.annotations.mapper.func;


import org.pippi.elasticsearch.helper.model.annotations.mapper.base.FuncQuery;
import org.pippi.elasticsearch.helper.model.bean.func.RandomScoreBean;

import java.lang.annotation.*;

/**
 * random_score 函数会输出一个 0 到 1 之间的数，当种子 seed 值相同时，生成的随机结果是一致的，例如，将用户的会话 ID 作为 seed ：
 * random_score 语句没有任何过滤器 filter ，所以会被应用到所有文档。 将用户的会话 ID 作为种子 seed ，让该用户的随机始终保持一致，
 * 相同的种子 seed 会产生相同的随机结果。 当然，如果增加了与查询匹配的新文档，无论是否使用一致随机，其结果顺序都会发生变化
 *
 * 应用场景：
 *  当出现 文档 score 同分时，希望能通过随机的方式调整相同评分文档曝光度相近，同时为了保证同一个用户看到的随机列表时一致的，因此可以传入
 *  seed值，在前后搜索seed值相同时，保证返回的随机列表是一致的。 因此一般seed值可以定义为（用户session_id, user_id等）
 *
 * @author: JohenTeng
 * @date: 2022/7/19
 **/
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@FuncQuery(RandomScoreBean.class)
public @interface FuncScore_RandomScore {

    /*
        被该注解修饰的属性 赋值会被作为 random_score 的 seed值，因此该值 必须为 String, int, long 类型
     */
    String field() default "";

    float weight() default 0.0f;
}
