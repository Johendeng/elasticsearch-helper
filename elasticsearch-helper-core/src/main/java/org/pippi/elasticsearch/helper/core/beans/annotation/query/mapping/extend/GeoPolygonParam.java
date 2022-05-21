package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.elasticsearch.common.geo.GeoPoint;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;

import java.io.Serializable;
import java.util.List;

/**
 * @author: JohenTeng
 * @date: 2022/5/21
 **/
public class GeoPolygonParam implements EsComplexParam, Serializable {

    private List<GeoPoint> points;

    public List<GeoPoint> getPoints() {
        return points;
    }

    public void setPoints(List<GeoPoint> points) {
        this.points = points;
    }
}
