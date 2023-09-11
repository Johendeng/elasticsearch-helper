package org.pippi.elasticsearch.helper.core.proxy;

import com.google.common.collect.Lists;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.model.config.EsHelperConfiguration;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author     JohenTeng
 * @date      2021/7/21
 */
@SuppressWarnings("all")
public class EsOperationProxy<T> implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(EsOperationProxy.class);

    private static final List<EsOperationExecutor> EXECUTORS = Lists.newArrayList();

    private Class<T> targetInterface;

    private boolean visitQueryBeanParent = true;

    private boolean enableLogOutEsQueryJson = EsHelperConfiguration.statementLogOut;

    private RequestOptions requestOption = RequestOptions.DEFAULT;

    static {
        EXECUTORS.add(AnnotationBeanQueryExecutor.executor());
        EXECUTORS.add(AnnotationMethodQueryExecutor.executor());
    }

    public EsOperationProxy(Class<T> targetInterface, boolean visitQueryBeanParent) {
        this.targetInterface = targetInterface;
        this.visitQueryBeanParent = visitQueryBeanParent;
    }

    public EsOperationProxy(Class<T> targetInterface, boolean visitQueryBeanParent, RequestOptions requestOption) {
        this.targetInterface = targetInterface;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.requestOption = requestOption;
    }

    public EsOperationProxy(Class<T> targetInterface, boolean visitQueryBeanParent, boolean enableLogOutEsQueryJson) {
        this.targetInterface = targetInterface;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.enableLogOutEsQueryJson = enableLogOutEsQueryJson;
    }

    public EsOperationProxy(Class<T> targetInterface, boolean visitQueryBeanParent, RequestOptions requestOption, boolean enableLogOutEsQueryJson) {
        this.targetInterface = targetInterface;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.requestOption = requestOption;
        this.enableLogOutEsQueryJson = enableLogOutEsQueryJson;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        for (EsOperationExecutor executor : EXECUTORS) {
            if (executor.condition(targetInterface, method, args)) {
                return executor.operate(targetInterface, requestOption, method, args, visitQueryBeanParent, enableLogOutEsQueryJson);
            }
        }
        throw new EsHelperConfigException("un-match any es-operate-executor");
    }

    public Class<T> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<T> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public boolean isVisitQueryBeanParent() {
        return visitQueryBeanParent;
    }

    public void setVisitQueryBeanParent(boolean visitQueryBeanParent) {
        this.visitQueryBeanParent = visitQueryBeanParent;
    }

    /**
     * 构建查询代理对象
     */
    public static <T> T build(Class<T> targetInterfaceClazz,
                               boolean visitParent,
                               RequestOptions requestOption,
                               boolean enableLogOut) {
        return (T) Proxy.newProxyInstance(
                targetInterfaceClazz.getClassLoader(),
                new Class[]{targetInterfaceClazz},
                new EsOperationProxy(targetInterfaceClazz, visitParent, requestOption, enableLogOut)
        );
    }

    /**
     * 构建查询代理对象
     */
    public static <T> T build(Class<T> targetInterfaceClazz,
                               boolean visitParent,
                               RequestOptions requestOption) {
        return (T) Proxy.newProxyInstance(
                targetInterfaceClazz.getClassLoader(),
                new Class[]{targetInterfaceClazz},
                new EsOperationProxy(targetInterfaceClazz, visitParent, requestOption)
        );
    }

    /**
     * 构建查询代理对象, 默认 visitParent: true, enableLogOut: true
     */
    public static <T> T build(Class<T> targetInterfaceClazz) {
        return build(targetInterfaceClazz, true, RequestOptions.DEFAULT, true);
    }

    public static <T> T build(Class<T> targetInterfaceClazz, RequestOptions requestOption) {
        return build(targetInterfaceClazz, true, requestOption, true);
    }
}
