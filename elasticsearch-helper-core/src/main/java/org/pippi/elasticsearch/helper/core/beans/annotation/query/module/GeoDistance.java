package org.pippi.elasticsearch.helper.core.beans.annotation.query.module;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Query;

import java.lang.annotation.*;

/**
 *   geo_distance 找出指定位置在给定距离内的数据，相当于指定圆心和半径找到圆中点
 *   找出两千米范围内的所有门店
 *   distance：距离 单位/km
 *   location：坐标点 圆心所在位置
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoDistance {

    Base value() default @Base;

    DistanceUnit unit();

    /**
     *  Geo distance calculation
     */
    org.elasticsearch.common.geo.GeoDistance geoDistance() default org.elasticsearch.common.geo.GeoDistance.ARC;

    GeoValidationMethod geoValidationMethod() default GeoValidationMethod.STRICT;

    boolean ignoreUnmapped() default false;
}
