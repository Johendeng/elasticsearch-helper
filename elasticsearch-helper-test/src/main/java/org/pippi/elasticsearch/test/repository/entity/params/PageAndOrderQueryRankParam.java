package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.param.EsPage;
import org.pippi.elasticsearch.helper.model.param.RangeParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.PageAndOrder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range;



/**
 * PageAndOrderQueryRankParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsAnnQueryIndex(index = "account", traceScore = true, size = 30)
public class PageAndOrderQueryRankParam {

    @Range(tag = Range.LE_GE, includeLower = true)
    private RangeParam age;

    @PageAndOrder
    private EsPage page;

    public RangeParam getAge() {
        return age;
    }

    public void setAge(RangeParam age) {
        this.age = age;
    }

    public EsPage getPage() {
        return page;
    }

    public void setPage(EsPage page) {
        this.page = page;
    }
}
