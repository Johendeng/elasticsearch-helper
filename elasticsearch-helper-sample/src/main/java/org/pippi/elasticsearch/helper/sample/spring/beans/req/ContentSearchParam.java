package org.pippi.elasticsearch.helper.sample.spring.beans.req;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Match;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;

/**
 * ContentSearchParam
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
@EsQueryIndex(index = "test", model = QueryModel.BOOL)
public class ContentSearchParam {

    @Range(value = @Base(meta = EsMeta.INTEGER), tag = Range.LE_GE)
    private RangeParam intensity;

    @Match(
            value = @Base(meta = EsMeta.TEXT, highLight = true),
            fuzziness = FuzzinessEnum.ONE,
            prefixLength = 1
    )
    private String title;


    public RangeParam getIntensity() {
        return intensity;
    }

    public void setIntensity(RangeParam intensity) {
        this.intensity = intensity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
