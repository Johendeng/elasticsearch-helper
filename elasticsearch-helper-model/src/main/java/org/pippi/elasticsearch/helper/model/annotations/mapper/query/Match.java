package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;

import java.lang.annotation.*;

/**
 * ExtMatch
 *
 * @author     JohenTeng
 * @date      2021/9/23
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Match {

    Base value() default @Base;

    int prefixLength() default 0;

    int maxExpansions() default 50;

    boolean fuzzyTranspositions() default true;

    boolean lenient() default false;

    /**
     * 自动生成同义词搜索
     */
    boolean autoGenerateSynonymsPhraseQuery() default true;

    FuzzinessEnum fuzziness() default FuzzinessEnum.AUTO;

    /**
     * 是否对停用词进行搜索
     */
    ZeroTermsQueryOption zerTermsQuery() default ZeroTermsQueryOption.NONE;

    String analyzer() default "";
}
