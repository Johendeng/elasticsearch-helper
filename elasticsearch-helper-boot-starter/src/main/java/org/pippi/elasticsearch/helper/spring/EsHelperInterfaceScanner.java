package org.pippi.elasticsearch.helper.spring;

import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.hook.EsHookReedits;
import org.pippi.elasticsearch.helper.core.hook.UserHooks;
import org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy;
import org.pippi.elasticsearch.helper.spring.proxy.EsHelperProxyBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
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
import java.util.stream.Collectors;

/**
 * EsHelperInterfaceScanner
 *  read all Interface which annotated by
 *  {@link org.pippi.elasticsearch.helper.spring.annotation.EsHelperProxy @EsHelperProxy}
 *  and  load a proxy instance for it
 * @author     JohenTeng
 * @date      2021/9/18
 */
@Component
public class EsHelperInterfaceScanner implements ApplicationContextAware,
                                                 ResourceLoaderAware,
                                                 BeanDefinitionRegistryPostProcessor,
                                                 ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(EsHelperInterfaceScanner.class);

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private ApplicationContext applicationContext;
    private MetadataReaderFactory metadataReaderFactory;
    private ResourcePatternResolver resourcePatternResolver;

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
    @SuppressWarnings("all")
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // scan packages get all Class that annotation by @EsHelperProxy
        Set<Class<?>> beanClazzSet = this.findAllClazz();
        for (Class<?> beanClazz : beanClazzSet) {
            if (isNotNeedProxy(beanClazz)) {
                continue;
            }
            EsHelperProxy proxyAnn = AnnotationUtils.findAnnotation(beanClazz, EsHelperProxy.class);
            String requestOptKey = proxyAnn.requestOption();
            // BeanDefinition builder
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
            definition.setBeanClass(beanClazz);
            definition.setLazyInit(true);
            definition.setInstanceSupplier(()->
                new EsHelperProxyBeanFactory(beanClazz, RequestOptionMap.get(requestOptKey), proxyAnn.visitParent())
            );
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            String simpleName = beanClazz.getSimpleName();
            simpleName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
            registry.registerBeanDefinition(simpleName, definition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<Class<?>> beanClazzSet = this.findAllClazz();
        for (Class<?> clazz : beanClazzSet) {
            if (clazz.isAssignableFrom(UserHooks.class)) {
                EsHookReedits.loadHooksFromTargetInterface(clazz);
            }
        }
    }

    private Set<Class<?>> findAllClazz() {
        List<String> packages = AutoConfigurationPackages.get(applicationContext);
        // scan packages get all Class that annotation by @EsHelperProxy
        return packages.stream().flatMap(path -> findAllQueryHelperProxyInterfaces(path).stream()).collect(Collectors.toSet());
    }

    private Set<Class<?>> findAllQueryHelperProxyInterfaces(String basePackage){
        Set<Class<?>> set = new LinkedHashSet<>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz = Class.forName(className);
                    set.add(clazz);
                }
            }
        }catch (ClassNotFoundException e) {
            throw new EsHelperConfigException("Es-helper init Reflection ERROR, cause", e);
        } catch (IOException e) {
            throw new EsHelperConfigException("Es-helper init I/O ERROR, cause", e);
        }
        return set;
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(this.applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    private boolean isNotNeedProxy(Class<?> beanClazz) {
        return  null == AnnotatedElementUtils.findMergedAnnotation(beanClazz, EsHelperProxy.class);
    }
}
