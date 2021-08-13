package org.pippi.elasticsearch.helper.core.hook;

import org.pippi.elasticsearch.helper.core.EsSearchHelper;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/7/21
 */
@FunctionalInterface
public interface RequestHook {

    <R>EsSearchHelper apply(EsSearchHelper helper, R param);

}
