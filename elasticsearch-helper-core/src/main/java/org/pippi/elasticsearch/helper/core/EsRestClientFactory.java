package org.pippi.elasticsearch.helper.core;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JohenDeng
 * @date 2023/9/8
 **/
public class EsRestClientFactory {

    private static final Map<String, RestHighLevelClient> CLIENT_MAP = new ConcurrentHashMap<>();

    public static final String PRIMARY = "primary";

    public static void loadClient(String key, RestHighLevelClient client) {
        CLIENT_MAP.put(key, client);
    }

    public static void loadPrimaryClient(RestHighLevelClient client) {
        CLIENT_MAP.put(PRIMARY, client);
    }

    public static RestHighLevelClient getClient(String key) {
        if (StringUtils.isBlank(key)) {
            return CLIENT_MAP.get(PRIMARY);
        }
        return CLIENT_MAP.get(key);
    }
}
