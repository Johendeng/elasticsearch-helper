package com.poet.elasticsearch.helper;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/3/24
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class DefaultEsSearchHelper extends EsSearchHelper {

    public DefaultEsSearchHelper(String indexName, RestHighLevelClient client) {
        super(indexName, client);
    }
}
