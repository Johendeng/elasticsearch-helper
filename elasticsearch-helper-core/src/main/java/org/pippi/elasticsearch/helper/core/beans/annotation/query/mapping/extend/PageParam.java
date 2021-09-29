package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.elasticsearch.search.sort.SortOrder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;

/**
 * Define Page-Query params and order Config
 *
 * @author JohenTeng
 * @date 2021/9/29
 */
public class PageParam implements EsComplexParam {

    private int current = 1;

    private int pageSize = 10;

    @Nullable
    private LinkedHashMap<String, SortOrder> orderMap;

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
    
}
