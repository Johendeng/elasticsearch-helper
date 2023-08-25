package org.pippi.elasticsearch.helper.core.beans.base;

import org.pippi.elasticsearch.helper.model.annotations.mapper.HighLight;

/**
 * load high light description
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
public class HighLightBean {

    /**
     * highLight fields
     * return
     */
    private String[] fields;

    /**
     *  which kind of highLight-key
     * return
     */
    private String highLightKey;

    /**
     * phrase @HighLight to HighLightBean
     */
    public static HighLightBean phrase(HighLight ann) {
        HighLightBean bean = new HighLightBean();
        bean.setFields(ann.fields());
        bean.setHighLightKey(ann.highLightKey());
        return bean;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getHighLightKey() {
        return highLightKey;
    }

    public void setHighLightKey(String highLightKey) {
        this.highLightKey = highLightKey;
    }
}
