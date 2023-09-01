package org.pippi.elasticsearch.helper.model.annotations.mapper.query;

import org.elasticsearch.index.query.GeoValidationMethod;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * geo_bounding_box（盒模型）找出落在指定矩形框中的坐标点，用于可视化范围筛选,
 * 例如：整个屏幕范围内的数据筛选（左上角坐标点和右下角坐标点）
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
@Query
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoBoundingBox {

    Base value() default @Base;

    /**
     * This enum is used to determine how to deal with invalid geo coordinates in geo related
     * queries:
     *  On STRICT validation invalid coordinates cause an exception to be thrown. 直接验证，存在无效坐标将会抛出异常
     *  On IGNORE_MALFORMED invalid coordinates are being accepted. 忽略无效坐标
     *  On COERCE invalid coordinates are being corrected to the most likely valid coordinate. 强制将无效坐标纠正为最接近的正确坐标
     */
    GeoValidationMethod geoValidationMethod() default GeoValidationMethod.STRICT;
}
