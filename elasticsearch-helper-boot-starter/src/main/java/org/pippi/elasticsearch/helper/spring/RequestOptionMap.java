package org.pippi.elasticsearch.helper.spring;

import org.elasticsearch.client.RequestOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JohenDeng
 * @date 2022/10/26
 **/
public class RequestOptionMap {

    private static final Map<String, RequestOptions>  REQ_OPT_MAP = new HashMap<>();

    /**
     * 新增es请求配置
     * @param key {@link org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy} {@see requestOption}
     * @param requestOption 目标配置
     */
    public static void add(String key, RequestOptions requestOption) {
        REQ_OPT_MAP.put(key, requestOption);
    }

    /**
     * 获取 es请求配置
     * @param key 配置key
     * @return 目标配置
     */
    public static RequestOptions get(String key) {
        return REQ_OPT_MAP.getOrDefault(key, RequestOptions.DEFAULT);
    }
}
