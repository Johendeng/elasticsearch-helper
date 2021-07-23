package org.pippi.elasticsearch.helper.core;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/3/12
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class EsDataModifyHelper {

    private static final Logger log = LoggerFactory.getLogger(EsDataModifyHelper.class);

    public RestHighLevelClient client;

    public EsDataModifyHelper(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     *  异步批量提交线程
     */
    public BulkProcessor bulkProcessor;

    /**
     *  插入单条数据
     * @param indexName
     * @param obj
     */
    public void insert(String indexName, Object obj) {
        IndexRequest request = new IndexRequest(indexName);
        request.source(XContentType.JSON, SerializerUtils.parseObjToJsonSkipNull(obj));
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
