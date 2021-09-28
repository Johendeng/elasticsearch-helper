package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * ExtFuzzy
 *
 * @author JohenTeng
 * @date 2021/9/23
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Fuzzy {

    Base value();

    org.pippi.elasticsearch.helper.core.beans.enums.Fuzzy fuzziness() default org.pippi.elasticsearch.helper.core.beans.enums.Fuzzy.AUTO;
    int prefixLength() default 0;
    int maxExpansions() default 50;
    boolean transpositions() default true;


}
