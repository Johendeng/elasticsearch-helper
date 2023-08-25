package org.pippi.elasticsearch.helper.model.annotations.mapper.query;


import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;
import org.pippi.elasticsearch.helper.model.enums.EsMeta;

import java.lang.annotation.*;

/**
 *  field have to be
 * {@link org.pippi.elasticsearch.helper.model.param.PageParam}
 *
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageAndOrder {

    /**
     * meta = EsMeta.COMPLEX =
     * @see org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.PageParam
     */
    Base value() default @Base(meta = EsMeta.COMPLEX);
}
