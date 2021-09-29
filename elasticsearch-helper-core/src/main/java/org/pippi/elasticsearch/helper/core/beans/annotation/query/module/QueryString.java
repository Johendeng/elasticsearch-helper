package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

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
 * @author JohenTeng
 * @date 2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryString {

    Base value();



}
