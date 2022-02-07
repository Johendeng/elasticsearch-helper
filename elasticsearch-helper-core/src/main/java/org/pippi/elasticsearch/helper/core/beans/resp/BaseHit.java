package org.pippi.elasticsearch.helper.core.beans.resp;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User define Response's JavaBean
 * @author     JohenTeng
 * @date      2021/7/21
 */
public class BaseHit implements Serializable {


    private String docId;
    private Float hitScore;
    private Map<String, List<String>> highLightMap;

    public BaseHit() {
    }

    public BaseHit(Float hitScore, String docId) {
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
