package org.pippi.elasticsearch.test.repository.mapper;

import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
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
public class AccountBaseMapperTest {

    @Resource
    private EsAccountMapper accountMapper;

    @Test
    @Ignore
    public void insertTest() {
        AccountEntity entity = new AccountEntity();
        entity.setDocId("1112");
        entity.setAccountNumber(1112);
        entity.setCity("beijing");
        entity.setAddress("fengyang");
        entity.setAge(32);
        entity.setBalance(2213);
        entity.setEmail("54566874@qq.com");
        entity.setFirstname("wang");
        entity.setLastname("wu");
        entity.setEmployer("lisi");
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
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));
    }


}
