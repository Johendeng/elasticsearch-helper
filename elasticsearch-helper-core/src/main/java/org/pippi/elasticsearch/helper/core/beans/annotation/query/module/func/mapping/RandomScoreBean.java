package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping;

import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.utils.CommonUtils;

/**
 * @author: JohenTeng
 * @date: 2022/8/1
 **/
public class RandomScoreBean implements ScoreFuncBuilder {

    private String field;

    private float weight;

    private String seedStringify;

    private long seedLong;

    private int seedInt;

    @Override
    public ScoreFunctionBuilder buildFuncScore() {
        RandomScoreFunctionBuilder randomFunc = ScoreFunctionBuilders.randomFunction();
        randomFunc.setField(field);
        if (weight > 0.0f) {
            randomFunc.setWeight(weight);
        }
        CommonUtils.optionalIfPresent(seedStringify, seed -> randomFunc.seed(seedStringify));
        CommonUtils.filterIfPresent(seedLong, seed -> seed < 0, randomFunc::seed);
        CommonUtils.filterIfPresent(seedInt, seed -> seed < 0, randomFunc::seed);
        return randomFunc;
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getSeedStringify() {
        return seedStringify;
    }

    public void setSeedStringify(String seedStringify) {
        this.seedStringify = seedStringify;
    }

    public long getSeedLong() {
        return seedLong;
    }

    public void setSeedLong(long seedLong) {
        this.seedLong = seedLong;
    }

    public int getSeedInt() {
        return seedInt;
    }

    public void setSeedInt(int seedInt) {
        this.seedInt = seedInt;
    }
}
