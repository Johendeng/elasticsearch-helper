package org.pippi.elasticsearch.helper.spring.proxy;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.proxy.EsOperationProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Objects;

/**
 * EsHelperProxyBeanFactory
 *
 * @author     JohenTeng
 * @date      2021/9/18
 */
public class EsHelperProxyBeanFactory<T> implements ApplicationContextAware,InitializingBean,FactoryBean<T> {

    private final Class<T> targetInterfaceClazz;

    private final boolean visitQueryBeanParent;

    private ApplicationContext applicationContext;

    private RequestOptions requestOption;

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
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() {
        return (T) EsOperationProxy.build(targetInterfaceClazz, visitQueryBeanParent, requestOption);
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
    }
}
