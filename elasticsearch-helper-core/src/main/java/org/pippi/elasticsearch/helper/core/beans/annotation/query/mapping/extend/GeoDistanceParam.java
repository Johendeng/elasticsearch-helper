package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.elasticsearch.common.geo.GeoPoint;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;

import java.io.Serializable;

/**
 * GeoDistanceParam
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
public class GeoDistanceParam implements EsComplexParam, Serializable {

    private GeoPoint centerPoint;

    private Double distance;

    public GeoPoint getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(GeoPoint centerPoint) {
        this.centerPoint = centerPoint;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
