package org.pippi.elasticsearch.helper.core.cud;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.model.exception.EsHelperDataModifyException;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

import java.io.IOException;

/**
 * @author JohenDeng
 * @date 2023/8/28
 **/
public class InsertImpl<T extends EsEntity> {

    private final RestHighLevelClient client;

    public InsertImpl(RestHighLevelClient client) {
        this.client = client;
    }

    public void insert(T entity, RequestOptions reqOpt) {
        IndexRequest req = new IndexRequest();
        req.source(SerializerUtils.parseObjToJson(entity), XContentType.JSON);
        try {
            client.index(req, reqOpt);
        } catch (IOException e) {
            throw new EsHelperDataModifyException(e);
        }
    }

}
