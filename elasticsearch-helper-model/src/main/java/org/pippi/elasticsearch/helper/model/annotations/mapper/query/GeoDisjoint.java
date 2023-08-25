package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.SpatialStrategy;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * GeoDisjoint
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoDisjoint {
    
    Base value() default @Base;

    ShapeRelation relation() default ShapeRelation.DISJOINT;

    SpatialStrategy strategy();
}
