package org.pippi.elasticsearch.test.repository.entity.params;

import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Term;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Terms;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;


/**
 * TermQueryParam
 *
 * @author JohenTeng
 * @date 2022/5/6
 */
@EsAnnQueryIndex(index = "account", traceScore = true, size = 30)
public class TermQueryParam {

    @Term(value = @Base(name = "firstname.keyword", boost = 3.0f, connect = EsConnector.MUST))
    private String firstname;

    @Term(value = @Base(name = "gender.keyword"))
    private String gender;

    @Terms(value = @Base(name = "lastname.keyword", boost = 3.0f, connect = EsConnector.MUST))
    private String[] lastnames;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String[] getLastnames() {
        return lastnames;
    }

    public void setLastnames(String[] lastnames) {
        this.lastnames = lastnames;
    }
}
