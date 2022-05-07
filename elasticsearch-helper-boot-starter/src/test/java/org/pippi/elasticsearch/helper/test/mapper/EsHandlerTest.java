package org.pippi.elasticsearch.helper.test.mapper;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.EsHelperSampleApplication;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.PageParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping.FuzzyQueryBean;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.*;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountEntity;
import org.pippi.elasticsearch.helper.spring.repository.mapper.EsHandleMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * EsHandlerTest
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class EsHandlerTest {

    @Resource
    private EsHandleMapper esHandleMapper;

    @Test
    public void testTermQuery() {
        TermQueryParam param = new TermQueryParam();
        param.setGender("F");
        param.setFirstname("Good");
        param.setLastnames(new String[]{"Bates", "Olson", "Campbell"});
        BaseResp<AccountEntity> res = esHandleMapper.termQuery(param);
        System.out.println(res.getRecords().size());
        System.out.println(SerializerUtils.parseObjToJson(res));
        res.getRecords().forEach(data -> {
            Assert.assertEquals(data.getGender(), "F");
        });
    }

    @Test
    public void testFuzzyQuery() {
        FuzzyQueryParam param = new FuzzyQueryParam();
        param.setAddress("Strete");
        BaseResp<AccountEntity> resp = esHandleMapper.fuzzyQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
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
        param.setQueryText("street");
        BaseResp<AccountEntity> resp = esHandleMapper.multiMatchQuery(param);
        System.out.println(SerializerUtils.parseObjToJson(resp));
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

}
