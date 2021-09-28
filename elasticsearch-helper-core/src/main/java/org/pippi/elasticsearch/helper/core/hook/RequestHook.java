package org.pippi.elasticsearch.helper.core.hook;

import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/7/21
 */
@FunctionalInterface
public interface RequestHook<R> {

    /**
     *  user define the operation of request
     * @param t
     * @param r
     * @return
     */
    AbstractEsRequestHolder apply(AbstractEsRequestHolder t, R r);

}
