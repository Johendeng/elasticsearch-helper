package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;

import javax.annotation.Nullable;

/**
 * RangeParam: define the two-side of range query
 *
 * @author JohenTeng
 * @date 2021/9/26
 */
public class RangeParam implements EsComplexParam {

    /**
     * from
     */
    @Nullable
    private Object left;

    /**
     * to
     */
    @Nullable
    private Object right;

    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }
}
