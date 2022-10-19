package org.pippi.elasticsearch.test.repository.entity.params;

import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.SearchAfter;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Term;

/**
 * @author: JohenTeng
 * @date: 2022/5/8
 **/
@EsQueryIndex(index = "account", traceScore = true, size = 100)
public class SearchAfterQueryParam {

    @Term
    private String gender;

    @SearchAfter(value = @Base(name = "account_number"),order = SortOrder.DESC, size = 5)
    private String id;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
