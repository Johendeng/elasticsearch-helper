package org.pippi.elasticsearch.helper.sample.query;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.pippi.elasticsearch.helper.core.DefaultEsSearchHelper;
import org.pippi.elasticsearch.helper.core.EsResponseParseHelper;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/3
 */
public class EsQueryHelperTest {

    private static final Logger log = LoggerFactory.getLogger(EsQueryHelperTest.class);

    private static RestHighLevelClient client;

    private static final String _LOCAL_DEV = "localhost";
    private static final String _TEST_INDEX = "news_record";

    static {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(_LOCAL_DEV, 9200)));
    }

    /**
     *  默认的中文分词将 中文直接拆分为单个汉字，没有词汇匹配，
     *  因此会导致 matchQuery 以及 fuzzyQuery 失效
     *
     * @throws IOException
     */
    @Test
    public void testQueryHelper () throws IOException {
        DefaultEsSearchHelper esSearchHelper = EsSearchHelper.builder()
                .index(_TEST_INDEX)
                .clazz(DefaultEsSearchHelper.class)
                .build();
        EsSearchHelper helper = esSearchHelper.match("content", "花目");



        SearchResponse resp = client.search(helper.getRequest(), RequestOptions.DEFAULT);
        List<Map<String, Object>> res = EsResponseParseHelper.getList(resp);
        log.info(SerializerUtils.parseObjToJsonPretty(res));
    }


}
