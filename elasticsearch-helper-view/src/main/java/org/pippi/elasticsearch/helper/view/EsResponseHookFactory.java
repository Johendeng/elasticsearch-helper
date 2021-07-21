package org.pippi.elasticsearch.helper.view;

import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Project Name:elasticsearch-helper
 * File Name:EsResponseHookFactory
 * Package Name:org.pippi.elasticsearch.helper.view
 * Date:2021/7/21 00:36
 * Author:dengtianjia
 * Description:
 */
public class EsResponseHookFactory {


    public static final Map<String, Function<SearchResponse, List<String>>> RESP_READ_FUNC_MAP = Maps.newHashMap();



    public static final Function<SearchResponse, List<String>> standardListStringifyRow = (resp) ->{
        SearchHits hits = resp.getHits();
        SearchHit[] hitArr = hits.getHits();
        List<String> res = new ArrayList<>(hitArr.length);
        for (SearchHit hit : hitArr) {
            res.add(hit.getSourceAsString());
        }
        return res;
    };

//    public static final Function<SearchResponse, List<T>> standardListBean = (resp) -> {
//        SearchHits hits = resp.getHits();
//        SearchHit[] hitArr = hits.getHits();
//        List<T> res = new ArrayList<>(hitArr.length);
//        for (SearchHit hit : hitArr) {
//            String recordJson = hit.getSourceAsString();
//            T record = SerializerUtils.jsonToBean(recordJson, type);
//            res.add(record);
//        }
//        return res;
//    };




}
