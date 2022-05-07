package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.PageParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.PageAndOrder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range;



/**
 * PageAndOrderQueryRankParam
 *
 * @author JohenTeng
 * @date 2022/5/7
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
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
