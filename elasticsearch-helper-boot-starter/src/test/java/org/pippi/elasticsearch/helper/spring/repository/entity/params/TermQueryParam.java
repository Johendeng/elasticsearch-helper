package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Term;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Terms;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;

/**
 * TermQueryParam
 *
 * @author dengtianjia@fiture.com
 * @date 2022/5/6
 */
@EsQueryIndex(index = "account", traceScore = true, size = 30)
public class TermQueryParam {

    @Term(value = @Base(name = "firstname.keyword", boost = 3.0f, connect = EsConnector.SHOULD))
    private String firstname;

    @Term
    private String gender;

    @Terms(value = @Base(name = "lastname.keyword", boost = 3.0f))
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
