package org.pippi.elasticsearch.helper.core.holder;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.WeightBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.FuncScoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: JohenTeng
 * @date: 2022/8/11
 **/
public class FuncScoreEsRequestHolder extends BoolEsRequestHolder {

    public FunctionScoreQueryBuilder functionScoreQueryBuilder;

    public List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFuncArr = new ArrayList<>();

    @Override
    public SearchRequest getRequest() {
        SearchRequest request = super.getRequest();
        functionScoreQueryBuilder = this.configScoreFunc();
        super.getSource().query(functionScoreQueryBuilder);
        return request;
    }

    /**
     * xxx:
     *  此处得设计有一些割裂
     *
     */
    public void addFilterFunc(ScoreFunctionBuilder scoreFunctionBuilder) {
        filterFuncArr.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(scoreFunctionBuilder));
    }

    public void addFilterFunc(QueryBuilder queryBuilder, ScoreFunctionBuilder scoreFunctionBuilder) {
        filterFuncArr.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(queryBuilder, scoreFunctionBuilder));
    }

    /**
     *  对function_score进行配置
     */
    private FunctionScoreQueryBuilder configScoreFunc() {
        BoolQueryBuilder queryBuilder = super.getQueryBuilder();
        EsQueryIndexBean indexConfig = super.getIndexConfig();
        FuncScoreBean funcScoreBean = indexConfig.getFuncScoreBean();
        if (funcScoreBean.getWeight() > 0) {
            WeightBuilder weightBuilder = ScoreFunctionBuilders.weightFactorFunction(funcScoreBean.getWeight());
            filterFuncArr.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(weightBuilder));
        }
        boolean hasClauses = queryBuilder.hasClauses();
        if (hasClauses) {
            functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryBuilder, filterFuncArr.toArray(new FunctionScoreQueryBuilder.FilterFunctionBuilder[0]));
        } else {
            functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(filterFuncArr.toArray(new FunctionScoreQueryBuilder.FilterFunctionBuilder[0]));
        }
        functionScoreQueryBuilder.scoreMode(funcScoreBean.getScoreMode());
        functionScoreQueryBuilder.boostMode(funcScoreBean.getBoostMode());
        functionScoreQueryBuilder.maxBoost(funcScoreBean.getMaxBoost());
        if (funcScoreBean.getMinScore() > 0) {
            functionScoreQueryBuilder.setMinScore(funcScoreBean.getMinScore());
        }
        return functionScoreQueryBuilder;
    }
}
