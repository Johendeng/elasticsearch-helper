package org.pippi.elasticsearch.helper.test.mapper;

import com.google.common.collect.Maps;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.EsHelperSampleApplication;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.PageParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.func.mapping.ExponentialDecayBean;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.*;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.func.ExponentialDecayParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.func.GaussDecayParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.func.LinerDecayParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountEntity;
import org.pippi.elasticsearch.helper.spring.repository.mapper.EsHandleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * EsHandlerTest
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class EsHandlerTest {

    private static final Logger log = LoggerFactory.getLogger(EsHandlerTest.class);

    @Resource
    private EsHandleMapper esHandleMapper;

    @Test
    public void testTermQuery() {
        TermQueryParam param = new TermQueryParam();
        param.setGender("F");
        param.setFirstname("Good");
        param.setLastnames(new String[]{"Bates", "Olson", "Campbell"});
        BaseResp<AccountEntity> res = esHandleMapper.termQuery(param);
        log.info("res size:{}", res.getRecords().size());
        log.info("data: {}", SerializerUtils.parseObjToJson(res));
        res.getRecords().forEach(data -> {
            Assert.assertEquals(data.getGender(), "F");
        });
    }

    @Test
    public void testFuzzyQuery() {
        FuzzyQueryParam param = new FuzzyQueryParam();
        param.setAddress("Strete");
        BaseResp<AccountEntity> resp = esHandleMapper.fuzzyQuery(param);
        log.info("data: {}", SerializerUtils.parseObjToJson(resp));
        resp.getRecords().forEach(data -> {
            Assert.assertTrue(data.getAddress().contains("Street"));
        });
    }

    @Test
    public void testMachQuery() {
        MatchQueryParam param = new MatchQueryParam();
        param.setAddress("857 Tabor");
        BaseResp<AccountEntity> resp = esHandleMapper.matchQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
    }

    @Test
    public void testMatchPhraseQuery() {
        MatchPhraseQueryParam param = new MatchPhraseQueryParam();
        param.setAddress("857 Tabor");
        BaseResp<AccountEntity> resp = esHandleMapper.matchPhraseQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
    }

    @Test
    public void testMatchPhrasePrefixQuery() {
        MatchPhrasePrefixQueryParam param = new MatchPhrasePrefixQueryParam();
        param.setAddress("438 Street");
        BaseResp<AccountEntity> resp = esHandleMapper.matchPhrasePrefixQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
    }

    @Test
    public void testMoreLikeThisQuery() {
        MoreLikeThisQueryParam param = new MoreLikeThisQueryParam();
        MoreLikeThisParam likeParam = new MoreLikeThisParam();
        likeParam.setTexts(new String[]{"Morgan", "Street"});
        param.setAddress(likeParam);
        BaseResp<AccountEntity> resp = esHandleMapper.moreLikeThisQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
    }

    @Test
    public void testMultiMatchQuery() {
        MultiMatchQueryParam param = new MultiMatchQueryParam();
        param.setQueryText("live in Jacksonburg and 25 year's old");
        BaseResp<AccountEntity> resp = esHandleMapper.multiMatchQuery(param);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testPageAndRangeQuery() {
        PageAndOrderQueryRankParam param = new PageAndOrderQueryRankParam();

        RangeParam rangeParam = new RangeParam();
        rangeParam.setLeft(20);
        rangeParam.setRight(30);
        param.setAge(rangeParam);

        PageParam pageParam = new PageParam();
        pageParam.setPageSize(5);
        pageParam.setCurrent(1);
        LinkedHashMap<String, SortOrder> orderMap = new LinkedHashMap<>();
        orderMap.put("age", SortOrder.DESC);
        pageParam.setOrderMap(orderMap);

        param.setPage(pageParam);

        BaseResp<AccountEntity> resp = esHandleMapper.pageAndRangeQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
    }

    @Test
    public void testQueryStringQuery() {
        QueryStringQueryParam param = new QueryStringQueryParam();
        param.setQueryText("Street AND age:>30");
        BaseResp<AccountEntity> resp = esHandleMapper.queryStringQuery(param);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testScriptQuery() {
        ScriptQueryParam param = new ScriptQueryParam();

        Map<String, String> scriptMap = Maps.newHashMap();
        scriptMap.put("name", "B");
        param.setScriptMapParam(scriptMap);

        BaseResp<AccountEntity> resp = esHandleMapper.scriptQuery(param);
        resp.getRecords().forEach(record -> {
            Assert.assertTrue(record.getAge() % 2 == 0);
            Assert.assertTrue(record.getFirstname().startsWith("B"));
        });
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testSearchAfter() {
        SearchAfterQueryParam param = new SearchAfterQueryParam();
        param.setGender("F");
        param.setId(String.valueOf(Integer.MAX_VALUE));
        BaseResp<AccountEntity> resp = esHandleMapper.searchAfterQuery(param);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));

        SearchAfterQueryParam param2 = new SearchAfterQueryParam();
        param2.setGender("F");
        param2.setId(String.valueOf(991));
        BaseResp<AccountEntity> resp2 = esHandleMapper.searchAfterQuery(param2);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp2));
    }

    // 暂时无该用例对应数据集，暂时忽略
    @Test
    @Ignore
    public void testNestedQuery() {
        SimpleAccountQueryParam param = new SimpleAccountQueryParam();
        param.setState("DE");
        param.setAddress("River Street");
        param.setFuzzyField("Bates");

        NestedQueryParam nestedQueryParam = new NestedQueryParam();
        nestedQueryParam.setNestParam(param);
        nestedQueryParam.setAddress("street");

        BaseResp<AccountEntity> resp = esHandleMapper.nestedQuery(nestedQueryParam);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testSourceOrderQuery() {
        SourceOrderQueryParam param = new SourceOrderQueryParam();
        param.setQueryText("123");
        BaseResp<AccountEntity> resp = esHandleMapper.sourceOrderQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
    }

    @Test
    public void testSpanTermQuery() {
        SpanTermQueryParam param = new SpanTermQueryParam();
        param.setAddress("875");
        BaseResp<AccountEntity> resp = esHandleMapper.spanTermQuery(param);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testWildCardQuery() {
        WildCardQueryParam param = new WildCardQueryParam();
        param.setAddress("87*");
        BaseResp<AccountEntity> resp = esHandleMapper.wildCardQuery(param);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testFunctionGaussDecayQuery() {
        GaussDecayParam gaussDecayParam = new GaussDecayParam();
        gaussDecayParam.setBalance(5000);
        BaseResp<AccountEntity> resp = esHandleMapper.functionGaussDecayQuery(gaussDecayParam);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testFunctionExponentialDecayQuery() {
        ExponentialDecayParam expBean = new ExponentialDecayParam();
        expBean.setBalance(5000);
        expBean.setLastname("Olson");
        BaseResp<AccountEntity> resp = esHandleMapper.functionExpDecayQuery(expBean);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }

    @Test
    public void testFunctionLinerDecayQuery() {
        LinerDecayParam linerParam = new LinerDecayParam();
        linerParam.setBalance(5000);
        BaseResp<AccountEntity> resp = esHandleMapper.functionLinerDecayQuery(linerParam);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }
}
