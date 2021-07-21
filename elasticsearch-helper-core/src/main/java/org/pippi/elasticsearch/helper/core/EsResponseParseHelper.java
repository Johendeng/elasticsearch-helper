package org.pippi.elasticsearch.helper.core;

import org.pippi.elasticsearch.helper.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public static List<Map<String, Object>> getList(SearchResponse resp){
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        List<Map<String, Object>> res = new ArrayList<>(hitArr.length);
        for (SearchHit hit : hitArr) {
            res.add(hit.getSourceAsMap());
        }
        return res;
    }

    public static String getListStringify (SearchResponse resp) {
        List<Map<String, Object>> resMap = getList(resp);
        return SerializerUtils.parseObjToJson(resMap);
    }

    public static <T> List<T> getList(SearchResponse resp, Class<T> type) {
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

    public static <T>T getOne(SearchResponse resp, Class<T> type) {
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        if (hitArr.length > 1) {
            throw new EsHelperQueryException("except one result, but find more");
        }
        if (hitArr.length == 1) {
            String jsonResStr = hitArr[0].getSourceAsString();
            return SerializerUtils.jsonToBean(jsonResStr, type);
        }
        return null;
    }




}
