package org.pippi.elasticsearch.helper.core.hook;

import org.pippi.elasticsearch.helper.core.EsSearchHelper;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/7/21
 */
@FunctionalInterface
public interface RequestHook {

    <R> AbstractEsRequestHolder apply(AbstractEsRequestHolder helper, R param);

}
