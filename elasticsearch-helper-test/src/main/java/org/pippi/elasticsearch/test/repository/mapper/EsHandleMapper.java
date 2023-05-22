package org.pippi.elasticsearch.test.repository.mapper;


import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;
import org.pippi.elasticsearch.test.repository.entity.params.*;
import org.pippi.elasticsearch.test.repository.entity.params.func.*;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;


/**
 * EsTermMapper
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@EsHelperProxy
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
}
