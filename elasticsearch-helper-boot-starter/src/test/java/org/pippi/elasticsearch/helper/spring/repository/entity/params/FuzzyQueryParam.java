package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Fuzzy;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;

/**
 * FuzzyQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
public class FuzzyQueryParam {

    @Fuzzy(maxExpansions = 10, transpositions = true, fuzziness = FuzzinessEnum.TWO)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
