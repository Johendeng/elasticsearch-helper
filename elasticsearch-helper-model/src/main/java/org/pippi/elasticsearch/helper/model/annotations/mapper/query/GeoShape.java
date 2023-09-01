package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.SpatialStrategy;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * geo_shape 用来查询图形，针对 geo_shape，两个图形之间的关系有：相交、包含、不相交。
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoShape {

    Base value() default @Base;

    ShapeRelation relation();

    SpatialStrategy strategy();
}
