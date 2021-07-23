package org.pippi.elasticsearch.helper.sample.ddl;

import org.pippi.elasticsearch.helper.beans.annotation.meta.EsField;
import org.pippi.elasticsearch.helper.beans.annotation.meta.EsIndex;
import org.pippi.elasticsearch.helper.beans.enums.Meta;

import java.util.Date;

/**
 * project: elasticsearch-helper
 * package: org.pippi.elasticsearch.helper.sample.ddl
 * date:    2021/7/22
 * developer: JohenTeng
 * email: 1078481395@qq.com
 **/
@EsIndex(name = "new_records")
public class NewsRecord {

    @EsField(type = Meta.KEYWORD)
    private String title;

    @EsField(type = Meta.TEXT)
    private String content;

    @EsField(type = Meta.DATE, format = "yyyy-MM-dd HH:mm:ss")
    private Date reportDate;

    @EsField(type = Meta.INTEGER)
    private Integer visitCount;

    public NewsRecord() {
    }

    public NewsRecord(String title, String content, Date reportDate, Integer visitCount) {
        this.title = title;
        this.content = content;
        this.reportDate = reportDate;
        this.visitCount = visitCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }
}
