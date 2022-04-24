package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.lang.annotation.*;

/**
 * <h>define the field type as <br/>
 * {@link org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.PageParam}</h>
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

    Base value() default @Base(meta = EsMeta.COMPLEX);
}
