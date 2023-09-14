package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.pippi.elasticsearch.helper.model.utils.CommonUtils;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * GeoPolygonQueryBean
 *
 * @author JohenTeng
 * @date 2022/5/16
 */
public class GeoPolygonQueryConf extends QueryConf<GeoPolygonQueryBuilder> {

    private Boolean ignoreUnmapped;

    private GeoValidationMethod geoValidationMethod;

    @Override
    public void configQueryBuilder(GeoPolygonQueryBuilder queryBuilder) {
        CommonUtils.optional(ignoreUnmapped).ifPresent(queryBuilder::ignoreUnmapped);
        CommonUtils.optional(geoValidationMethod).ifPresent(queryBuilder::setValidationMethod);
    }
}
