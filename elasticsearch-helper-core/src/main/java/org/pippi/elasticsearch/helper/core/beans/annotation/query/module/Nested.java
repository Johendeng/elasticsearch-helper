package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.apache.lucene.search.join.ScoreMode;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.lang.annotation.*;

/**
 * ExtNested
 *
 * @author     JohenTeng
 * @date      2021/9/23
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Nested {

    Base value() default @Base(meta = EsMeta.COMPLEX, connect = EsConnector.SHOULD);

    String path();

    ScoreMode scoreMode();

    boolean ignoreUnmapped() default false;


}
