package org.pippi.elasticsearch.helper.sample.spring.beans.req;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryField;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryType;

/**
 * ContentSearchParam
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
@EsQueryIndex(index = "test", model = QueryModel.BOOL)
public class ContentSearchParam {

    @EsQueryField(queryType = QueryType.TERM, meta = EsMeta.INTEGER)
    private Integer intensity;

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }
}
