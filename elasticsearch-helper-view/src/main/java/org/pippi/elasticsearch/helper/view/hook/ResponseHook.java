package org.pippi.elasticsearch.helper.view.hook;

import org.elasticsearch.action.search.SearchResponse;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/7/21
 */
@FunctionalInterface
public interface ResponseHook<R> {

    R  apply (SearchResponse resp);


}

