package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MatchPhrase;

/**
 * MatchPhraseQueryParam
 *
 * @author dengtianjia@fiture.com
 * @date 2022/5/7
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
public class MatchPhraseQueryParam {

    @MatchPhrase
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
