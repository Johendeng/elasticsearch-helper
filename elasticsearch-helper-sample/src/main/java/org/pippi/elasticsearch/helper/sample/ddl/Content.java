package org.pippi.elasticsearch.helper.sample.ddl;

import org.pippi.elasticsearch.helper.core.beans.annotation.meta.EsField;
import org.pippi.elasticsearch.helper.core.beans.annotation.meta.EsIndex;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;

import java.util.List;

/**
 * 描述
 *
 * @author JohenTeng
 * @date 2021/8/4
 */
@EsIndex(name = "content")
public class Content {

    @EsField(name = "bodyPart", type = EsMeta.KEYWORD)
    private List<String> bodyPart;

    @EsField(type = EsMeta.INTEGER)
    private int intensity;

    @EsField(type = EsMeta.TEXT, analyzer = "ik_smart")
    private String describe;

    @EsField(type = EsMeta.TEXT, analyzer = "ik_smart")
    private String title;

    public Content() {
    }

    public Content(String describe, String title) {
        this.describe = describe;
        this.title = title;
    }

    public Content(List<String> bodyPart, int intensity, String describe, String title) {
        this.bodyPart = bodyPart;
        this.intensity = intensity;
        this.describe = describe;
        this.title = title;
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
