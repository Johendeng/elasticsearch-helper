package org.pippi.elasticsearch.helper.core.hook;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

import org.elasticsearch.action.search.SearchResponse;
import com.google.common.collect.Maps;
import org.pippi.elasticsearch.helper.model.exception.EsHelperConfigException;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 * @author  JohenTeng
 * @date 2021/7/21
 */
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class EsHookReedits {

    public static final Map<String, RequestHook> REP_FUNC_REGEDIT = Maps.newHashMap();

    public static final Map<String, ResponseHook> RESP_FUNC_REGEDIT = Maps.newHashMap();

    public static void addReqHook (String key, RequestHook requestHook) {
        RequestHook checkBean = REP_FUNC_REGEDIT.get(key);
        if (Objects.nonNull(checkBean)) {
            throw new EsHelperConfigException("Duplicated RequestHook-Func Key define!!!");
        }
        REP_FUNC_REGEDIT.put(key, requestHook);
    }

    public static void addRespHook(String key, ResponseHook responseHook) {
        ResponseHook checkBean = RESP_FUNC_REGEDIT.get(key);
        if (Objects.nonNull(checkBean)) {
            throw new EsHelperConfigException("Duplicated ResponseHook-Func Key define!!!");
        }
        RESP_FUNC_REGEDIT.put(key, responseHook);
    }

    public static <P> AbstractEsSession useReqHook(String key, AbstractEsSession helper, P param) {
        return REP_FUNC_REGEDIT.get(key).handleRequest(helper, param);
    }

    public static <R>R useRespHook (String key, SearchResponse resp) {
        return (R)RESP_FUNC_REGEDIT.get(key).handleResponse(resp);
    }

    public static void loadHooksFromTargetInterface(Class<?> targetInterface) {
        Field[] allFields = targetInterface.getDeclaredFields();
        for (Field currentField : allFields){
            Class<?> type = currentField.getType();
                try {
                if (type.equals(RequestHook.class)) {
                    String name = currentField.getName();
                    RequestHook currentReqHook = (RequestHook)currentField.get(targetInterface);
                    EsHookReedits.addReqHook(name, currentReqHook);
                }
                if (type.equals(ResponseHook.class)) {
                    String name = currentField.getName();
                    ResponseHook currentRespHook = (ResponseHook)currentField.get(targetInterface);
                    EsHookReedits.addRespHook(name, currentRespHook);
                }
            } catch (IllegalAccessException e) {
                throw new Error("Hook-Func has a illegal access, please define it as #public static final");
            }
        }
    }


}
