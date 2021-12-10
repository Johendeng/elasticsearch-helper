package org.pippi.elasticsearch.helper.spring.repository.entity.params;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsCondition;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.HighLight;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Match;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MultiMatch;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Term;
import org.pippi.elasticsearch.helper.core.beans.enums.EsConnector;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.spring.repository.entity.condition.AgeUseCondition;

/**
 * SimpleAccountQueryParam
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@EsQueryIndex(index = "account", model = QueryModel.BOOL, traceScore = true, minScore = 0.01f)
@HighLight(fields = "address")
public class SimpleAccountQueryParam {

	@Term(value = @Base(connect = EsConnector.FILTER))
	private String state;

	@Range(value = @Base(connect = EsConnector.FILTER), tag = Range.LE_GE)
	@EsCondition(AgeUseCondition.class)
	private RangeParam age;

	@Match(value = @Base(connect = EsConnector.SHOULD))
	private String address;

	@MultiMatch(
			value = @Base(connect = EsConnector.SHOULD),
			fields = {"firstname","employer","lastname"},
			type = MultiMatchQueryBuilder.Type.MOST_FIELDS,
			minimumShouldMatch = "1"
	)
	private String fuzzyField;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public RangeParam getAge() {
		return age;
	}

	public void setAge(RangeParam age) {
		this.age = age;
	}

	public String getFuzzyField() {
		return fuzzyField;
	}

	public void setFuzzyField(String fuzzyField) {
		this.fuzzyField = fuzzyField;
	}
}
