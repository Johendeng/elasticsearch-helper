package org.pippi.elasticsearch.test.original_api;

import com.google.common.collect.Maps;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.model.utils.JacksonUtils;
import org.pippi.elasticsearch.helper.model.exception.EsHelperException;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2023/8/28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
@Ignore
public class UpsetTest {

    @Resource
    private RestHighLevelClient client;


    @Test
    public void insertTest() {
        AccountEntity entity = new AccountEntity();
        entity.setAccountNumber(8888);
        entity.setCity("chongqing");
        entity.setAddress("shuangliu");
        entity.setAge(31);
        entity.setBalance(2222);
        entity.setEmail("1222@qq.com");
        entity.setFirstname("aaaa");
        entity.setLastname("dddd");


        Map<String, Object> map = EsBeanFieldTransUtils.toMap(entity);
        IndexRequest req = new IndexRequest("account");
        req.source(JacksonUtils.parseObjToJson(map), XContentType.JSON);
        try {
            IndexResponse resp = client.index(req, RequestOptions.DEFAULT);
            System.out.println(JacksonUtils.parseObjToJsonPretty(resp));
        } catch (IOException e) {
            throw new EsHelperException(e);
        }

    }


    @Test
    public void updateTest() throws IOException {
        UpdateRequest up = new UpdateRequest();
        up.index("account");
        up.id("1");
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("firstname", "deng555");
        System.out.println(JacksonUtils.parseObjToJson(map));
        up.doc(JacksonUtils.parseObjToJson(map), XContentType.JSON);
        UpdateResponse update = client.update(up, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.parseObjToJsonPretty(update));
    }

    @Test
    public void deleteTest() throws IOException {
        DeleteRequest req = new DeleteRequest("account");
        req.id("1");
        DeleteResponse delete = client.delete(req, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.parseObjToJsonPretty(delete));
    }

}
