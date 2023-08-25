package org.pippi.elasticsearch.helper.model;

import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.FuncQuery;
import org.pippi.elasticsearch.helper.model.bean.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.model.utils.ExtAnnBeanMapUtils;

import java.lang.annotation.Annotation;

/**
 * @author: JohenTeng
 * @date: 2022/8/1
 **/
public interface ScoreFuncBuilder {

    ScoreFunctionBuilder buildFuncScore();

    static ScoreFunctionBuilder generate(EsQueryFieldBean fieldBean) {
        Annotation scoreFuncAnn = fieldBean.getFuncScoreAnn();
        FuncQuery funcType = scoreFuncAnn.annotationType().getAnnotation(FuncQuery.class);
        Class<? extends ScoreFuncBuilder> scoreFuncBeanType = funcType.value();
        ScoreFuncBuilder scoreFuncBuilder = (ScoreFuncBuilder) ExtAnnBeanMapUtils.mapping(scoreFuncAnn, scoreFuncBeanType);
        scoreFuncBuilder.builderExtend(fieldBean);
        return scoreFuncBuilder.buildFuncScore();
    }

    default void builderExtend(EsQueryFieldBean fieldBean) {}
}
