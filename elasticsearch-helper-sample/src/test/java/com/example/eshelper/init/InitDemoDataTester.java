package com.example.eshelper.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.model.utils.SerializerUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import com.example.eshelper.ElasticsearchHelperSampleApplication;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * InintDemoDataTester
 *
 * @author JohenTeng
 * @date 2021/12/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchHelperSampleApplication.class)
public class InitDemoDataTester {

	@Resource
	private RestHighLevelClient restHighLevelClient;

	private static final String indexName = "account";

	@Test
	public void dataInit() throws IOException {
		File file = ResourceUtils.getFile("classpath:sample-data/account-data.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Stream<String> lines = reader.lines();
		Iterator<String> iterator = lines.iterator();
		int index = 1;
		String id = "0";
		BulkRequest bulkRequest = Requests.bulkRequest();
		while (iterator.hasNext()) {
			String ld = iterator.next();
			if (index % 2 == 1) {
				Map<String, Map<String, String>> stringMapMap =
					SerializerUtils.jsonToBeans(ld, new TypeReference<Map<String, Map<String, String>>>() {
					});
				id = stringMapMap.get("index").get("_id");
			}
			if (index % 2 == 0) {
				bulkRequest.add(Requests.indexRequest(indexName).id(id).source(ld, XContentType.JSON));
			}
			index ++;
		}
		restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
	}

}
