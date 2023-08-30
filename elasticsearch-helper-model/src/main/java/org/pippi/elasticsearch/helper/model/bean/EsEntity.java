package org.pippi.elasticsearch.helper.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User define Response's JavaBean
 * @author     JohenTeng
 * @date      2021/7/21
 */
public class EsEntity implements Serializable {

    /**
     * es 索引默认存在的 _id 字段，如果未定义，es会为该字段分配 uuid，
     * 该字段作为 更新/删除 的定位字段
     */
    @JsonIgnore
    private String docId;

    @JsonIgnore
    private Float hitScore;

    @JsonIgnore
    private Map<String, List<String>> highLightMap;

    public EsEntity() {
    }

    public EsEntity(Float hitScore, String docId) {
        this.hitScore = hitScore;
        this.docId = docId;
    }

    public Float getHitScore() {
        return hitScore;
    }

    public void setHitScore(Float hitScore) {
        this.hitScore = hitScore;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Map<String, List<String>> getHighLightMap() {
        return highLightMap;
    }

    public void setHighLightMap(Map<String, List<String>> highLightMap) {
        this.highLightMap = highLightMap;
    }
}
