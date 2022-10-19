package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MatchPhrasePrefix;

/**
 * MatchPharsePrefixQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
public class MatchPhrasePrefixQueryParam {

    @MatchPhrasePrefix(slop = 10)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
