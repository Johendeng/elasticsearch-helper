package org.pippi.elasticsearch.helper.spring;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author JohenDeng
 * @date 2023/8/29
 **/
public interface EsHelperCustomerConfig {


    /**
     * 定义高亮功能的实现
     */
    Map<String, Supplier<HighlightBuilder>> declareHighLight();

    Map<String, RequestOptions> declareRequestOpt();
}
