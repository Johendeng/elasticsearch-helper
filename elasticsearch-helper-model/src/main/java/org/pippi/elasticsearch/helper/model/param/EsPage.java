package org.pippi.elasticsearch.helper.model.param;


import org.elasticsearch.search.sort.SortOrder;
import javax.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Define Page-Query params and order Config
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
public class EsPage<T> implements EsComplexParam {

    private int current = 1;

    private int pageSize = 10;

    @Nullable
    private LinkedHashMap<String, SortOrder> orderMap;

    private List<T> records;

    public int getExclude() {
        return Math.max((current - 1), 0) * pageSize;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Nullable
    public LinkedHashMap<String, SortOrder> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(@Nullable LinkedHashMap<String, SortOrder> orderMap) {
        this.orderMap = orderMap;
    }


    public void setRecords(List<T> records) {
        this.records = records;
    }

    public List<T> getRecords() {
        return records;
    }
}
