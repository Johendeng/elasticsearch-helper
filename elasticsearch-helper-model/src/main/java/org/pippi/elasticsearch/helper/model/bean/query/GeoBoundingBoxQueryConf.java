package org.pippi.elasticsearch.helper.model.bean.query;

import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * GeoBoundingBoxQueryBean
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
public class GeoBoundingBoxQueryConf extends QueryConf<GeoBoundingBoxQueryBuilder> {

    /**
     * This enum is used to determine how to deal with invalid geo coordinates in geo related
     * queries:
     *
     *  On STRICT validation invalid coordinates cause an exception to be thrown.
     *  On IGNORE_MALFORMED invalid coordinates are being accepted.
     *  On COERCE invalid coordinates are being corrected to the most likely valid coordinate.
     */
    private GeoValidationMethod geoValidationMethod = GeoValidationMethod.STRICT;

    public static GeoBoundingBoxQueryConf build() {
        return new GeoBoundingBoxQueryConf();
    }

    @Override
    public void configQueryBuilder(GeoBoundingBoxQueryBuilder queryBuilder) {
        queryBuilder.setValidationMethod(this.geoValidationMethod);
    }

    public GeoValidationMethod getGeoValidationMethod() {
        return geoValidationMethod;
    }

    public GeoBoundingBoxQueryConf setGeoValidationMethod(GeoValidationMethod geoValidationMethod) {
        this.geoValidationMethod = geoValidationMethod;
        return this;
    }
}
