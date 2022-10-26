package org.pippi.elasticsearch.helper.spring.proxy;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.proxy.EsQueryProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Objects;
import java.util.Optional;

/**
 * EsHelperProxyBeanFactory
 *
 * @author     JohenTeng
 * @date      2021/9/18
 */
public class EsHelperProxyBeanFactory<T> implements ApplicationContextAware,InitializingBean,FactoryBean<T> {

    private final Class<T> targetInterfaceClazz;

    private final boolean visitQueryBeanParent;

    private RestHighLevelClient client;

    private ApplicationContext applicationContext;

    private RequestOptions requestOption;

    private static final String ENABLE_LOG_OUT_PROPERTIES = "es.helper.queryLogOut.enable";

    private boolean enableLogOut = false;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public EsHelperProxyBeanFactory(Class<T> targetInterfaceClazz, boolean visitQueryBeanParent) {
        this.targetInterfaceClazz = targetInterfaceClazz;
        this.visitQueryBeanParent = visitQueryBeanParent;
    }

    public EsHelperProxyBeanFactory(Class<T> targetInterfaceClazz, RequestOptions requestOption, boolean visitQueryBeanParent) {
        this.targetInterfaceClazz = targetInterfaceClazz;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.requestOption = requestOption;
    }

    public EsHelperProxyBeanFactory(Class<T> targetInterfaceClazz, boolean visitQueryBeanParent, RestHighLevelClient client) {
        this.targetInterfaceClazz = targetInterfaceClazz;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.client = client;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() {
        return (T)EsQueryProxy.build(targetInterfaceClazz, visitQueryBeanParent, client, requestOption, enableLogOut);
    }

    @Override
    public Class<?> getObjectType() {
        return targetInterfaceClazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        RestHighLevelClient restClient = applicationContext.getBean(RestHighLevelClient.class);
        Objects.requireNonNull(restClient, "SpringContext haven't RestHighLevelClient, config it");
        this.client = restClient;
        this.enableLogOut = Optional.ofNullable(
            applicationContext.getEnvironment().getProperty(ENABLE_LOG_OUT_PROPERTIES, Boolean.class)
        ).orElse(true);
    }
}
