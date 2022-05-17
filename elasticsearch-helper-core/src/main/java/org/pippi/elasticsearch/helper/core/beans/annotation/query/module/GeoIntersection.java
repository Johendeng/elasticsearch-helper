package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.SpatialStrategy;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 * GeoIntersection
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoIntersection {

    Base value() default @Base;

    ShapeRelation relation() default ShapeRelation.INTERSECTS;

    SpatialStrategy strategy();
}
