package org.pippi.elasticsearch.helper.core.helper;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.pippi.elasticsearch.helper.core.beans.exception.EsHelperQueryException;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseHit;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author    JohenTeng
 * @date     2021/5/8
 *
 **/
public class EsResponseParseHelper {

    private static final Logger log = LoggerFactory.getLogger(EsResponseParseHelper.class);

    public static String getListStringify (SearchResponse resp) {
        BaseResp back = getListMap(resp);
        return SerializerUtils.parseObjToJson(back);
    }

    /**
     *  simple read es response
     * @param resp
     * return
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
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (MapUtils.isNotEmpty(highlightFields)) {
                Map<String, List<String>> highLightMap = Maps.newHashMap();
                for (Map.Entry<String, HighlightField> en : highlightFields.entrySet()) {
                    String key = en.getKey();
                    List<String> value = Arrays.stream(en.getValue().fragments()).map(Text::toString).collect(Collectors.toList());
                    highLightMap.put(key, value);
                }
                sourceMap.put("highLight", highLightMap);
            }
            records.add(sourceMap);
        }
        res.setRecords(records);
        return res;
    }

    public static <T>List<T> readList(SearchResponse resp, Class<T> type) {
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        List<T> records = new ArrayList<>(hitArr.length);
        int needWarm = 0;
        for (SearchHit hit : hitArr) {
            String recordJson = hit.getSourceAsString();
            T record = SerializerUtils.jsonToBean(recordJson, type);
            records.add(record);
            needWarm += loadBaseHitData(record, hit, type);
        }
        if (needWarm > 0) {
            log.warn("response check has HighLight data, but result-type not be extend from BaseHit.class");
        }
        return records;
    }


    public static <T>BaseResp<T> readBaseResp(SearchResponse resp, Class<T> type) {
        BaseResp res = new BaseResp();
        SearchHits hits = resp.getHits();
        res.setMaxScore(hits.getMaxScore());
        res.setTotalHit(hits.getTotalHits().value);
        SearchHit[] hitArr = hits.getHits();
        List<T> records = new ArrayList<>(hitArr.length);
        int needWarn = 0;
        for (SearchHit hit : hitArr) {
            String recordJson = hit.getSourceAsString();
            T record = SerializerUtils.jsonToBean(recordJson, type);
            records.add(record);
            needWarn += loadBaseHitData(record, hit, type);
        }
        res.setRecords(records);
        if (needWarn > 0) {
            log.warn("response check has HighLight data, but result-type not be extend from BaseHit.class");
        }
        return res;
    }

    private static int loadBaseHitData(Object record, SearchHit hit, Class<?> type) {
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        if (BaseHit.class.isAssignableFrom(type) && (Objects.nonNull(highlightFields) && highlightFields.size() > 0)) {
            ((BaseHit)record).setDocId(hit.getId());
            ((BaseHit)record).setHitScore(hit.getScore());
            if (MapUtils.isNotEmpty(highlightFields)) {
                Map<String, List<String>> highLightMap = highlightFields.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, highlightField ->
                                Arrays.stream(highlightField.getValue().fragments()).map(Text::toString).collect(Collectors.toList())));
                ((BaseHit)record).setHighLightMap(highLightMap);
            }
            return 0;
        } else if (Objects.nonNull(highlightFields) && highlightFields.size() > 0) {
            return 1;
        }
        return 0;
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
