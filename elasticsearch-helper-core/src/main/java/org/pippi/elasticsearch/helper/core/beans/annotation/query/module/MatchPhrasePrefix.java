package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * MatchPhrasePrefix
 *
 * @author JohenTeng
 * @date 2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPhrasePrefix {

    Base value();

    int slop() default 0;

    int maxExpansions() default 50;

    String analyzer() default "";

    ZeroTermsQueryOption zeroTermsQuery() default ZeroTermsQueryOption.NONE;

}
