package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.param.GeoPolygonParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoPolygon;
import org.pippi.elasticsearch.helper.core.beans.query.GeoPolygonQueryBean;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

import java.util.Objects;

/**
 * GeoPolygonQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/16
 */
@EsQueryHandle(GeoPolygon.class)
public class GeoPolygonQueryHandler extends AbstractQueryHandler<GeoPolygonQueryBean>{

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoPolygonQueryBean> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        if (Objects.isNull(value) || !(value instanceof GeoPolygonParam)) {
            throw new EsHelperQueryException("GeoPolygon's param have to be GeoPolygonParam.class");
        }
        GeoPolygonQueryBuilder queryBuilder = QueryBuilders.geoPolygonQuery(queryDes.getField(), ((GeoPolygonParam) value).getPoints());
        queryBuilder.boost(queryDes.getBoost());
        return queryBuilder;
    }
}
