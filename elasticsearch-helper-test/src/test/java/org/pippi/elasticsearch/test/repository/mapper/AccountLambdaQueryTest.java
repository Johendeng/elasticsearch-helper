package org.pippi.elasticsearch.test.repository.mapper;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.lambda.wrapper.EsWrappers;
import org.pippi.elasticsearch.helper.model.param.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.model.resp.AggRes;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

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
        System.out.println(SerializerUtils.parseObjToJsonPretty(aggRes.fetchByPath("$._city_distribution.Nogal")));
    }

    @Test
    public void termTest() {
        List<AccountEntity> list = accountMapper.selectList(EsWrappers.lambdaQuery(AccountEntity.class)
                .term(AccountEntity::getFirstname, "Dena"));
        System.out.println(SerializerUtils.parseObjToJsonPretty(list));
    }

    @Test
    public void matchTest() {
        List<AccountEntity> list = accountMapper.selectList(EsWrappers.lambdaQuery(AccountEntity.class)
                .match(AccountEntity::getFirstname, "Dena"));
        System.out.println(SerializerUtils.parseObjToJsonPretty(list));
    }

    @Test
    public void wildCardTest() {
        List<AccountEntity> list = accountMapper.selectList(EsWrappers.lambdaQuery(AccountEntity.class).filter()
                .wildCard(AccountEntity::getFirstname, "*ure*"));
        System.out.println(SerializerUtils.parseObjToJsonPretty(list));
    }

    @Test
    public void queryStringTest() {
        List<AccountEntity> list = accountMapper.selectList(EsWrappers.lambdaQuery(AccountEntity.class)
                .queryString("857 OR Castleton", AccountEntity::getAddress, AccountEntity::getEmail, AccountEntity::getCity));
        System.out.println(SerializerUtils.parseObjToJsonPretty(list));
    }

    @Test
    public void multiMatchTest() {
        List<AccountEntity> list = accountMapper.selectList(EsWrappers.lambdaQuery(AccountEntity.class)
                .multiMatch("Nogal", AccountEntity::getCity));
        System.out.println(SerializerUtils.parseObjToJsonPretty(list));
    }

    @Test
    public void moreLikeThisTest() {
        MoreLikeThisParam param = new MoreLikeThisParam();
        param.setTexts(new String[]{"Olson"});
//        MoreLikeThisQueryBuilder.Item item = new MoreLikeThisQueryBuilder.Item(null, "102");
//        item.fields("email");
//        param.setItems(new MoreLikeThisQueryBuilder.Item[]{item});
        List<AccountEntity> list = accountMapper.selectList(EsWrappers.lambdaQuery(AccountEntity.class)
                .moreLikeThis(param, AccountEntity::getLastname, AccountEntity::getFirstname));
        System.out.println(SerializerUtils.parseObjToJsonPretty(list));
    }

}
