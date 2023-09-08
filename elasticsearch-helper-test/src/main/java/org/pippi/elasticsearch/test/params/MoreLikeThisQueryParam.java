package org.pippi.elasticsearch.test.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.param.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MoreLikeThis;

/**
 * MoreLikeThisQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsAnnQueryIndex(index = "account", traceScore = true, size = 30)
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
