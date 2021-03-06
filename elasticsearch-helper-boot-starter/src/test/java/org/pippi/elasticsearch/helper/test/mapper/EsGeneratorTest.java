package org.pippi.elasticsearch.helper.test.mapper;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pippi.elasticsearch.helper.core.utils.EsHelperRelBeansGenerator;
import org.pippi.elasticsearch.helper.EsHelperSampleApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * EsGeneratorTest
 *
 * @author JohenTeng
 * @date 2021/12/10
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsHelperSampleApplication.class)
public class EsGeneratorTest {

    @Resource
    private RestHighLevelClient client;

    @Test
    public void testGenClazzFile() throws IOException {
        EsHelperRelBeansGenerator generator = new EsHelperRelBeansGenerator("account", client);
        generator.run();
    }
}
