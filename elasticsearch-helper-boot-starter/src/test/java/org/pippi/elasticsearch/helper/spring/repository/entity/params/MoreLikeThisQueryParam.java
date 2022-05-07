package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MoreLikeThis;

/**
 * MoreLikeThisQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
public class MoreLikeThisQueryParam {

    @MoreLikeThis(fields = {"address", "email"}, minTermFreq = 1, minDocFreq = 1)
    private MoreLikeThisParam address;

    public MoreLikeThisParam getAddress() {
        return address;
    }

    public void setAddress(MoreLikeThisParam address) {
        this.address = address;
    }
}
