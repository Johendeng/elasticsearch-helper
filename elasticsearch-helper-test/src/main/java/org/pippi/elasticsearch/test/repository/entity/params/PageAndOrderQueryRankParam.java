package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsQueryBean;
import org.pippi.elasticsearch.helper.model.param.PageParam;
import org.pippi.elasticsearch.helper.model.param.RangeParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.PageAndOrder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range;



/**
 * PageAndOrderQueryRankParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsQueryBean(index = "account", traceScore = true, size = 30)
public class PageAndOrderQueryRankParam {

    @Range(tag = Range.LE_GE, includeLower = true)
    private RangeParam age;

    @PageAndOrder
    private PageParam page;

    public RangeParam getAge() {
        return age;
    }

    public void setAge(RangeParam age) {
        this.age = age;
    }

    public PageParam getPage() {
        return page;
    }

    public void setPage(PageParam page) {
        this.page = page;
    }
}
