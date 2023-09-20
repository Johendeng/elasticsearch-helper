package org.pippi.elasticsearch.test.repository.mapper;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.lambda.wrapper.EsWrappers;
import org.pippi.elasticsearch.helper.model.resp.AggRes;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author JohenDeng
 * @date 2023/9/20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class AccountLambdaQueryTest {

    @Resource
    private EsAccountMapper accountMapper;


    @Test
    public void aggTest() {
        AggRes aggRes = accountMapper.selectAgg(EsWrappers.lambdaQuery(AccountEntity.class)
                .term(AccountEntity::getGender, "F")
                .agg(AggregationBuilders.terms("_city_distribution").field("city").size(1000)
                        .subAggregation(AggregationBuilders.count("_person_count").field("city"))));
        System.out.println(SerializerUtils.parseObjToJsonPretty(aggRes.fetchByPath("$._city_distribution")));
    }

    @Test
    public void selectCountTest() {
        Long count = accountMapper.selectCount(EsWrappers.lambdaQuery(AccountEntity.class)
                .queryString("Street AND Tabor", AccountEntity::getAddress));
        System.out.println(count);
    }
}
