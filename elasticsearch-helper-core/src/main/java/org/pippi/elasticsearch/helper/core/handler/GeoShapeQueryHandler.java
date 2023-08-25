package org.pippi.elasticsearch.helper.core.handler;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.param.GeoShapeParam;
import org.pippi.elasticsearch.helper.core.beans.query.GeoShapeQueryBean;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoDisjoint;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoIntersection;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoShape;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoWithin;

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
