package org.pippi.elasticsearch.helper.spring.proxy;

import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.proxy.EsQueryProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * EsHelperProxyBeanFactory
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
public class EsHelperProxyBeanFactory<T> implements FactoryBean<T> {

    private Class<T> targetInterfaceClazz;

    private boolean visitQueryBeanParent;

    private RestHighLevelClient client;

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

}
