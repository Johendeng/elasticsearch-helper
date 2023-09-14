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
    private String tag = Range.L_G;

    private String format;

    private String relation;

    private boolean includeLower = true;

    private boolean includeUpper = true;

    private String timeZone = "+08:00";

    public static RangeQueryConf build() {
        return new RangeQueryConf();
    }

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

    public RangeQueryConf setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public RangeQueryConf setFormat(String format) {
        this.format = format;
        return this;
    }

    public RangeQueryConf setRelation(String relation) {
        this.relation = relation;
        return this;
    }

    public RangeQueryConf setIncludeLower(boolean includeLower) {
        this.includeLower = includeLower;
        return this;
    }

    public RangeQueryConf setIncludeUpper(boolean includeUpper) {
        this.includeUpper = includeUpper;
        return this;
    }

    public RangeQueryConf setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public String getFormat() {
        return format;
    }

    public String getRelation() {
        return relation;
    }

    public boolean isIncludeLower() {
        return includeLower;
    }

    public boolean isIncludeUpper() {
        return includeUpper;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
