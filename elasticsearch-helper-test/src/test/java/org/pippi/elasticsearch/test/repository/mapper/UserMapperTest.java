package org.pippi.elasticsearch.test.repository.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.model.utils.JacksonUtils;
import org.pippi.elasticsearch.helper.lambda.wrapper.EsWrappers;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.pippi.elasticsearch.test.repository.entity.UserEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JohenDeng
 * @date 2023/9/5
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;


    @Test
    public void testByAccount() {
        System.out.println(JacksonUtils.parseObjToJsonPretty(userMapper.selectByAccount(1)));
    }

    @Test
    public void testByNested() {
        UserEntity.DetailInfo info = new UserEntity.DetailInfo();
        info.setAge(20);
        List<UserEntity> res = userMapper.selectByNested(info);
        System.out.println(JacksonUtils.parseObjToJsonPretty(res));
    }

    @Test
    public void selectTest() {
        List<UserEntity> list = userMapper.selectList(EsWrappers.lambdaQuery(UserEntity.class)
                        .nested("detail_info", EsWrappers.lambdaQuery(UserEntity.DetailInfo.class)
                                .gte(UserEntity.DetailInfo::getAge, 20)));

        UserEntity userEntity = userMapper.selectOne(EsWrappers.lambdaQuery(UserEntity.class)
                .term(UserEntity::getAccountNumber, 1));

        System.out.println(JacksonUtils.parseObjToJsonPretty(list));

        System.out.println(JacksonUtils.parseObjToJsonPretty(userEntity));
    }

}
