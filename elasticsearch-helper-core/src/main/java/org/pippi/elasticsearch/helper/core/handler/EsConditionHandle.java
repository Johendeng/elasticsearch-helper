package org.pippi.elasticsearch.helper.core.handler;

/**
 * @author   JohenTeng
 * @date     2021/12/3
 **/
public interface EsConditionHandle<T> {

    /**
     * if The val is useful
     * @param val
     * return
     */
    boolean test(T val);

}
