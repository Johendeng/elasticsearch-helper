package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.apache.commons.lang3.tuple.Pair;
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

    String defaultField() ;

    String field() ;

    /**
     * ex:
     *  fieldAndBoost = "field:2.0"
     */
    String fieldAndBoost() ;

    /**
     * ex:
     *  fieldAndBoosts = {"field1:2.0","field2:1.0"}
     */
    String[] fieldAndBoosts() ;

    String analyzer() ;
    Operator defaultOperator() ;
    FuzzinessEnum fuzzinessEnum() ;
    int fuzzyMaxExpansions() ;
    int fuzzyPrefixLength() ;
    String fuzzyRewrite() ;
    boolean fuzzyTranspositions() ;
    boolean analyzeWildcard() ;
    boolean autoGenerateSynonymsPhraseQuery() ;
    boolean allowLeadingWildcard() ;
    boolean enablePositionIncrements() ;
    boolean escape() ;
    boolean lenient() ;
    int phraseSlop() ;
    int maxDeterminizedStates() ;
    String quoteAnalyzer() ;
    String quoteFieldSuffix() ;
    float tieBreaker() ;
    String timeZone() ;
    String minimumShouldMatch() ;
    float boost() ;
}
