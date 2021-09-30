package org.pippi.elasticsearch.helper.spring.entity.result;

import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/8/4
 */
public class TestRecord extends BaseResp.BaseHit {

    private int intensity;

    private String describe;

    private String title;

    public TestRecord() {
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
