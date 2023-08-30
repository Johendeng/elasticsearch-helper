package org.pippi.elasticsearch.helper.model.annotations.mapper.query;



import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * Term
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Term {

    Base value()  default @Base;

}
