package org.pippi.elasticsearch.helper.core.wrapper;

import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;

/**
 * @author JohenDeng
 * @date 2023/8/28
 **/
public class UpdateImpl<T extends EsEntity> {

    private final RestHighLevelClient client;

    public UpdateImpl(RestHighLevelClient client) {
        this.client = client;
    }




}
