package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsQueryBean;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Match;

/**
 * MatchQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsQueryBean(index = "account", traceScore = true, size = 30)
public class MatchQueryParam {

    @Match
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
