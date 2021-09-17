package org.pippi.elasticsearch.helper.sample.query;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.DefaultEsSearchHelper;
import org.pippi.elasticsearch.helper.core.helper.EsResponseParseHelper;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;
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
public class EsQueryHelperTest {

    private static final Logger log = LoggerFactory.getLogger(EsQueryHelperTest.class);

    private static RestHighLevelClient client;

    private static final String _LOCAL_DEV = "localhost";
    private static final String _TEST_INDEX = "content_s";

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
        DefaultEsSearchHelper esSearchHelper = EsSearchHelper.builder()
                .index(_TEST_INDEX)
                .clazz(DefaultEsSearchHelper.class)
                .build();
//        "title"
        esSearchHelper.getBool()
                .should(QueryBuilders.termQuery("intensity", 6))
                .should(
                        QueryBuilders.multiMatchQuery("燃脂", "title")
                )
//                .must(
//                        QueryBuilders.functionScoreQuery(
//                                ScoreFunctionBuilders.scriptFunction(
//                                        "(_doc['intensity'].value + _doc['title'].value) * 2"
//                                )
//                        )
//                )
        ;

        esSearchHelper.getSource()
                .trackScores(true)
                .sort(SortBuilders.scriptSort(
                new Script("return Math.round(_score)"),
                ScriptSortBuilder.ScriptSortType.NUMBER
        ).order(SortOrder.DESC));

//        esSearchHelper.getSource().trackScores(true)
////                      .sort("_score", SortOrder.DESC)
//                      .sort("intensity", SortOrder.DESC);

        System.out.println(esSearchHelper.getSource().toString());

        SearchResponse resp = client.search(esSearchHelper.getRequest(), RequestOptions.DEFAULT);
        BaseResp parseRes = EsResponseParseHelper.getListMap(resp);
        log.info(SerializerUtils.parseObjToJsonPretty(parseRes));
    }


}
