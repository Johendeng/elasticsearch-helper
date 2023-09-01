package org.pippi.elasticsearch.helper.core.proxy;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.pippi.elasticsearch.helper.core.EsQueryEngine;
import org.pippi.elasticsearch.helper.core.hook.EsHookReedits;
import org.pippi.elasticsearch.helper.core.hook.HookQuery;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.core.hook.ResponseHook;
import org.pippi.elasticsearch.helper.core.reader.EsResponseReader;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.lambda.mapper.EsBaseMapper;
import org.pippi.elasticsearch.helper.model.annotations.hook.UseRequestHook;
import org.pippi.elasticsearch.helper.model.annotations.hook.UseResponseHook;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.exception.EsHelperQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author JohenDeng
 * @date 2023/8/30
 **/
@SuppressWarnings("all")
public class AnnotationBeanQueryExecutor extends EsOperationExecutor{

    private static final AnnotationBeanQueryExecutor INSTANCE = new AnnotationBeanQueryExecutor();
    private static final Logger log = LoggerFactory.getLogger("ANNOTATION-BEAN-QUERY-EXECUTOR");

    private AnnotationBeanQueryExecutor() {}

    public static EsOperationExecutor executor() {
        return INSTANCE;
    }

    @Override
    boolean condition(Class<?> targetInterface, Method method, Object[] args) {
        if (method.getDeclaringClass().equals(EsBaseMapper.class)) {
            return false;
        }
        if (args != null && args.length == 1) {
            Object arg = args[0];
            if (method.isAnnotationPresent(EsAnnQueryIndex.class) && arg.getClass().isAnnotationPresent(EsAnnQueryIndex.class)) {
                throw new RuntimeException("two types query-define dont exist as the same time");
            }
            if (arg.getClass().isAnnotationPresent(EsAnnQueryIndex.class)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object operate(RestHighLevelClient client, Class<?> targetInterface, RequestOptions requestOption,
                          Method method, Object[] args, boolean visitParent, boolean statementLogOut) {
        Object param = args[0];
        AbstractEsSession<?> session = EsQueryEngine.execute(param, visitParent);
        session = this.handleRequestHook(session, param, method);
        SearchResponse resp = null;
        try {
            SearchRequest request = session.getRequest();
            if (statementLogOut) {
                log.info("{} # {} execute-es-query-json is\n{}", targetInterface.getSimpleName(), method.getName(), session.getSource().toString());
            }
            resp = client.search(request, requestOption);
        } catch (IOException e) {
            throw new EsHelperQueryException("EsSearchExecute I/O exception, cause:", e);
        }
        return handleRespHookIfPresent(param, method, resp);
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

    private AbstractEsSession<?> handleRequestHook(AbstractEsSession<?> session, Object param, Method method) {
        RequestHook requestHook = null;
        if ((requestHook = checkRequestHook(param, method)) != null) {
            session = requestHook.handleRequest(session, param);
        }
        return session;
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

    private Object handleRespHookIfPresent(Object param, Method method, SearchResponse resp) {
        ResponseHook responseHook = null;
        if ((responseHook = checkResponseHook(param, method)) != null) {
            return responseHook.handleResponse(resp);
        } else {
            return EsResponseReader.readResp(method, resp);
        }
    }
}
