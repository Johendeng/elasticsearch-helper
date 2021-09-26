package org.pippi.elasticsearch.helper.sample.spring.beans.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.pippi.elasticsearch.helper.core.beans.annotation.meta.EsField;
import org.pippi.elasticsearch.helper.core.beans.annotation.meta.EsIndex;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;
import org.pippi.elasticsearch.helper.core.beans.resp.BaseResp;

import java.util.List;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/8/4
 */
public class Content extends BaseResp.BaseHit {

    private List<String> bodyPart;

    private int intensity;

    private String describe;

    private String title;

    public Content() {
    }

    public List<String> getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(List<String> bodyPart) {
        this.bodyPart = bodyPart;
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
