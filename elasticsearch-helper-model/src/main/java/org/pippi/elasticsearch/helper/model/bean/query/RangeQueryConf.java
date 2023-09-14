package org.pippi.elasticsearch.helper.model.bean.query;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.pippi.elasticsearch.helper.model.annotations.mapper.query.Range;
import org.pippi.elasticsearch.helper.model.bean.QueryConf;

/**
 * RangeQueryBean
 *
 * @author     JohenTeng
 * @date      2021/9/26
 */
public class RangeQueryConf extends QueryConf<RangeQueryBuilder> {

    /**
      need config value is:
      {
        @link Range#LE_GE
        @link Range#L_G
        @link Range#L_GE
        @link Range#LE_G
        @link Range#F_T
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
        if (StringUtils.isNotBlank(format)) {
            queryBuilder.format(format);
        }
        if (StringUtils.isNotBlank(relation)) {
            queryBuilder.relation(relation);
        }
        if (StringUtils.isNotBlank(timeZone)) {
            queryBuilder.timeZone(timeZone);
        }
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
