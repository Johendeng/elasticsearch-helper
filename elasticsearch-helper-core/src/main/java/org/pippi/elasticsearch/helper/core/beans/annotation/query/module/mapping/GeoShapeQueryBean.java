package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.SpatialStrategy;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.pippi.elasticsearch.helper.core.utils.CommonUtils;

/**
 * GeoShapeQueryBean
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
public class GeoShapeQueryBean extends QueryBean<GeoShapeQueryBuilder> {

    /**
     * 多形状匹配中的 关系
     *     INTERSECTS("intersects"),
     *     DISJOINT("disjoint"),
     *     WITHIN("within"),
     *     CONTAINS("contains");
     */
    private ShapeRelation relation;

    private SpatialStrategy strategy;

    @Override
    public void configQueryBuilder(GeoShapeQueryBuilder queryBuilder) {
        CommonUtils.optional(this.relation).ifPresent(queryBuilder::relation);
        CommonUtils.optional(this.strategy).ifPresent(queryBuilder::strategy);
    }
}
