package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoPolygon;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.bean.query.GeoPolygonQueryConf;
import org.pippi.elasticsearch.helper.model.param.GeometryParam;
import org.pippi.elasticsearch.helper.model.utils.ExceptionUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * GeoPolygonQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/16
 */
@EsQueryHandle(GeoPolygon.class)
public class GeoPolygonQueryHandler extends AbstractQueryHandler<GeoPolygonQueryConf>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoPolygonQueryConf> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        if (Objects.nonNull(value) && (value instanceof GeometryParam)) {
            try {
                return QueryBuilders.geoIntersectionQuery(queryDes.getField(), (GeometryParam) value);
            } catch (IOException e) {
                throw ExceptionUtils.mpe("geo-polygon-query i/o error, cause:", e);
            }
        } else if (Objects.nonNull(value) && value instanceof String) {
            return QueryBuilders.geoIntersectionQuery(queryDes.getField(), (String) value);
        } else {
            return null;
        }
    }
}
