package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryHandle;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.GeoDistanceParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.GeoDistance;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.GeoDistanceQueryBean;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * GeoDistanceQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
@EsQueryHandle(GeoDistance.class)
public class GeoDistanceQueryHandler extends AbstractQueryHandler<GeoDistanceQueryBean> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoDistanceQueryBean> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        if (!(value instanceof GeoDistanceQueryBean)) {
            throw new EsHelperQueryException("GeoBoundingBoxQuery's param-type have to be GeoDistanceQueryBean");
        }
        GeoDistanceParam queryValue = (GeoDistanceParam) value;
        GeoDistanceQueryBean extBean = queryDes.getExtBean();
        return QueryBuilders.geoDistanceQuery(queryDes.getField())
                .point(queryValue.getCenterPoint())
                .distance(queryValue.getDistance(), extBean.getUnit());
    }
}
