package com.poet.elasticsearch.helper;

import com.poet.elasticsearch.helper.utils.SerializerUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Project Name:elasticsearch-helper
 * File Name:SerializerTest
 * Package Name:com.poet.elasticsearch.helper
 * Date:2021/4/30 09:58
 * Author:dengtianjia
 * Description:
 */
public class SerializerTest {


    @Test
    public void testObjTransToJson () {

        Map map = new HashMap();
        map.put("dfs", 123);
        map.put("ddd", null);

        String s = SerializerUtils.parseObjToJsonSkipNull(map);

        System.out.println(s);

    }



}
