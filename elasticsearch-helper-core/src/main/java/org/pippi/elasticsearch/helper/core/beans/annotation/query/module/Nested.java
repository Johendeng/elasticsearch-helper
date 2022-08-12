package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.apache.lucene.search.join.ScoreMode;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.lang.annotation.*;

/**
 * when path is un-define, will add this queryBuilder into parent's query,use {@link Base} define logic-connector
 * 当嵌套查询路径未定义时，将会将生成的 queryBuilder 加入到现有查询中，使用 @Base 中定义的逻辑连接符
 * 对应场景为 and (a or b) 的场景
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

    ScoreMode scoreMode() default ScoreMode.Total;

    boolean ignoreUnmapped() default false;
}
