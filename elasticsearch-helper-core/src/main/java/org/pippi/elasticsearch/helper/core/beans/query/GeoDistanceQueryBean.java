package org.pippi.elasticsearch.helper.core.beans.query;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;

/**
 * GeoDistanceQueryBean
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
public class GeoDistanceQueryBean extends QueryBean<GeoDistanceQueryBuilder> {

    private DistanceUnit unit;

    private GeoDistance geoDistance;

    private GeoValidationMethod geoValidationMethod;

    private Boolean ignoreUnmapped;

    @Override
    public void configQueryBuilder(GeoDistanceQueryBuilder queryBuilder) {
        queryBuilder.geoDistance(this.geoDistance)
                .ignoreUnmapped(this.ignoreUnmapped)
                .setValidationMethod(this.geoValidationMethod);
    }

    public DistanceUnit getUnit() {
        return unit;
    }

    public void setUnit(DistanceUnit unit) {
        this.unit = unit;
    }

    public GeoDistance getGeoDistance() {
        return geoDistance;
    }

    public void setGeoDistance(GeoDistance geoDistance) {
        this.geoDistance = geoDistance;
    }

    public GeoValidationMethod getGeoValidationMethod() {
        return geoValidationMethod;
    }

    public void setGeoValidationMethod(GeoValidationMethod geoValidationMethod) {
        this.geoValidationMethod = geoValidationMethod;
    }

    public Boolean getIgnoreUnmapped() {
        return ignoreUnmapped;
    }

    public void setIgnoreUnmapped(Boolean ignoreUnmapped) {
        this.ignoreUnmapped = ignoreUnmapped;
    }
}
