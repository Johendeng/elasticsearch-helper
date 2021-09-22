package org.pippi.elasticsearch.helper.spring;

import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * EsHelperAutoConfiguration
 *
 * @author johen
 * @date 2021/9/17
 */
@Configuration
public class EsHelperAutoConfiguration {

    @Value("${es.helper.ext.handle.packages:''}")
    private String esQueryExtHandlePackages;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    public void load(){
        //TODO: 应该放出数个配置点 可以供用户进行扩展 配置？？？？   如何提供？ 提供几个？？   怎么提供？？
        Assert.notNull(restHighLevelClient, "Application-Context has no RestHighLevelClient-instance,you have to config it at first");
        QueryHandlerFactory.doQueryHandleScan();
    }

}
