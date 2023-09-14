package org.pippi.elasticsearch.test.repository.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.lambda.wrapper.Wrappers;
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
        System.out.println(SerializerUtils.parseObjToJsonPretty(userMapper.selectByAccount(1)));
    }

    @Test
    public void testByNested() {
        UserEntity.DetailInfo info = new UserEntity.DetailInfo();
        info.setAge(20);
        List<UserEntity> res = userMapper.selectByNested(info);
        System.out.println(SerializerUtils.parseObjToJsonPretty(res));
    }

    @Test
    public void selectTest() {
        UserEntity list = userMapper.selectOne(Wrappers.lambdaQuery(UserEntity.class)
                        .config(1000, 0.0f, true)
                        .nested("detail_info", Wrappers.lambdaQuery(UserEntity.DetailInfo.class)
                                .term(UserEntity.DetailInfo::getAge, 28)));
        System.out.println(SerializerUtils.parseObjToJsonPretty(list));
    }

}
