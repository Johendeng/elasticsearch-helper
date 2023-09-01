package org.pippi.elasticsearch.test.repository.entity.params;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsCondition;
import org.pippi.elasticsearch.helper.model.annotations.mapper.EsAnnQueryIndex;
import org.pippi.elasticsearch.helper.model.annotations.mapper.HighLight;
import org.pippi.elasticsearch.helper.model.enums.EsConnector;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;
import org.pippi.elasticsearch.helper.model.param.RangeParam;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Match;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.MultiMatch;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Term;
import org.pippi.elasticsearch.test.repository.entity.condition.AgeUseCondition;


/**
 * SimpleAccountQueryParam
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@EsAnnQueryIndex(index = "account", model = QueryModel.BOOL, traceScore = true, minScore = 0.0001f)
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
