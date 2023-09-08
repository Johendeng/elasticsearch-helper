package org.pippi.elasticsearch.test.repository.mapper;


import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsMapper;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Gte;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Terms;
import org.pippi.elasticsearch.helper.model.resp.BaseResp;
import org.pippi.elasticsearch.test.params.*;
import org.pippi.elasticsearch.test.params.func.*;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;

import java.util.List;


/**
 * EsTermMapper
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@EsMapper
public interface EsHandleMapper {

    /**
     * @param param simple es query
     */
    BaseResp<AccountEntity> termQuery(TermQueryParam param);

    BaseResp<AccountEntity> fuzzyQuery(FuzzyQueryParam param);

    BaseResp<AccountEntity> matchQuery(MatchQueryParam param);

    BaseResp<AccountEntity> matchPhraseQuery(MatchPhraseQueryParam param);

    BaseResp<AccountEntity> matchPhrasePrefixQuery(MatchPhrasePrefixQueryParam param);

    BaseResp<AccountEntity> moreLikeThisQuery(MoreLikeThisQueryParam param);

    BaseResp<AccountEntity> multiMatchQuery(MultiMatchQueryParam param);

    BaseResp<AccountEntity> pageAndRangeQuery(PageAndOrderQueryRankParam param);

    BaseResp<AccountEntity> queryStringQuery(QueryStringQueryParam param);

    BaseResp<AccountEntity> scriptQuery(ScriptQueryParam param);

    BaseResp<AccountEntity> searchAfterQuery(SearchAfterQueryParam param);

    BaseResp<AccountEntity> nestedQuery(NestedQueryParam param);

    BaseResp<AccountEntity> sourceOrderQuery(SourceOrderQueryParam param);

    BaseResp<AccountEntity> spanTermQuery(SpanTermQueryParam param);

    BaseResp<AccountEntity> wildCardQuery(WildCardQueryParam param);

    BaseResp<AccountEntity> functionGaussDecayQuery(GaussDecayParam param);

    BaseResp<AccountEntity> functionExpDecayQuery(ExponentialDecayParam param);

    BaseResp<AccountEntity> functionLinerDecayQuery(LinerDecayParam param);

    BaseResp<AccountEntity> functionRanScoreQuery(RandomScoreParam param);

    BaseResp<AccountEntity> functionFieldValueQuery(FieldValueParam param);

    BaseResp<AccountEntity> functionScriptQuery(ScriptFuncParam param);

    @EsAnnQueryIndex(index = "account")
    List<AccountEntity> methodQueryTest(@Gte Integer age,
                                        @Terms(@Base("firstname.keyword")) String ... firstname);
}
