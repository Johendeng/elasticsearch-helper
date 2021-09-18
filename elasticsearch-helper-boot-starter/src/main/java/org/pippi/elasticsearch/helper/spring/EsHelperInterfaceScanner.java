package org.pippi.elasticsearch.helper.spring;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.AnnotationUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;
import org.pippi.elasticsearch.helper.spring.proxy.EsHelperProxyBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * EsHelperInterfaceScanner
 *  read all Interface which annotated  {@link org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy @EsHelperProxy}
 *  and  load a proxy instance for it
 * @author JohenTeng
 * @date 2021/9/18
 */
@Component
public class EsHelperInterfaceScanner implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, ResourceLoaderAware {

    private static final Logger log = LoggerFactory.getLogger(EsHelperInterfaceScanner.class);

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private ApplicationContext applicationContext;
    private MetadataReaderFactory metadataReaderFactory;
    private ResourcePatternResolver resourcePatternResolver;

    private static final Set<Class<?>> _HELPER_PROXY_SET = Sets.newHashSet();

    //todo
    private static RestHighLevelClient client;
    static {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        List<String> packages = AutoConfigurationPackages.get(applicationContext);
        // 开始扫描包，获取字节码
        Set<Class<?>> beanClazzSet = findAllQueryHelperProxyInterfaces(packages.get(0));
        for (Class beanClazz : beanClazzSet) {
            // 判断是否是需要被代理的接口
            if (isNotNeedProxy(beanClazz)) {
                continue;
            }
            // BeanDefinition构建器
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getConstructorArgumentValues()
                      .addGenericArgumentValue(beanClazz);
//            definition.setBeanClass(EsHelperProxyBeanFactory.class);
            definition.setLazyInit(true);
            definition.setInstanceSupplier(()->
                new EsHelperProxyBeanFactory(beanClazz, true, client)
            );
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            String simpleName = beanClazz.getSimpleName();
            simpleName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
            registry.registerBeanDefinition(simpleName, definition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    private Set<Class<?>> findAllQueryHelperProxyInterfaces(String basePackage){
        Set<Class<?>> set = new LinkedHashSet<>();
        // 此处固定写法即可,含义就是包及子包下的所有类
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz;
                    try {
                        clazz = Class.forName(className);
                        set.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(this.applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    private boolean isNotNeedProxy(Class beanClazz) {
        return  null == AnnotatedElementUtils.findMergedAnnotation(beanClazz, EsHelperProxy.class);
    }
}
