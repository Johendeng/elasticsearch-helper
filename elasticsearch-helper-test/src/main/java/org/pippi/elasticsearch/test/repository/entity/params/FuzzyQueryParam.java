package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Fuzzy;
import org.pippi.elasticsearch.helper.model.enums.FuzzinessEnum;

/**
 * FuzzyQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@EsAnnQueryIndex(index = "account", traceScore = true, size = 30)
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
