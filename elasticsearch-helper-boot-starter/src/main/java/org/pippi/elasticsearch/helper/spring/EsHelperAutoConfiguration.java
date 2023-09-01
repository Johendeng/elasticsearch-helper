package org.pippi.elasticsearch.helper.spring;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.pippi.elasticsearch.helper.core.QueryHandlerFactory;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.config.ExtendQueryFeatureHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * EsHelperAutoConfiguration
 *
 * @author     JohenTeng
 * @date      2021/9/17
 */
@Configuration
public class EsHelperAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * user define config, { System.getProperty(_EXT_DEFINE_QUERY_HANDLE_KEY) }
     * format : es.helper.ext.handles=com.***.loc1,com.***.loc2
     * just the package loc, but it's also support define a explicit path like：com.XXX.xxx.TestQueryHandle
     */
    public static final String _EXT_DEFINE_QUERY_HANDLE_PROPERTY_PROP = "es.helper.ext.handle.packages";

    public static final String _ENABLE_LOG_OUT_PROPERTIES_PROP = "es.helper.queryLogOut.enable";

    public static final String _MAP_UNDERSCORE_TO_CAMEL_CASE_PROP = "es.helper.configuration.map-underscore-to-camel-case";

    public static final String _UPDATE_STRATEGY_PROP = "es.helper.configuration.field-strategy";

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private static final String HIGH_LIGHT_DEFAULT_KEY = "default";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null) {
            Assert.notNull(restHighLevelClient, "Application-Context has no RestHighLevelClient-instance,you have to config it at first");
            QueryHandlerFactory.doQueryHandleScan();
            // if user undefine global-highlight style,
            // will use default (es default highlight style)
            ExtendQueryFeatureHolder.configHighLight(HIGH_LIGHT_DEFAULT_KEY , SearchSourceBuilder::highlight);
            ApplicationContext applicationContext = event.getApplicationContext();
            Map<String, EsHelperCustomerConfig> customerConfigMap = applicationContext.getBeansOfType(EsHelperCustomerConfig.class);
            if (!customerConfigMap.isEmpty()) {
                customerConfigMap.values().forEach(customerConfig -> {
                            Map<String, Supplier<HighlightBuilder>> highLightMap = customerConfig.declareHighLight();
                            if (highLightMap != null && !highLightMap.isEmpty()) {
                                highLightMap.forEach(ExtendQueryFeatureHolder::configHighLight);
                            }
                            Map<String, RequestOptions> reqOptMap = customerConfig.declareRequestOpt();
                            if (reqOptMap != null && !reqOptMap.isEmpty()) {
                                ReqOptionsHolder.putAll(reqOptMap);
                            }
                        });
            }
            Environment env = applicationContext.getEnvironment();
            Optional.ofNullable(env.getProperty(_EXT_DEFINE_QUERY_HANDLE_PROPERTY_PROP))
                    .ifPresent(EsHelperConfiguration::setExtDefineQueryHandlerProperty);
            Optional.ofNullable(env.getProperty(_ENABLE_LOG_OUT_PROPERTIES_PROP, Boolean.class))
                    .ifPresent(EsHelperConfiguration::setStatementLogOut);
            Optional.ofNullable(env.getProperty(_MAP_UNDERSCORE_TO_CAMEL_CASE_PROP, Boolean.class))
                    .ifPresent(EsHelperConfiguration::setMapUnderscoreToCamelCase);
            Optional.ofNullable(env.getProperty(_UPDATE_STRATEGY_PROP))
                    .ifPresent(EsHelperConfiguration::setGlobalUpdateStrategy);
        }
    }
}
