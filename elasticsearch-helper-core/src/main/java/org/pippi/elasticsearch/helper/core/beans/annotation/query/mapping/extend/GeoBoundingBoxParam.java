package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.elasticsearch.common.geo.GeoPoint;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;

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
