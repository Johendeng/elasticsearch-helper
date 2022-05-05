package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;

import java.lang.annotation.*;
import java.time.ZoneId;
import java.util.Map;

/**
     "query_string": {
         "query": "field1",
         "fields": [],
         "type": "best_fields",
         "default_operator": "or",
         "max_determinized_states": 10000,
         "enable_position_increments": true,
         "fuzziness": "AUTO",
         "fuzzy_prefix_length": 0,
         "fuzzy_max_expansions": 50,
         "phrase_slop": 0,
         "escape": false,
         "auto_generate_synonyms_phrase_query": true,
         "fuzzy_transpositions": true,
         "boost": 1
     }
 * @author     JohenTeng
 * @date      2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryString {

    Base value() default @Base;

    MultiMatchQueryBuilder.Type type() default MultiMatchQueryBuilder.Type.BEST_FIELDS;

    /**
     * use default field ,when there is un-define any fields
     */
    String defaultField();

    /**
     * field: "field1"
     */
    String field() default "";

    /**
     * ex:
     *  fieldAndBoost = "field:2.0"
     */
    String fieldAndBoost() ;

    /**
     * ex:
     *  fieldAndBoosts = {"field1:2.0","field2:1.0"}
     */
    String[] fieldAndBoosts();

    String analyzer();

    Operator defaultOperator() default Operator.OR;

    FuzzinessEnum fuzziness() default FuzzinessEnum.AUTO;

    int fuzzyMaxExpansions() default 50;

    int fuzzyPrefixLength() default 0;

    String fuzzyRewrite() default "";

    boolean fuzzyTranspositions() default true;

    /**
     * Set to {@code true} to enable analysis on wildcard and prefix queries.
     */
    boolean analyzeWildcard() default false;

    boolean allowLeadingWildcard() default true;

    boolean autoGenerateSynonymsPhraseQuery() default true;

    boolean enablePositionIncrements() default true;

    boolean escape() default false;

    boolean lenient() ;

    int phraseSlop() default 0;

    int maxDeterminizedStates() default 10000;

    String quoteAnalyzer() ;

    String quoteFieldSuffix() ;

    float tieBreaker() ;

    String timeZone() ;

    String minimumShouldMatch() ;

    float boost() default 1;
}
