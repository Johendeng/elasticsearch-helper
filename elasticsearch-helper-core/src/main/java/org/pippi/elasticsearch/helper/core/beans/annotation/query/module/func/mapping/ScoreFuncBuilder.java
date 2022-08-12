package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping;

import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryFieldBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.FuncQuery;
import org.pippi.elasticsearch.helper.core.utils.ExtAnnBeanMapUtils;

import java.lang.annotation.Annotation;

/**
 * @author: JohenTeng
 * @date: 2022/8/1
 **/
public interface ScoreFuncBuilder {

    ScoreFunctionBuilder buildFuncScore();

    static ScoreFunctionBuilder generate(EsQueryFieldBean fieldBean) {
        Annotation scoreFuncAnn = fieldBean.getExtAnnotation();
        FuncQuery funcType = scoreFuncAnn.annotationType().getAnnotation(FuncQuery.class);
        Class<? extends ScoreFuncBuilder> scoreFuncBeanType = funcType.value();
        ScoreFuncBuilder scoreFuncBuilder = (ScoreFuncBuilder) ExtAnnBeanMapUtils.mapping(scoreFuncAnn, scoreFuncBeanType);
        scoreFuncBuilder.builderExtend(fieldBean);
        return scoreFuncBuilder.buildFuncScore();
    }

    default void builderExtend(EsQueryFieldBean fieldBean) {}
}
