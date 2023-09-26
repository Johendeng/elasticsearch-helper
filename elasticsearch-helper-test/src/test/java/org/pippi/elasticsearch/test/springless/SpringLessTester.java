package org.pippi.elasticsearch.test.springless;

import com.google.common.collect.Maps;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.junit.Assert;
import org.pippi.elasticsearch.helper.core.EsRestClientFactory;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.pippi.elasticsearch.helper.core.proxy.EsOperationProxy;
import org.pippi.elasticsearch.helper.model.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.model.resp.BaseResp;
import org.pippi.elasticsearch.test.params.TermQueryParam;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;
import org.pippi.elasticsearch.test.repository.mapper.EsHandleMapper;

import java.io.IOException;
import java.util.Map;

public class SpringLessTester {

    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)));

    static {
        QueryHandlerFactory.doQueryHandleScan();
        EsRestClientFactory.loadPrimaryClient(client);
    }

    public static void main(String[] args) {
//        springlessQueryTest();
        viewMapping();
    }

    public static void viewMapping() {
        Map<String, Object> mapping = getRemoteMappingDefine("account", "primary");
        System.out.println(SerializerUtils.parseObjToJsonPretty(
                mapping
        ));
    }

    private static void springlessQueryTest() {
        TermQueryParam param = new TermQueryParam();
        param.setGender("F");
        param.setFirstname("Fulton");
        param.setLastnames(new String[]{"Holt"});

        EsHandleMapper esHandleMapper = EsOperationProxy.build(EsHandleMapper.class);
        BaseResp<AccountEntity> res = esHandleMapper.termQuery(param);
        System.out.println(SerializerUtils.parseObjToJsonPretty(res));
        res.getRecords().forEach(data -> {
            Assert.assertEquals(data.getGender(), "F");
        });
        System.exit(0);
    }

    private static Map<String, Object> getRemoteMappingDefine(String index, String clientKey) {
        RestHighLevelClient client = EsRestClientFactory.getClient(clientKey);
        GetMappingsRequest mappingReq = new GetMappingsRequest();
        mappingReq.indices(index);
        try {
            GetMappingsResponse mapResp = client.indices().getMapping(mappingReq, RequestOptions.DEFAULT);
            return mapResp.mappings().get(index).getSourceAsMap();
        } catch (IOException e) {
            return Maps.newHashMap();
        }
    }
}
