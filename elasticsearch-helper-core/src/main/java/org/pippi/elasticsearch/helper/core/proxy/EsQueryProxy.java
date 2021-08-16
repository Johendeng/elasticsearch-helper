package org.pippi.elasticsearch.helper.core.proxy;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.pippi.elasticsearch.helper.beans.annotation.hook.RequestHook;
import org.pippi.elasticsearch.helper.beans.annotation.hook.ResponseHook;
import org.pippi.elasticsearch.helper.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.EsSearchHelper;
import org.pippi.elasticsearch.helper.core.HighLevelRestClientHolder;
import org.pippi.elasticsearch.helper.core.engine.EsQueryEngine;
import org.pippi.elasticsearch.helper.core.hook.EsHookRegedit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/7/21
 */

public class EsQueryProxy<T> implements InvocationHandler {

    private Class<T> targetInterface;

    private boolean visitQueryBeanParent = true;

    public EsQueryProxy() {
    }

    public EsQueryProxy(Class<T> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public EsQueryProxy(Class<T> targetInterface, boolean visitQueryBeanParent) {
        this.targetInterface = targetInterface;
        this.visitQueryBeanParent = visitQueryBeanParent;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args == null || args.length == 0 || args.length > 1 ) {
            throw new EsHelperQueryException("ES-HELPER un-support multi-params or miss-param, params must be single");
        }
        if (args != null && args.length == 1) {
            Object param = args[0];
            EsSearchHelper esHelper = EsQueryEngine.execute(param, visitQueryBeanParent);
            if (method.isAnnotationPresent(RequestHook.class)) {
                RequestHook reqHookAnn = method.getAnnotation(RequestHook.class);
                esHelper = EsHookRegedit.useReqHook(reqHookAnn.value(), esHelper, param);
            }
            SearchResponse resp = HighLevelRestClientHolder.client().search(esHelper.getRequest(), RequestOptions.DEFAULT);
            if (method.isAnnotationPresent(ResponseHook.class)) {
                ResponseHook respHookAnn = method.getAnnotation(ResponseHook.class);
                return EsHookRegedit.useRespHook(respHookAnn.value(), resp);
            } else {
                Class<?> returnType = method.getReturnType();


            }
        }
        return null;
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
}
