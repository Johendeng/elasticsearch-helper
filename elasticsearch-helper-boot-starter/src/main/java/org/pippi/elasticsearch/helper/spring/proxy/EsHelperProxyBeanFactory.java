package org.pippi.elasticsearch.helper.spring.proxy;

import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.proxy.EsQueryProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * EsHelperProxyBeanFactory
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
public class EsHelperProxyBeanFactory<T> implements ApplicationContextAware,InitializingBean,FactoryBean<T> {

    private Class<T> targetInterfaceClazz;

    private boolean visitQueryBeanParent;

    private RestHighLevelClient client;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public EsHelperProxyBeanFactory(Class<T> targetInterfaceClazz, boolean visitQueryBeanParent) {
        this.targetInterfaceClazz = targetInterfaceClazz;
        this.visitQueryBeanParent = visitQueryBeanParent;
    }

    public EsHelperProxyBeanFactory(Class<T> targetInterfaceClazz, boolean visitQueryBeanParent, RestHighLevelClient client) {
        this.targetInterfaceClazz = targetInterfaceClazz;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.client = client;
    }

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(
                targetInterfaceClazz.getClassLoader(),
                new Class[]{targetInterfaceClazz},
                new EsQueryProxy<T>(targetInterfaceClazz, visitQueryBeanParent, client)
        );
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
    public void afterPropertiesSet() throws Exception {
        RestHighLevelClient restClient = applicationContext.getBean(RestHighLevelClient.class);
        Objects.requireNonNull(restClient, "SpringContext haven't RestHighLevelClient, config it");
        this.client = restClient;
    }
}
