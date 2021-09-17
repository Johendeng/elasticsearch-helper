package org.pippi.elasticsearch.helper.core.helper;

import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
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


    public <R> R  parse(SearchResponse resp, Class<R> clazz) {

        return null;
    }


    public static String getListStringify (SearchResponse resp) {
        BaseResp back = getListMap(resp);
        return SerializerUtils.parseObjToJson(back);
    }

    /**
     *  simple read es response
     * @param resp
     * @return
     */
    public static BaseResp<Map<String, Object>> getListMap(SearchResponse resp){
        BaseResp res = new BaseResp();
        SearchHits hits = resp.getHits();
        res.setMaxScore(hits.getMaxScore());
        res.setTotalHit(hits.getTotalHits().value);

        SearchHit[] hitArr = hits.getHits();
        List<Map<String, Object>> records = new ArrayList<>(hitArr.length);
        for (SearchHit hit : hitArr) {
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            sourceMap.put("docId", hit.getId());
            sourceMap.put("hitScore", hit.getScore());
            records.add(sourceMap);
        }

        res.setRecords(records);
        return res;
    }

    public static <T extends BaseResp.BaseHit>BaseResp<T> getList(SearchResponse resp, Class<T> type) {
        BaseResp res = new BaseResp();
        SearchHits hits = resp.getHits();
        res.setMaxScore(hits.getMaxScore());
        res.setTotalHit(hits.getTotalHits().value);

        SearchHit[] hitArr = hits.getHits();
        List<T> records = new ArrayList<>(hitArr.length);
        for (SearchHit hit : hitArr) {
            String recordJson = hit.getSourceAsString();
            T record = SerializerUtils.jsonToBean(recordJson, type);
            record.setDocId(hit.getId());
            record.setHitScore(hit.getScore());
            records.add(record);
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
