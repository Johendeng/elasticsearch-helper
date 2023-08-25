package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * SpanTerm
 *
 * @author     JohenTeng
 * @date      2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpanTerm {

    Base value()  default @Base;

}
