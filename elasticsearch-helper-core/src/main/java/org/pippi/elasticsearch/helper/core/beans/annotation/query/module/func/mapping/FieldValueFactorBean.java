package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping;

import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.utils.CommonUtils;

/**
 * @author: JohenTeng
 * @date: 2022/8/1
 **/
public class FieldValueFactorBean implements ScoreFuncBuilder {

    private String field;

    private float factor;

    private float missing;

    private float weight;

    private FieldValueFactorFunction.Modifier modifier;

    @Override
    public ScoreFunctionBuilder buildFuncScore() {
        FieldValueFactorFunctionBuilder fieldValFunc = ScoreFunctionBuilders.fieldValueFactorFunction(field);
        fieldValFunc.factor(factor);
        CommonUtils.filterIfPresent(missing, missing -> missing != 0, fieldValFunc::missing);
        CommonUtils.filterIfPresent(weight, weight -> weight > 0, fieldValFunc::setWeight);
        return fieldValFunc;
    }

    @Override
    public void builderExtend(EsQueryFieldBean fieldBean) {
        this.field = fieldBean.getField();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getMissing() {
        return missing;
    }

    public void setMissing(float missing) {
        this.missing = missing;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public FieldValueFactorFunction.Modifier getModifier() {
        return modifier;
    }

    public void setModifier(FieldValueFactorFunction.Modifier modifier) {
        this.modifier = modifier;
    }
}
