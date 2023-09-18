package org.pippi.elasticsearch.helper.model.param;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.geometry.utils.Geohash;


import java.io.Serializable;

/**
 * 预定义 空间搜索-矩形范围搜索查询参数
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
public class GeoBoundingBoxParam<T extends GeoBoundingBoxParam.BoundingBoxParam> implements EsComplexParam,Serializable {

    private T param;

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public static GeoBoundingBoxParam<CornerPoints> cornerPoints(double top, double left, double bottom, double right) {
        CornerPoints points = new CornerPoints();
        points.setTop(top);
        points.setLeft(left);
        points.setBottom(bottom);
        points.setRight(right);
        GeoBoundingBoxParam<CornerPoints> param = new GeoBoundingBoxParam<>();
        param.setParam(points);
        return param;
    }

    public static GeoBoundingBoxParam<GeoHash> geoHash(String geoHash) {
        GeoBoundingBoxParam<GeoHash> param = new GeoBoundingBoxParam<>();
        GeoHash hash = new GeoHash();
        hash.setGeoHash(geoHash);
        param.setParam(hash);
        return param;
    }

    public static GeoBoundingBoxParam<GeoPoints> geoHash(GeoPoint topLeft, GeoPoint bottomRight) {
        GeoBoundingBoxParam<GeoPoints> param = new GeoBoundingBoxParam<>();
        GeoPoints points = new GeoPoints();
        param.setParam(points);
        points.setTopLeft(topLeft);
        points.setBottomRight(bottomRight);
        return param;
    }

    public static GeoBoundingBoxParam<CornerGeoHash> geoHash(String topLeft, String bottomRight) {
        GeoBoundingBoxParam<CornerGeoHash> param = new GeoBoundingBoxParam<>();
        CornerGeoHash points = new CornerGeoHash();
        param.setParam(points);
        points.setTopLeft(topLeft);
        points.setBottomRight(bottomRight);
        return param;
    }

    public static class CornerPoints extends BoundingBoxParam {

        private double top;

        private double left;

        private double bottom;

        private double right;

        public double getTop() {
            return top;
        }

        public void setTop(double top) {
            this.top = top;
        }

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getBottom() {
            return bottom;
        }

        public void setBottom(double bottom) {
            this.bottom = bottom;
        }

        public double getRight() {
            return right;
        }

        public void setRight(double right) {
            this.right = right;
        }
    }

    public static class GeoHash extends BoundingBoxParam {

        private String geoHash;

        public String getGeoHash() {
            return geoHash;
        }

        public void setGeoHash(String geoHash) {
            this.geoHash = geoHash;
        }
    }

    public static class GeoPoints extends BoundingBoxParam {

        private GeoPoint topLeft;

        private GeoPoint bottomRight;

        public GeoPoint getTopLeft() {
            return topLeft;
        }

        public void setTopLeft(GeoPoint topLeft) {
            this.topLeft = topLeft;
        }

        public GeoPoint getBottomRight() {
            return bottomRight;
        }

        public void setBottomRight(GeoPoint bottomRight) {
            this.bottomRight = bottomRight;
        }
    }

    public static class CornerGeoHash extends BoundingBoxParam {

        private String topLeft;

        private String bottomRight;

        public String getTopLeft() {
            return topLeft;
        }

        public void setTopLeft(String topLeft) {
            this.topLeft = topLeft;
        }

        public String getBottomRight() {
            return bottomRight;
        }

        public void setBottomRight(String bottomRight) {
            this.bottomRight = bottomRight;
        }
    }

    public static abstract class BoundingBoxParam {}
}
