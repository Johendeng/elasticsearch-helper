package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.search.MatchQuery;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.Fuzzy;

import java.lang.annotation.*;

/**
 * ExtMuilteMatch
 *
 * @author JohenTeng
 * @date 2021/9/23
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiMatch {

    Base value();

    MultiMatchQueryBuilder.Type type() default MultiMatchQueryBuilder.Type.BEST_FIELDS;
    MatchQuery.ZeroTermsQuery zeroTermsQuery() default MatchQuery.ZeroTermsQuery.NONE;

    /**
     *  define the extrude fields:
     *  different kinds of boosts ,
     *   For Example:
     *   boostFields = field1:3.0,field2:2.0
     *   multiQueryBuilder.field ("field1", 3.0).field("field2", 2.0)
     * @return
     */
    String boostFields() default "";
    boolean autoGenerateSynonymsPhraseQuery() default true;
    Fuzzy fuzziness() default Fuzzy.AUTO;
    boolean fuzzyTranspositions() default false;
    boolean lenient() default true;
    int prefixLength() default 0;
    int maxExpansions() default 50;
    String analyzer() default "";
    Operator operator() default Operator.OR;
    String minimumShouldMatch() default "1";
    int slop() default 0;

}
