package org.pippi.elasticsearch.helper.core.hook;

/**
 * HookQuery
 *
 * @author JohenTeng
 * @date 2021/9/29
 */
public abstract class HookQuery<PARAM, RESULT> {

    private RequestHook<PARAM> requestHook;

    private ResponseHook<RESULT> responseHook;

    /**
     * define RequestHook
     * @param requestHook
     */
    public abstract void setRequestHook(RequestHook<PARAM> requestHook);

    /**
     * define ResponseHook
     * @param responseHook
     */
    public abstract void setResponseHook(ResponseHook<RESULT> responseHook);


    public RequestHook<PARAM> getRequestHook() {
        return requestHook;
    }

    public ResponseHook<RESULT> getResponseHook() {
        return responseHook;
    }
}
