package org.pippi.elasticsearch.helper.test.mapper;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.proxy.EsQueryProxy;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.spring.repository.entity.params.TermQueryParam;
import org.pippi.elasticsearch.helper.spring.repository.entity.result.AccountEntity;
import org.pippi.elasticsearch.helper.spring.repository.mapper.EsHandleMapper;

import java.lang.reflect.Proxy;

/**
 * @author: JohenTeng
 * @date: 2022/8/17
 **/
public class SpringLessTester {

    private static RestHighLevelClient client =  new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)));

    static {
        QueryHandlerFactory.doQueryHandleScan();
    }

    public static void main(String[] args) {
        TermQueryParam param = new TermQueryParam();
        param.setGender("F");
        param.setFirstname("Good");
        param.setLastnames(new String[]{"Bates", "Olson", "Campbell"});

        EsHandleMapper esHandleMapper = (EsHandleMapper)  EsQueryProxy.build(EsHandleMapper.class, client);

        BaseResp<AccountEntity> resp = esHandleMapper.termQuery(param);
        System.out.println(SerializerUtils.parseObjToJsonPretty(resp));

        System.exit(0);
    }
}
