package org.pippi.elasticsearch.helper.lambda.wrapper.query;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.EsRestClientFactory;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
public class BaseTest {

    private static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
            new HttpHost("localhost", 9200),
            new HttpHost("localhost", 9201),
            new HttpHost("localhost", 9202)
    ));

    static {
        QueryHandlerFactory.doQueryHandleScan();
        EsRestClientFactory.loadPrimaryClient(client);
    }

}
