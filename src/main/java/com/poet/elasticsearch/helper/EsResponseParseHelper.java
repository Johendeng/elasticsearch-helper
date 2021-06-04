package com.poet.elasticsearch.helper;

import com.poet.elasticsearch.helper.utils.SerializerUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;

/**
 * project: elasticsearch-helper
 * package: com.poet.elasticsearch.helper
 * date:    2021/5/8
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
public class EsResponseParseHelper {


    /**
     *  simple read es response
     * @param resp
     * @return
     */
    public static List<String> read(SearchResponse resp){
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        List<String> res = new ArrayList<>(hitArr.length);
        for (SearchHit hit : hitArr) {
            res.add(hit.getSourceAsString());
        }
        return res;
    }

    public static <T> List<T> read(SearchResponse resp, Class<T> type) {
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        List<T> res = new ArrayList<>(hitArr.length);
        for (SearchHit hit : hitArr) {
            String recordJson = hit.getSourceAsString();
            T record = SerializerUtils.jsonToBean(recordJson, type);
            res.add(record);
        }
        return res;
    }





}
