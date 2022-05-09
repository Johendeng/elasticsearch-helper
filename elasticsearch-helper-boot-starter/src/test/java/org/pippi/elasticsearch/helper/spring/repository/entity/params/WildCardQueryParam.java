package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.WildCard;

/**
 * WildCardQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/9
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
public class WildCardQueryParam {

    @WildCard(caseInsensitive = true)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
