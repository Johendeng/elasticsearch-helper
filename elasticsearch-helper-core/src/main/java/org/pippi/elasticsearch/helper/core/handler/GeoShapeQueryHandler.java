package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoDisjoint;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoIntersection;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoShape;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoWithin;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.GeoShapeQueryConf;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.model.param.GeometryParam;

import java.io.IOException;
import java.util.Objects;

/**
 * GeoShapeQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/14
 */
@EsQueryHandle(value = {GeoShape.class, GeoDisjoint.class, GeoWithin.class, GeoIntersection.class})
public class GeoShapeQueryHandler extends AbstractQueryHandler<GeoShapeQueryConf>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoShapeQueryConf> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        if (Objects.nonNull(value) && value instanceof GeometryParam) {
            try {
                return QueryBuilders.geoShapeQuery(queryDes.getField(), (GeometryParam) value);
            } catch (IOException e) {
                throw new EsHelperQueryException("build GeoShapeQuery Error", e);
            }
        } else if (Objects.nonNull(value) && value instanceof String) {
            return QueryBuilders.geoShapeQuery(queryDes.getField(), (String) value);
        } else {
            return null;
        }
    }
}
