package org.pippi.elasticsearch.helper.core.handler;

import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.EsQueryHandle;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.param.GeoBoundingBoxParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.GeoBoundingBox;
import org.pippi.elasticsearch.helper.model.bean.query.GeoBoundingBoxQueryConf;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * GeoBoundingBoxQueryHandler
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
@EsQueryHandle(GeoBoundingBox.class)
public class GeoBoundingBoxQueryHandler extends AbstractQueryHandler<GeoBoundingBoxQueryConf> {

    @Override
    public QueryBuilder handle(EsQueryFieldBean<GeoBoundingBoxQueryConf> queryDes, AbstractEsSession searchHelper) {
        GeoBoundingBoxQueryBuilder geoBoundingBoxQuery = QueryBuilders.geoBoundingBoxQuery(queryDes.getField());
        Object queryValue = queryDes.getValue();
        if (!(queryValue instanceof GeoBoundingBoxParam)) {
            throw new EsHelperQueryException("GeoBoundingBoxQuery's param-type have to be GeoBoundingBoxParam");
        }
        GeoBoundingBoxParam.BoundingBoxParam param = ((GeoBoundingBoxParam<?>) queryValue).getParam();
        if (param instanceof GeoBoundingBoxParam.CornerPoints) {
            GeoBoundingBoxParam.CornerPoints targetParam =  ((GeoBoundingBoxParam.CornerPoints) param);
            geoBoundingBoxQuery.setCorners(targetParam.getTop(), targetParam.getLeft(), targetParam.getBottom(), targetParam.getRight());
        } else if (param instanceof GeoBoundingBoxParam.GeoHash) {
            geoBoundingBoxQuery.setCorners(((GeoBoundingBoxParam.GeoHash) param).getGeoHash());
        } else if (param instanceof GeoBoundingBoxParam.GeoPoints) {
            GeoBoundingBoxParam.GeoPoints targetParam = (GeoBoundingBoxParam.GeoPoints) param;
            geoBoundingBoxQuery.setCorners(targetParam.getTopLeft(), targetParam.getBottomRight());
        } else if (param instanceof GeoBoundingBoxParam.CornerGeoHash) {
            GeoBoundingBoxParam.CornerGeoHash targetParam = (GeoBoundingBoxParam.CornerGeoHash) param;
            geoBoundingBoxQuery.setCorners(targetParam.getTopLeft(), targetParam.getBottomRight());
        }
        return geoBoundingBoxQuery;
    }
}
