package org.pippi.elasticsearch.helper.core.proxy;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.EsQueryEngine;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.UseRequestHook;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.UseResponseHook;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.helper.EsResponseParseHelper;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.hook.EsHookReedits;
import org.pippi.elasticsearch.helper.core.hook.HookQuery;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.hook.ResponseHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
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
        if (enableLogOutEsQueryJson) {
            log.info("{} # {} execute-es-query-json is\n{}", targetInterface.getSimpleName(), method.getName(), esHolder.getSource().toString());
        }
        SearchResponse resp = null;
        try {
            resp = client.search(esHolder.getRequest(), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new EsHelperQueryException("EsSearchExecute I/O exception, cause:", e);
        }
        ResponseHook responseHook = null;
        if ((responseHook = checkResponseHook(param, method)) != null) {
            return responseHook.handleResponse(resp);
        } else {
            return this.returnDefaultResult(method, resp);
        }
    }

    /**
     * phrase SearchResponse and return Result
     * @param method
     * @param resp
     * return
     */
    private Object returnDefaultResult(Method method, SearchResponse resp) {
        Class<?> returnType = method.getReturnType();
        if (!(returnType.isAssignableFrom(BaseResp.class) || returnType.equals(List.class))) {
            throw new EsHelperQueryException("un-support this kind of return-type,please define @ResponseHook or change type to BaseResp/List");
        }
        ParameterizedType paramReturnType = (ParameterizedType)method.getGenericReturnType();
        Type[] paramTypes = paramReturnType.getActualTypeArguments();
        Class<?> paramClazz = (Class<?>) paramTypes[0];
        try {
            if (returnType.isAssignableFrom(BaseResp.class)) {
                return EsResponseParseHelper.readBaseResp(resp, paramClazz);
            }
            return EsResponseParseHelper.readList(resp, paramClazz);
        } catch (Exception e) {
            throw new EsHelperQueryException("default result handler Error, cause:", e);
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

}
