package org.pippi.elasticsearch.helper.core.proxy;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.beans.annotation.hook.ResponseHook;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.beans.resp.StandAggResp;
import org.pippi.elasticsearch.helper.core.EsQueryEngine;
import org.pippi.elasticsearch.helper.core.helper.EsResponseParseHelper;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.hook.EsHookReedits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/7/21
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
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args == null || args.length == 0 || args.length > 1 ) {
            throw new EsHelperQueryException("ES-HELPER un-support multi-params or miss-param, params must be single");
        }
        if (args != null && args.length == 1) {
            Object param = args[0];
            AbstractEsRequestHolder esHolder = EsQueryEngine.execute(param, visitQueryBeanParent);
            if (method.isAnnotationPresent(RequestHook.class)) {
                RequestHook reqHookAnn = method.getAnnotation(RequestHook.class);
                esHolder = EsHookReedits.useReqHook(reqHookAnn.value(), esHolder, param);
            }
            if (enableLogOutEsQueryJson) {
                log.info("{} # {} execute-es-query-json is \n {} \n", targetInterface.getSimpleName(), method.getName(), esHolder.getSource().toString());
            }
            SearchResponse resp = client.search(esHolder.getRequest(), RequestOptions.DEFAULT);
            if (method.isAnnotationPresent(ResponseHook.class)) {
                ResponseHook respHookAnn = method.getAnnotation(ResponseHook.class);
                return EsHookReedits.useRespHook(respHookAnn.value(), resp);
            } else {
                Class<?> returnType = method.getReturnType();
                if (returnType.equals(BaseResp.class)) {
                    ParameterizedType paramReturnType = (ParameterizedType)method.getGenericReturnType();
                    Type[] paramTypes = paramReturnType.getActualTypeArguments();
                    Class paramClazz = (Class) paramTypes[0];
                    if (BaseResp.BaseHit.class.isAssignableFrom(paramClazz)){
                        BaseResp<? extends BaseResp.BaseHit> baseResp = EsResponseParseHelper.getList(resp, ((Class<? extends BaseResp.BaseHit>) paramClazz));
                        return baseResp;
                    } else {
                        throw new EsHelperQueryException("BaseResponse's ParameterizedType has to be <? extends BaseResp.BaseHit>");
                    }
                }
                if (returnType.equals(StandAggResp.class)) {
                    // TODO: 解析返回结果

                }
                throw new EsHelperQueryException("un-support this kind of return-type,please define @ResponseHook or change type to BaseResp/StandAggResp");
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
