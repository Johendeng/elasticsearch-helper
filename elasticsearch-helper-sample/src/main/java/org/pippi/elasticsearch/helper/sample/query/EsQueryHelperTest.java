package org.pippi.elasticsearch.helper.sample.query;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.pippi.elasticsearch.helper.core.DefaultEsSearchHelper;
import org.pippi.elasticsearch.helper.core.EsResponseParseHelper;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;

import java.io.IOException;

/**
 * 描述
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/3
 */
public class EsQueryHelperTest {

    private static RestHighLevelClient client;

    private static final String _LOCAL_DEV = "localhost";
    private static final String _TEST_INDEX = "news_record";

    static {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(_LOCAL_DEV, 9200)));
    }

    @Test
    public void testQueryHelper () throws IOException {
        DefaultEsSearchHelper esSearchHelper = EsSearchHelper.builder()
                .index(_TEST_INDEX)
                .clazz(DefaultEsSearchHelper.class)
                .build();
        EsSearchHelper helper = esSearchHelper.term("title", "黄瓜");
        SearchResponse resp = client.search(helper.getRequest(), RequestOptions.DEFAULT);
        EsResponseParseHelper.getList(resp);
    }


}
