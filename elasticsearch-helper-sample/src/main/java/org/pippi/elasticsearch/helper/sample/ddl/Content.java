package org.pippi.elasticsearch.helper.sample.ddl;

import org.pippi.elasticsearch.helper.beans.annotation.meta.EsField;
import org.pippi.elasticsearch.helper.beans.annotation.meta.EsIndex;
import org.pippi.elasticsearch.helper.beans.enums.Meta;

import java.util.List;

/**
 * 描述
 *
 * @author dengtianjia@fiture.com
 * @date 2021/8/4
 */
@EsIndex(name = "content")
public class Content {

    @EsField(name = "bodyPart", type = Meta.ARRAY)
    private List<String> bodyPart;

    @EsField(type = Meta.INTEGER)
    private int intensity;

    @EsField(type = Meta.TEXT, analyzer = "ik_smart")
    private String describe;

    @EsField(type = Meta.TEXT, analyzer = "ik_smart")
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
