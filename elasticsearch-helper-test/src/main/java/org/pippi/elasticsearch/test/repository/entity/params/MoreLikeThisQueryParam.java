package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsQueryBean;
import org.pippi.elasticsearch.helper.model.param.MoreLikeThisParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MoreLikeThis;

/**
 * MoreLikeThisQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsQueryBean(index = "account", traceScore = true, size = 30)
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
