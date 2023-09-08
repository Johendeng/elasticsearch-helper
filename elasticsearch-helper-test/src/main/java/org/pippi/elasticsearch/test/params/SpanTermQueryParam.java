package org.pippi.elasticsearch.test.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SpanTerm;

/**
 * SpanTermQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/9
 */
@EsAnnQueryIndex(index = "account", traceScore = true, size = 30)
public class SpanTermQueryParam {

    @SpanTerm
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
