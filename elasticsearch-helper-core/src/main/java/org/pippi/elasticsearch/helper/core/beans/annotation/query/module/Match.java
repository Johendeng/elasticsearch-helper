package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;

import java.lang.annotation.*;

/**
 * ExtMatch
 *
 * @author JohenTeng
 * @date 2021/9/23
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Match {

    Base value();

    int prefixLength() default 0;

    int maxExpansions() default 50;

    boolean fuzzyTranspositions() default true;

    boolean lenient() default false;

    boolean autoGenerateSynonymsPhraseQuery() default true;

    FuzzinessEnum fuzziness() default FuzzinessEnum.AUTO;

    ZeroTermsQueryOption zerTermsQuery() default ZeroTermsQueryOption.NONE;

}
