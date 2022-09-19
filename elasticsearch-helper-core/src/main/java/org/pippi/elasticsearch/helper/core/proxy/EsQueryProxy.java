package org.pippi.elasticsearch.helper.core.proxy;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.EsQueryEngine;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.UseRequestHook;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.UseResponseHook;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.hook.EsHookReedits;
import org.pippi.elasticsearch.helper.core.hook.HookQuery;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.hook.ResponseHook;
import org.pippi.elasticsearch.helper.core.reader.EsResponseReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author     JohenTeng
 * @date      2021/7/21
 */
public class EsQueryProxy<T> implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(EsQueryProxy.class);

    private Class<T> targetInterface;

    private boolean visitQueryBeanParent = true;

    private RestHighLevelClient client;

    private boolean enableLogOutEsQueryJson = false;

    public EsQueryProxy(Class<T> targetInterface, boolean visitQueryBeanParent, RestHighLevelClient client) {
        this.targetInterface = targetInterface;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.client = client;
    }

    public EsQueryProxy(Class<T> targetInterface, boolean visitQueryBeanParent, RestHighLevelClient client, boolean enableLogOutEsQueryJson) {
        this.targetInterface = targetInterface;
        this.visitQueryBeanParent = visitQueryBeanParent;
        this.client = client;
        this.enableLogOutEsQueryJson = enableLogOutEsQueryJson;
    }

    @Override
    @SuppressWarnings(value = {"rawtypes", "unchecked"})
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (args == null || args.length != 1) {
            throw new EsHelperQueryException("ES-HELPER un-support multi-params or miss-param, params must be single");
        }
        Object param = args[0];
        AbstractEsRequestHolder esHolder = EsQueryEngine.execute(param, visitQueryBeanParent);
        RequestHook requestHook = null;
        if ((requestHook = checkRequestHook(param, method)) != null) {
            esHolder = requestHook.handleRequest(esHolder, param);
        }
        SearchResponse resp = null;
        try {
            SearchRequest request = esHolder.getRequest();
            if (enableLogOutEsQueryJson) {
                log.info("{} # {} execute-es-query-json is\n{}", targetInterface.getSimpleName(), method.getName(), esHolder.getSource().toString());
            }
            resp = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new EsHelperQueryException("EsSearchExecute I/O exception, cause:", e);
        }
        ResponseHook responseHook = null;
        if ((responseHook = checkResponseHook(param, method)) != null) {
            return responseHook.handleResponse(resp);
        } else {
            /**
             * default response reader
             */
            return EsResponseReader.readResp(method, resp);
        }
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

    private RequestHook checkRequestHook (Object param, Method method) {
        if (HookQuery.class.isAssignableFrom(param.getClass())) {
            HookQuery hookQuery = (HookQuery) param;
            RequestHook reqHook = null;
            if (Objects.nonNull(hookQuery.getRequestHook())) {
                reqHook = hookQuery.getRequestHook();
            }
            return reqHook;
        }
        if (method.isAnnotationPresent(UseRequestHook.class)) {
            UseRequestHook useReqHookAnn = method.getAnnotation(UseRequestHook.class);
            String reqHookKey = useReqHookAnn.value();
            return EsHookReedits.REP_FUNC_REGEDIT.get(reqHookKey);
        }
        return null;
    }

    private ResponseHook checkResponseHook(Object param, Method method) {
        if (HookQuery.class.isAssignableFrom(param.getClass())) {
            HookQuery hookQuery = (HookQuery) param;
            ResponseHook reqHook = null;
            if (Objects.nonNull(hookQuery.getResponseHook())) {
                reqHook = hookQuery.getResponseHook();
            }
            return reqHook;
        }
        if (method.isAnnotationPresent(UseResponseHook.class)) {
            UseResponseHook useResponseHookAnn = method.getAnnotation(UseResponseHook.class);
            String responseHookKey = useResponseHookAnn.value();
            return EsHookReedits.RESP_FUNC_REGEDIT.get(responseHookKey);
        }
        return null;
    }

    /**
     * 构建查询代理对象
     */
    public static Object build(Class<?> targetInterfaceClazz, boolean visitParent, RestHighLevelClient client, boolean enableLogOut) {
        return Proxy.newProxyInstance(
                targetInterfaceClazz.getClassLoader(),
                new Class[]{targetInterfaceClazz},
                new EsQueryProxy(targetInterfaceClazz, visitParent, client, enableLogOut)
        );
    }

    /**
     * 构建查询代理对象, 默认 visitParent: true, enableLogOut: true
     */
    public static Object build(Class<?> targetInterfaceClazz, RestHighLevelClient client) {
        return build(targetInterfaceClazz, true, client, true);
    }
}
