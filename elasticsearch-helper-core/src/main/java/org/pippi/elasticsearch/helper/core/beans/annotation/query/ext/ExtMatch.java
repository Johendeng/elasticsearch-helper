package org.pippi.elasticsearch.helper.core.beans.annotation.query.ext;

import org.elasticsearch.index.search.MatchQuery;
import org.pippi.elasticsearch.helper.core.beans.enums.Fuzzy;

import java.lang.annotation.*;

/**
 * ExtMatch
 *
 * @author JohenTeng
 * @date 2021/9/23
 */
@Ext
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtMatch {

    int prefixLength() default 0;

    int maxExpansions() default 50;

    boolean fuzzyTranspositions() default true;

    boolean lenient() default false;

    boolean autoGenerateSynonymsPhraseQuery() default true;

    Fuzzy fuzziness() default Fuzzy.AUTO;

    MatchQuery.ZeroTermsQuery zerTermsQuery() default MatchQuery.ZeroTermsQuery.NONE;

}
