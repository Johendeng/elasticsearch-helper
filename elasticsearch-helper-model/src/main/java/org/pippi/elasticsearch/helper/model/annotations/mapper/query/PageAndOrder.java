package org.pippi.elasticsearch.helper.model.annotations.mapper.query;


import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;
import org.pippi.elasticsearch.helper.model.param.EsPage;

import java.lang.annotation.*;

/**
 *  field have to be
 * {@link EsPage}
 *
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageAndOrder {

    /**
     * meta = EsMeta.COMPLEX =
     * @see EsPage
     */
    Base value() default @Base(meta = EsMeta.COMPLEX);
}
