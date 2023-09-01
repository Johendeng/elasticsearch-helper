package org.pippi.elasticsearch.test.config;

import com.google.common.collect.Maps;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import org.pippi.elasticsearch.helper.spring.EsHelperCustomerConfig;
import org.pippi.elasticsearch.test.constants.HighLightKeys;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 1- 配置自定义的 highLight 对象生成器
 *
 * @author JohenTeng
 * @date 2021/9/30
 */
@Configuration
public class EsConfiguration implements EsHelperCustomerConfig {


	@Override
	public Map<String, Supplier<HighlightBuilder>> declareHighLight() {
		Map<String, Supplier<HighlightBuilder>> highLightMap = Maps.newHashMap();
		highLightMap.put(HighLightKeys.HTML, () -> SearchSourceBuilder.highlight().fragmentSize(10).numOfFragments(5).noMatchSize(5));
		highLightMap.put(HighLightKeys.DOC, () -> SearchSourceBuilder.highlight().fragmentSize(10).numOfFragments(5).noMatchSize(3));
		return highLightMap;
	}

	@Override
	public Map<String, RequestOptions> declareRequestOpt() {
		return null;
	}
}
