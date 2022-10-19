package org.pippi.elasticsearch.helper.core.beans.annotation.query.func.mapping;

import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.utils.CommonUtils;

/**
 * @author: JohenTeng
 * @date: 2022/8/1
 **/
public class RandomScoreBean implements ScoreFuncBuilder {

    private String field;

    private float weight;

    // 该字段为了保证每次相同评分文档产生的随机结果是一致的 （用户session_id, 用户id 等参数 需要用户自定义传入）
    private Object seed;

    @Override
    public ScoreFunctionBuilder buildFuncScore() {
        RandomScoreFunctionBuilder randomFunc = ScoreFunctionBuilders.randomFunction();
        randomFunc.setField(field);
        if (weight > 0.0f) {
            randomFunc.setWeight(weight);
        }
        Class<?> clazz = this.seed.getClass();
        if (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Long.class) ) {
            CommonUtils.optionalIfPresent(seed, seed -> randomFunc.seed(seed.hashCode()));
        }  else {
            throw new EsHelperQueryException("random_score's [seed] have to be String.class, Integer.class or Long.class");
        }
        return randomFunc;
    }

    @Override
    public void builderExtend(EsQueryFieldBean fieldBean) {
        this.field = fieldBean.getField();
        this.seed = fieldBean.getValue();
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

    public Object getSeed() {
        return seed;
    }

    public void setSeed(Object seed) {
        this.seed = seed;
    }
}
