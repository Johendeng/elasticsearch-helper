package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.param.GeoDistanceParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoDistance;
import org.pippi.elasticsearch.helper.model.bean.query.GeoDistanceQueryConf;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * GeoDistanceQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
@EsQueryHandle(GeoDistance.class)
public class GeoDistanceQueryHandler extends AbstractQueryHandler<GeoDistanceQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoDistanceQueryConf> queryDes, AbstractEsSession searchHelper) {
        Object value = queryDes.getValue();
        if (!(value instanceof GeoDistanceQueryConf)) {
            throw new EsHelperQueryException("GeoBoundingBoxQuery's param-type have to be GeoDistanceQueryBean");
        }
        GeoDistanceParam queryValue = (GeoDistanceParam) value;
        GeoDistanceQueryConf extBean = queryDes.getExtBean();
        return QueryBuilders.geoDistanceQuery(queryDes.getField())
                .point(queryValue.getCenterPoint())
                .distance(queryValue.getDistance(), extBean.getUnit());
    }
}
