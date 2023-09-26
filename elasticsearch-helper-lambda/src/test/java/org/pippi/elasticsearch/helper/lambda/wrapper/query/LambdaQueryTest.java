package org.pippi.elasticsearch.helper.lambda.wrapper.query;

import org.junit.Test;
import org.pippi.elasticsearch.helper.model.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.lambda.entity.AccountEntity;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
public class LambdaQueryTest extends BaseTest {

    @Test
    public void fillQueryTest() {
        EsLambdaQueryWrapper<AccountEntity> wrapper = new EsLambdaQueryWrapper<>(AccountEntity.class);
        wrapper.term(AccountEntity::getAccountNumber, 11)
                .must().term(AccountEntity::getCity, "test");
        System.out.println(SerializerUtils.parseObjToJsonPretty(wrapper));
    }


}
