package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * GeoDistanceQueryBean
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
public class GeoDistanceQueryConf extends QueryConf<GeoDistanceQueryBuilder> {

    private DistanceUnit unit;

    private GeoDistance geoDistance = GeoDistance.ARC;

    private GeoValidationMethod geoValidationMethod = GeoValidationMethod.STRICT;

    private Boolean ignoreUnmapped = false;

    public static GeoDistanceQueryConf build(DistanceUnit unit) {
        return new GeoDistanceQueryConf().setUnit(unit);
    }

    @Override
    public void configQueryBuilder(GeoDistanceQueryBuilder queryBuilder) {
        queryBuilder.geoDistance(this.geoDistance)
                .ignoreUnmapped(this.ignoreUnmapped)
                .setValidationMethod(this.geoValidationMethod);
    }

    public DistanceUnit unit() {
        return unit;
    }

    public GeoDistanceQueryConf setUnit(DistanceUnit unit) {
        this.unit = unit;
        return this;
    }

    public GeoDistance geoDistance() {
        return geoDistance;
    }

    public GeoDistanceQueryConf setGeoDistance(GeoDistance geoDistance) {
        this.geoDistance = geoDistance;
        return this;
    }

    public GeoValidationMethod geoValidationMethod() {
        return geoValidationMethod;
    }

    public GeoDistanceQueryConf setGeoValidationMethod(GeoValidationMethod geoValidationMethod) {
        this.geoValidationMethod = geoValidationMethod;
        return this;
    }

    public Boolean ignoreUnmapped() {
        return ignoreUnmapped;
    }

    public GeoDistanceQueryConf setIgnoreUnmapped(Boolean ignoreUnmapped) {
        this.ignoreUnmapped = ignoreUnmapped;
        return this;
    }
}
