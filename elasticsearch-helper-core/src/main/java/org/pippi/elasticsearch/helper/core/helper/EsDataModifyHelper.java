package org.pippi.elasticsearch.helper.core.helper;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @project: elasticsearch-helper
 * @package: org.pippi.elasticsearch.helper
 * @date:    2021/3/12
 * @author:  JohenTeng
 * @email: 1078481395@qq.com
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
        request.source(SerializerUtils.parseObjToJsonSkipNull(obj), XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insert(String indexName, String id, Object obj) {
        IndexRequest request = new IndexRequest(indexName);
        request.id(id).source(SerializerUtils.parseObjToJsonSkipNull(obj), XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(String indexName, String id, Object obj) {
        UpdateRequest request = new UpdateRequest();
        String s = SerializerUtils.parseObjToJsonSkipNull(obj);
        request.index(indexName).id(id).doc(s, XContentType.JSON);
        try {
            client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void batchUpdate(String indexName, List records){
        BulkRequest bulkRequest = new BulkRequest();
        for (Object record: records){
            UpdateRequest tempReq = new UpdateRequest();
            tempReq.doc(SerializerUtils.parseObjToJsonSkipNull(record), XContentType.JSON);
            bulkRequest.add(tempReq);
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
