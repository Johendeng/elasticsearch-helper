package org.pippi.elasticsearch.test.repository.mapper;

import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.model.utils.JacksonUtils;
import org.pippi.elasticsearch.helper.lambda.wrapper.EsWrappers;
import org.pippi.elasticsearch.helper.lambda.wrapper.query.EsLambdaQueryWrapper;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author JohenDeng
 * @date 2023/9/13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class AccountCUDTest {

    @Resource
    private EsAccountMapper accountMapper;

    @Test
    @Ignore
    public void insertTest() {
        AccountEntity entity = new AccountEntity();
        entity.setDocId("1113");
        entity.setAccountNumber(1113);
        entity.setCity("Nogal");
        entity.setAddress("fengyang");
        entity.setAge(14);
        entity.setBalance(1145);
        entity.setEmail("555887@qq.com");
        entity.setFirstname("da");
        entity.setLastname("ssm");
        entity.setEmployer("mk");
        entity.setState("NN");
        entity.setGender("F");
        int insert = accountMapper.insert(entity);
        System.out.println(insert);
    }

    @Test
    @Ignore
    public void updateTest() {
        AccountEntity entity = new AccountEntity();
        entity.setDocId("1112");
        entity.setFirstname("zhao");
        int i = accountMapper.updateById(entity);
        System.out.println(i);
    }

    @Test
    @Ignore
    public void updateWrapperTest() {
        AccountEntity entity = new AccountEntity();
        entity.setFirstname("wang");
        BulkByScrollResponse resp = accountMapper.update(entity, new EsLambdaQueryWrapper<>(AccountEntity.class)
                .term(AccountEntity::getAccountNumber, "1112"));
        System.out.println(JacksonUtils.parseObjToJsonPretty(resp));
    }

    @Test
    @Ignore
    public void deleteByIdTest() {
        int i = accountMapper.deleteById(1112);
        Assert.assertTrue(i == 1);
    }

    @Test
    @Ignore
    public void deleteByQueryTest() {
        BulkByScrollResponse resp = accountMapper.delete(EsWrappers.lambdaQuery(AccountEntity.class)
                .term(AccountEntity::getAccountNumber, 1112));
        System.out.println(JacksonUtils.parseObjToJsonPretty(resp));
    }
}
