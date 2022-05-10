package org.pippi.elasticsearch.helper.core.hook;

import org.elasticsearch.action.search.SearchResponse;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * 当参数对象继承该类，可以定义请求处理方法/响应处理方法
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
public abstract class HookQuery<PARAM, RESULT> {

    protected RequestHook<PARAM> requestHook;

    protected ResponseHook<RESULT> responseHook;

    public HookQuery() {
        this.setRequestHook();
        this.setResponseHook();
    }

    /**
     * define RequestHook
     */
    protected abstract void configRequestHook(AbstractEsRequestHolder holder, PARAM param);

    /**
     * define ResponseHook
     */
    protected abstract RESULT configResponseHook(SearchResponse resp);


    public void setRequestHook() {
        this.requestHook = (holder, param) -> {
            this.configRequestHook(holder, param);
            return holder;
        };
    }

    public void setResponseHook() {
        this.responseHook = (orgResp) -> this.configResponseHook(orgResp);
    }

    public RequestHook<PARAM> getRequestHook() {
        return requestHook;
    }

    public ResponseHook<RESULT> getResponseHook() {
        return responseHook;
    }
}
