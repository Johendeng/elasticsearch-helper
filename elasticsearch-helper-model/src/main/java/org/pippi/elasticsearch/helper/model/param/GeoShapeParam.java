package org.pippi.elasticsearch.helper.model.param;

import org.elasticsearch.geometry.Geometry;

import java.io.Serializable;

/**
 * 该参数仅需要定义任意一个
 *
 * @author JohenTeng
 * @date 2022/5/13
 */
public class GeoShapeParam<SHAPE extends Geometry> implements EsComplexParam, Serializable {

    private SHAPE shape;

    private String indexedShapeId;

    public SHAPE getShape() {
        return shape;
    }

    public void setShape(SHAPE shape) {
        this.shape = shape;
    }

    public String getIndexedShapeId() {
        return indexedShapeId;
    }

    public void setIndexedShapeId(String indexedShapeId) {
        this.indexedShapeId = indexedShapeId;
    }
}
