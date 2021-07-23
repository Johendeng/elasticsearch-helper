package org.pippi.elasticsearch.helper.sample.ddl;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.pippi.elasticsearch.helper.core.utils.IndexMappingUtils;

import java.util.Date;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.sample.ddl
 * date:    2021/7/22
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class MappingGenTest {

    private static RestHighLevelClient client;


    static {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("local.dev1", 9200)));
    }

    @Test
    public void genMappingTest() {
        IndexMappingUtils mappingUtils = new IndexMappingUtils();
        mappingUtils.export("news_record.json", null, NewsRecord.class);
    }

    @Test
    public void appendData() {
        NewsRecord record1 = new NewsRecord("白菜", "十字花科，芸苔属二年生草本，高可达60厘米", new Date(), 12);
        NewsRecord record2 = new NewsRecord("土豆", "一般指马铃薯。马铃薯（学名：Solanum tuberosum L.），属茄科，一年生草本植物，块茎可供食用，是全球第四大重要的粮食作物", new Date(), 12);
        NewsRecord record3 = new NewsRecord("黄瓜", "葫芦科一年生蔓生或攀援草本植物。茎、枝伸长，有棱沟，被白色的糙硬毛。卷须细。叶柄稍粗糙，有糙硬毛；叶片宽卵状心形，膜质，裂片三角形，有齿", new Date(), 12);
        NewsRecord record4 = new NewsRecord("西红柿", "西红柿一般指番茄。番茄（学名：Lycopersicon esculentum），即西红柿，是管状花目、茄科、番茄属的一种一年生或多年生草本植物", new Date(), 12);
        NewsRecord record5 = new NewsRecord("大葱", "葱种下一变种，区别于分葱（小葱）变种与红葱（楼葱）变种。大葱植株地上部的外形很象洋葱", new Date(), 12);


    }


}
