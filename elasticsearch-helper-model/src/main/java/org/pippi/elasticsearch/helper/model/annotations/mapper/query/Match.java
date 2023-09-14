package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.index.query.Operator;
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
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Match {

    Base value() default @Base;

    /**
     * 用来控制match查询匹配词条的逻辑条件，默认值是or，如果设置为and，表示查询满足所有条件
     */
    Operator operator() default Operator.OR;

    /**
     * 当operator参数设置为or时，该参数用来控制应该匹配的分词的最少数量
     */
    String minimumShouldMatch() default "";

    int prefixLength() default 0;

    int maxExpansions() default 50;

    boolean fuzzyTranspositions() default true;

    boolean lenient() default false;

    FuzzinessEnum fuzziness() default FuzzinessEnum.AUTO;

    /**
     * 是否对停用词进行搜索
     */
    ZeroTermsQueryOption zerTermsQuery() default ZeroTermsQueryOption.NONE;

    String analyzer() default "";

    /**
     * 自动生成同义词搜索
     */
    boolean autoGenerateSynonymsPhraseQuery() default true;
}
