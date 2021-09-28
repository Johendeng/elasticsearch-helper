package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.elasticsearch.index.query.RangeQueryBuilder;

/**
 * RangeQueryBean
 *
 * @author JohenTeng
 * @date 2021/9/26
 */
public class RangeQueryBean  extends QueryBean<RangeQueryBuilder>{

    /**
      need config value is:
      {
        @link org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range#LE_GE
        @link org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range#L_G
        @link org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range#L_GE
        @link org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range#LE_G
        @link org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range#F_T
      }
     */
    private String tag;

    private String format;
    private String relation;
    private boolean includeLower;
    private boolean includeUpper;
    private String timeZone;

    @Override
    public void configQueryBuilder(RangeQueryBuilder queryBuilder) {
        // TODO: 需要查看默认配置
        queryBuilder.format(format)
                .relation(relation)
                .includeLower(includeLower)
                .includeUpper(includeUpper)
                .timeZone(timeZone);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public boolean isIncludeLower() {
        return includeLower;
    }

    public void setIncludeLower(boolean includeLower) {
        this.includeLower = includeLower;
    }

    public boolean isIncludeUpper() {
        return includeUpper;
    }

    public void setIncludeUpper(boolean includeUpper) {
        this.includeUpper = includeUpper;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
