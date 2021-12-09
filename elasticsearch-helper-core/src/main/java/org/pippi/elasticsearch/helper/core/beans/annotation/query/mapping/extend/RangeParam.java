package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;

import javax.annotation.Nullable;

/**
 * RangeParam: define the two-side of range query
 *
 * author     JohenTeng
 * date      2021/9/26
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

    @Nullable
    public Object getLeft() {
        return left;
    }

    public void setLeft(@Nullable Object left) {
        this.left = left;
    }

    @Nullable
    public Object getRight() {
        return right;
    }

    public void setRight(@Nullable Object right) {
        this.right = right;
    }

    public static RangeBuilder builder() {
        return new RangeBuilder();
    }

    public static class RangeBuilder {

        private Object left;

        private Object right;

        public RangeBuilder left(Object left) {
            this.left = left;
            return this;
        }

        public RangeBuilder right(Object right) {
            this.right = right;
            return this;
        }

        public RangeParam build() {
            RangeParam param = new RangeParam();
            param.setLeft(this.left);
            param.setRight(this.right);
            return param;
        }
    }
}
