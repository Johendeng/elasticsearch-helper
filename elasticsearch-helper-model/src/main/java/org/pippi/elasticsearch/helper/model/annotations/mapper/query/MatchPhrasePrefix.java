package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * MatchPhrasePrefix
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPhrasePrefix {

    Base value()  default @Base;

    int slop() default 0;

    int maxExpansions() default 50;

    String analyzer() default "";

    ZeroTermsQueryOption zeroTermsQuery() default ZeroTermsQueryOption.NONE;
}
