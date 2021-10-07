package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * more like this query,
 * search the most similar _doc
 *
 *  the Field should be define to MoreLikeThisParam.class
 *
 * @author JohenTeng
 * @date 2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MoreLikeThis {

    Base value() default @Base;

    /**
     * 纳入查询评分的字段
     * @return
     */
    String[] fields() default {};

    String analyzer() default "";

    float boostTerms() default  0;

    boolean failOnUnsupportedField() default  true;

    boolean include() default  false;

    int minTermFreq() default  2;

    int maxQueryTerms() default  25;

    int minDocFreq() default  5;

    int maxDocFreq() default  2147483647;

    int minWordLength() default  0;

    int maxWordLength() default  0;

    String minimumShouldMatch() default  "30%";

    String[] stopWords() default  {};


}
