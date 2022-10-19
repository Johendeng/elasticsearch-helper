package org.pippi.elasticsearch.helper.core.hook;

import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;

/**
 *
 * @author     JohenTeng
 * @date      2021/7/21
 */
@FunctionalInterface
public interface RequestHook<PARAM> {

    /**
     *  user define the operation of request
     *      you can extend-define Es-request or
     *      define aggregation
     * @param holder
     * @param param
     * return
     */
    @SuppressWarnings(value = {"rawtypes"})
    AbstractEsSession handleRequest(AbstractEsSession holder, PARAM param);

}
