package org.pippi.elasticsearch.helper.spring;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.pippi.elasticsearch.helper.core.config.GlobalEsQueryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * EsHelperAutoConfiguration
 *
 * @author     JohenTeng
 * @date      2021/9/17
 */
@Configuration
public class EsHelperAutoConfiguration {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private static final String DEFAULT_KEY = "default";

    @PostConstruct
    public void load(){
        Assert.notNull(restHighLevelClient, "Application-Context has no RestHighLevelClient-instance,you have to config it at first");
        QueryHandlerFactory.doQueryHandleScan();
        // if user undefine global-highlight style,
        // will use default (es default highlight style)
        GlobalEsQueryConfig.configHighLight(DEFAULT_KEY , SearchSourceBuilder::highlight);
    }



}
