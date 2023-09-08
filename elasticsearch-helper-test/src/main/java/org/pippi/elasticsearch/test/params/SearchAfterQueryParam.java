package org.pippi.elasticsearch.test.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.SearchAfter;

/**
 * @author: JohenTeng
 * @date: 2022/5/8
 **/
@EsAnnQueryIndex(index = "account", traceScore = true, size = 100)
public class SearchAfterQueryParam {

//    @Term(@Base(name = "gender.keyword"))
    private String gender;

    @SearchAfter(value = @Base(name = "account_number"), size = 5)
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
