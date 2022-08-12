package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping;

import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;

/**
 * @author: JohenTeng
 * @date: 2022/8/1
 **/
public class ExponentialDecayBean implements ScoreFuncBuilder {

    private String field;

    // 变量值
    private String origin;

    private String scale;

    private String offset = null;

    private double decay;

    @Override
    public ScoreFunctionBuilder buildFuncScore() {
        return ScoreFunctionBuilders.exponentialDecayFunction(field, origin, scale, offset, decay);
    }

    @Override
    public void builderExtend(EsQueryFieldBean fieldBean) {
        this.origin = fieldBean.getValue().toString();
        this.field = fieldBean.getField();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public double getDecay() {
        return decay;
    }

    public void setDecay(double decay) {
        this.decay = decay;
    }
}
