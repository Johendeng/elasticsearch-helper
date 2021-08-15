package org.pippi.elasticsearch.helper.sample.ddl;

import com.google.common.collect.Lists;
import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.pippi.elasticsearch.helper.core.helper.EsDataModifyHelper;
import org.pippi.elasticsearch.helper.core.utils.IndexMappingUtils;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.sample.ddl
 * date:    2021/7/22
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class MappingGenTest {

    private static RestHighLevelClient client;

    private static final String _LOCAL_DEV = "localhost";


    static {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(_LOCAL_DEV, 9200)));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCount(){
        System.out.println((int)(Math.random()*10000.0));
    }

    @Test
    public void etlData() throws SQLException {
        String url = "";
        String userName = "";
        String   password = "";

        String sql = "select 1";

        Connection connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement prep = connection.prepareStatement(sql);
        ResultSet resultSet = prep.executeQuery();
        List<String> arr = Lists.newArrayList(
                "全身","肩部","背部","手臂","胸部","腹部","臀部","腿部","颈部","腰部");


        EsDataModifyHelper helper = new EsDataModifyHelper(client);
        int count = 1;
        while (resultSet.next()) {
            Content content = new Content();
            String description = resultSet.getString(1);
            String title = resultSet.getString(2);
            content.setDescribe(description);
            content.setTitle(title);
            content.setIntensity( (int)(Math.random()*10000.0)/100 );
            content.setBodyPart(Lists.newArrayList( arr.get( (int)(Math.random()*10000.0)%arr.size() ), arr.get( (int)(Math.random()*10000.0)%arr.size() ) ));
            helper.update("content",count + "", content);
            count ++;
        }

        connection.close();
    }

    @Test
    public void genMappingTest() {
        IndexMappingUtils mappingUtils = new IndexMappingUtils();
        mappingUtils.export("content.json", null, Content.class);
    }

    @Test
    public void appendData() {
        Content content1 = new Content(Lists.newArrayList("脖颈", "腰腹", "小腿"), 11, "这是一堂练脖颈和腰腹的课程，由张三教练教授，非常的轻松，快乐", "轻松的hit课程");
        Content content2 = new Content(Lists.newArrayList("手臂", "腰腹"), 12, "一堂练手臂和腰腹的课程，由李四教练教授，非常的燃脂，运动量大", "燃脂的hit训练");
        Content content3 = new Content(Lists.newArrayList("臀部", "大腿"), 13, "一堂练臀部和大腿的课程，由王武教练教授，非常的燃脂，快乐", "瑜伽课");
        Content content4 = new Content(Lists.newArrayList("手臂", "腰腹"), 14, "一堂练手臂和腰腹的课程，由赵六教练教授，非常的轻松，快乐", "徒手训练");
        Content content5 = new Content(Lists.newArrayList("大腿", "腰腹"), 15, "一堂练腰和头的课程，由李四教练教授，运动量大", "快乐跑动");
        Content content6 = new Content(Lists.newArrayList("小腿", "大腿"), 16, "一堂练腰和头的课程，由王武教练教授，让我们快乐的跳跃吧", "高效燃脂课");
        Content content7 = new Content(Lists.newArrayList("手掌", "胸部"), 17, "一堂练腰和头的课程，由张三教练教授，瑜伽，古法太极", "瑜伽，古法太极");
        Content content8 = new Content(Lists.newArrayList("胸部", "手臂"), 18, "一堂练腰和头的课程，由赵六教练教授，舒缓心情", "冥想瑜伽");
        Content content9 = new Content(Lists.newArrayList("小腿", "大腿"), 19, "一堂练腰和头的课程，由张三教练教授，高效的燃烧脂肪", "轻松的hit课程");
        Content content10 = new Content(Lists.newArrayList("脖颈", "手臂"), 20, "这是一堂练腰和头的课程，由田七教练教授，hit", "轻松的hit课程");
        EsDataModifyHelper helper = new EsDataModifyHelper(client);
        helper.insert("content_s","1", content1);
        helper.insert("content_s","2", content2);
        helper.insert("content_s","3", content3);
        helper.insert("content_s","4", content4);
        helper.insert("content_s","5", content5);
        helper.insert("content_s","6", content6);
        helper.insert("content_s","7", content7);
        helper.insert("content_s","8", content8);
        helper.insert("content_s","9", content9);
        helper.insert("content_s","10", content10);
    }

    @Test
    public void testUpdate() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("content_s").id("11").doc(XContentFactory.jsonBuilder().startObject()
                .array("bodyPartArr", "大腿", "胸部")
                .endObject());
        client.update(updateRequest, RequestOptions.DEFAULT);

    }


}
