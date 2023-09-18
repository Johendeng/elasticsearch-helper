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

    private Boolean ignoreUnmapped = false;

    private GeoValidationMethod geoValidationMethod = GeoValidationMethod.STRICT;

    public static GeoPolygonQueryConf build() {
        return new GeoPolygonQueryConf();
    }

    @Override
    public void configQueryBuilder(GeoPolygonQueryBuilder queryBuilder) {
        CommonUtils.optional(ignoreUnmapped).ifPresent(queryBuilder::ignoreUnmapped);
        CommonUtils.optional(geoValidationMethod).ifPresent(queryBuilder::setValidationMethod);
    }

    public Boolean ignoreUnmapped() {
        return ignoreUnmapped;
    }

    public GeoPolygonQueryConf setIgnoreUnmapped(Boolean ignoreUnmapped) {
        this.ignoreUnmapped = ignoreUnmapped;
        return this;
    }

    public GeoValidationMethod geoValidationMethod() {
        return geoValidationMethod;
    }

    public GeoPolygonQueryConf setGeoValidationMethod(GeoValidationMethod geoValidationMethod) {
        this.geoValidationMethod = geoValidationMethod;
        return this;
    }
}
