package org.pippi.elasticsearch.test.params.func;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore;
import org.pippi.elasticsearch.helper.model.annotations.mapper.func.FuncScore_RandomScore;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Match;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;

/**
 * @author: JohenTeng
 * @date: 2022/8/17
 **/
@EsAnnQueryIndex(index = "account", model = QueryModel.FUNC_SCORE, traceScore = true,
        funcScore = @FuncScore(boostMode = CombineFunction.SUM)
)
public class RandomScoreParam {

    @Match(fuzziness = FuzzinessEnum.ONE)
    private String address;

    @FuncScore_RandomScore(field = "age")
    private String seed;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }
}

