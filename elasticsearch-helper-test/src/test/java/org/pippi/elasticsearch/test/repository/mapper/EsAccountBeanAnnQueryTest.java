package org.pippi.elasticsearch.test.repository.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.model.utils.JacksonUtils;
import org.pippi.elasticsearch.test.EsHelperSampleApplication;
import org.pippi.elasticsearch.test.params.AggAccountQueryParam;
import org.pippi.elasticsearch.test.params.SimpleAccountQueryParam;
import org.pippi.elasticsearch.test.result.AccountAggResult;
import org.pippi.elasticsearch.test.repository.entity.AccountEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * EsAccountMapperTest
 *
 * @author JohenTeng
 * @date 2021/12/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class EsAccountBeanAnnQueryTest {

	@Resource
	private EsAccountMapper esAccountMapper;

	@Test
	public void testSimpleQuery() {
		SimpleAccountQueryParam param = new SimpleAccountQueryParam();
		param.setState("DE");
		param.setAddress("River Street");
		param.setFuzzyField("Bates");
		List<AccountEntity> resp = esAccountMapper.queryByParam(param);
		System.out.println(JacksonUtils.parseObjToJsonPretty(resp));
	}

	@Test
	public void testAggQuery() {
		AggAccountQueryParam param = new AggAccountQueryParam();
		param.setCity("Nogal");
		AccountAggResult res = esAccountMapper.aggByParam(param);
		System.out.println(JacksonUtils.parseObjToJson(res));
	}

	@Test
	public void testAggQueryAnn() {
		AggAccountQueryParam param = new AggAccountQueryParam();
		param.setCity("Limestone");
		AccountAggResult res = esAccountMapper.aggByParamAnn(param);
		System.out.println(JacksonUtils.parseObjToJson(res));
	}
}
