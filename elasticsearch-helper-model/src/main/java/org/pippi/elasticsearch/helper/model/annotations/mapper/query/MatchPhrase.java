package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPhrase {

    Base value() default @Base;

    int slop() default 0;

    ZeroTermsQueryOption zeroTermsQuery() default ZeroTermsQueryOption.NONE;

    String analyzer() default "";
}
