package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;

import java.lang.annotation.*;
import java.util.zip.Deflater;

/**
 * default configuration:
     "multi_match": {
         "query": "dd",
         "fields": [
            "filed^1.0"
         ],
         "type": "best_fields",
         "operator": "OR",
         "slop": 0,
         "prefix_length": 0,
         "max_expansions": 50,
         "zero_terms_query": "NONE",
         "auto_generate_synonyms_phrase_query": true,
         "fuzzy_transpositions": true,
         "boost": 1
     }
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
    String[] fields();
    /**
     *  define the extrude fields:
     *  different kinds of boosts ,
     *   For Example:
     *   boostFields = ["field1:3.0","field2:2.0"]
     *   multiQueryBuilder.field ("field1", 3.0).field("field2", 2.0)
     * @return
     */
    String[] boostFields() default {};
    MultiMatchQueryBuilder.Type type() default MultiMatchQueryBuilder.Type.BEST_FIELDS;
    ZeroTermsQueryOption zeroTermsQuery() default ZeroTermsQueryOption.NONE;
    boolean autoGenerateSynonymsPhraseQuery() default true;
    FuzzinessEnum fuzziness() default FuzzinessEnum.AUTO;
    boolean fuzzyTranspositions() default true;
    boolean lenient() default false;
    int prefixLength() default 0;
    int maxExpansions() default 50;
    String analyzer() default "";
    Operator operator() default Operator.OR;
    String minimumShouldMatch() default "";
    int slop() default 0;

}
