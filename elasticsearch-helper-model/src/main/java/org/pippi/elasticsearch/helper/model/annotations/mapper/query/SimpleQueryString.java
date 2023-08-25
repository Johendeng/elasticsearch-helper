package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.SimpleQueryStringFlag;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;


import java.lang.annotation.*;

/**
 * simplify query type of {@link QueryString}
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleQueryString {

    Base value() default @Base;

     String field() default "";

     String fieldAndBoost() default "";

     String[] fieldAndBoosts() default {};

     String analyzer() default "";

     boolean analyzeWildcard() default false;

     boolean autoGenerateSynonymsPhraseQuery() default true;

     Operator defaultOperator() default Operator.OR;

     int fuzzyPrefixLength() default 0;

     int fuzzyMaxExpansions() default 50;

     boolean lenient() default false;

     String minimumShouldMatch() default "";

     String quoteFieldSuffix() default "";

     float boost() default 1.0f;

     SimpleQueryStringFlag[] flags() default {};

}
