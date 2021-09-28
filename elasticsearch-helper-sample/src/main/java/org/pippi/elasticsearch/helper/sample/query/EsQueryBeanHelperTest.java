package org.pippi.elasticsearch.helper.sample.query;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ZeroTermsQueryOption;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.helper.EsResponseParseHelper;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.holder.BoolEsRequestHolder;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/8/3
 */
public class EsQueryBeanHelperTest {

    private static final Logger log = LoggerFactory.getLogger(EsQueryBeanHelperTest.class);

    private static final String _LOCAL_DEV = "localhost";
    private static final String _TEST_INDEX = "content_s";

    private static RestHighLevelClient client;

    static {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(_LOCAL_DEV, 9200)));
    }

    /**
     * 默认的中文分词将 中文直接拆分为单个汉字，没有词汇匹配，
     * 因此会导致 matchQuery 以及 fuzzyQuery 失效
     *
     * @throws IOException
     */
    @Test
    public void testQueryHelper() throws IOException {
        BoolEsRequestHolder esSearchHelper = AbstractEsRequestHolder.builder()
                .queryModel(QueryModel.BOOL)
                .indexName(_TEST_INDEX)
                .build();

        esSearchHelper.getQueryBuilder()
                .should(
                        QueryBuilders.multiMatchQuery("hi", "title", "describe")
                        .zeroTermsQuery(ZeroTermsQueryOption.ALL)
                        .fuzziness(Fuzziness.ONE)
                )
        ;

        esSearchHelper.getSource().highlighter(SearchSourceBuilder.highlight().field("title").field("describe"));


        System.out.println(esSearchHelper.getSource().toString());

        SearchResponse resp = client.search(esSearchHelper.getRequest(), RequestOptions.DEFAULT);
        BaseResp parseRes = EsResponseParseHelper.getListMap(resp);
        log.info(SerializerUtils.parseObjToJsonPretty(parseRes));
    }


}
