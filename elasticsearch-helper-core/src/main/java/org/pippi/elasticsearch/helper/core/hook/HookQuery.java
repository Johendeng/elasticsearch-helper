package org.pippi.elasticsearch.helper.core.hook;

/**
 * HookQuery
 *
 * @author JohenTeng
 * @date 2021/9/29
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
    public abstract void setRequestHook();

    /**
     * define ResponseHook
     */
    public abstract void setResponseHook();

    public RequestHook<PARAM> getRequestHook() {
        return requestHook;
    }

    public ResponseHook<RESULT> getResponseHook() {
        return responseHook;
    }
}
