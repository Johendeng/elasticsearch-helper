package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.index.query.GeoValidationMethod;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 *  geo_polygon（地理多边形查询）
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoPolygon {

    Base value() default @Base;

    boolean ignoreUnmapped() default false;

    GeoValidationMethod geoValidationMethod() default GeoValidationMethod.STRICT;
}
