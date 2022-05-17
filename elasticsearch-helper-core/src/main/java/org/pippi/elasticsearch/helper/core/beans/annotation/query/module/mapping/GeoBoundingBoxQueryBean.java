package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.GeoValidationMethod;

/**
 * GeoBoundingBoxQueryBean
 *
 * @author JohenTeng
 * @date 2022/5/12
 */
public class GeoBoundingBoxQueryBean extends QueryBean<GeoBoundingBoxQueryBuilder>{

    /**
     * This enum is used to determine how to deal with invalid geo coordinates in geo related
     * queries:
     *
     *  On STRICT validation invalid coordinates cause an exception to be thrown.
     *  On IGNORE_MALFORMED invalid coordinates are being accepted.
     *  On COERCE invalid coordinates are being corrected to the most likely valid coordinate.
     */
    private GeoValidationMethod geoValidationMethod;

    @Override
    public void configQueryBuilder(GeoBoundingBoxQueryBuilder queryBuilder) {
        queryBuilder.setValidationMethod(this.geoValidationMethod);
    }
}
