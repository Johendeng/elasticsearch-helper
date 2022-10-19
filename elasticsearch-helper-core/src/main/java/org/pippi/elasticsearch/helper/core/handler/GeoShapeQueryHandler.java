package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.GeoShapeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.*;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.GeoShapeQueryBean;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.io.IOException;
import java.util.Objects;

/**
 * GeoShapeQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/14
 */
@EsQueryHandle(value = {GeoShape.class, GeoDisjoint.class, GeoWithin.class, GeoIntersection.class})
public class GeoShapeQueryHandler extends AbstractQueryHandler<GeoShapeQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoShapeQueryBean> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        if (!(value instanceof GeoShapeParam)) {
            throw new EsHelperQueryException("GeoShapeQuery's param-type have to be GeoShapeParam");
        }
        GeoShapeParam shapeParam = (GeoShapeParam) value;
        try {
            if (Objects.nonNull(shapeParam.getShape()) && StringUtils.isNotBlank(shapeParam.getIndexedShapeId())) {
                throw new EsHelperQueryException("geoShapeQuery's just need define one param");
            }
            if (Objects.nonNull(shapeParam.getShape())) {
                return QueryBuilders.geoShapeQuery(queryDes.getField(), shapeParam.getShape());
            }
            if (StringUtils.isNotBlank(shapeParam.getIndexedShapeId())) {
                return QueryBuilders.geoShapeQuery(queryDes.getField(), shapeParam.getIndexedShapeId());
            }
        } catch (IOException e) {
            throw new EsHelperQueryException("build GeoShapeQuery Error", e);
        }
        return null;
    }
}
