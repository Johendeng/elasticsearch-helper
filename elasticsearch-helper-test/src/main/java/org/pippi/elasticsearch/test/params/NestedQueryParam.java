package org.pippi.elasticsearch.test.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Nested;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Term;

/**
 * NestedQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
// 因为没有对应的查询数据集，因此暂时忽略该用例
@EsAnnQueryIndex(index = "account", traceScore = true, size = 30)
public class NestedQueryParam {

    @Term
    private String address;

    @Nested(path = "")
    private SimpleAccountQueryParam nestParam;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SimpleAccountQueryParam getNestParam() {
        return nestParam;
    }

    public void setNestParam(SimpleAccountQueryParam nestParam) {
        this.nestParam = nestParam;
    }
}
