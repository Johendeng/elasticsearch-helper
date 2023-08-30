package org.pippi.elasticsearch.test.original_api;

import com.google.common.collect.Maps;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author JohenDeng
 * @date 2023/8/28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class UpsetTest {

    @Resource
    private RestHighLevelClient client;


    @Test
    public void updateTest() throws IOException {
        UpdateRequest up = new UpdateRequest();
        up.index("account");
        up.id("1");
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("firstname", "deng");
        System.out.println(SerializerUtils.parseObjToJson(map));
        up.doc(SerializerUtils.parseObjToJson(map), XContentType.JSON);
        client.update(up, RequestOptions.DEFAULT);
    }

}
