package org.pippi.elasticsearch.helper.model.resp;

import java.io.Serializable;
import java.util.List;

/**
 * User define Response's JavaBean
 * @author     JohenTeng
 * @date      2021/7/21
 */
public class BaseResp<T> implements Serializable {

    private Long totalHit;

    private Float maxScore;

    private List<T> records;

    public BaseResp() {
    }

    public BaseResp(Long totalHit, Float maxScore, List<T> records) {
        this.totalHit = totalHit;
        this.maxScore = maxScore;
        this.records = records;
    }

    public Long getTotalHit() {
        return totalHit;
    }

    public void setTotalHit(Long totalHit) {
        this.totalHit = totalHit;
    }

    public Float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Float maxScore) {
        this.maxScore = maxScore;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
