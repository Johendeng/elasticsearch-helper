package org.pippi.elasticsearch.helper.sample.spring.beans.req;

import org.pippi.elasticsearch.helper.core.beans.annotation.query.Base;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.EsQueryIndex;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.HighLight;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.RangeParam;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Match;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.MultiMatch;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.module.Range;
import org.pippi.elasticsearch.helper.core.beans.enums.EsMeta;
import org.pippi.elasticsearch.helper.core.beans.enums.FuzzinessEnum;
import org.pippi.elasticsearch.helper.core.beans.enums.QueryModel;
import org.pippi.elasticsearch.helper.core.holder.AbstractEsRequestHolder;
import org.pippi.elasticsearch.helper.core.hook.HookQuery;
import org.pippi.elasticsearch.helper.core.hook.RequestHook;
import org.pippi.elasticsearch.helper.sample.ddl.Content;

/**
 * ContentSearchParam
 *
 * @author JohenTeng
 * @date 2021/9/18
 */
@EsQueryIndex(index = "test", model = QueryModel.BOOL)
@HighLight(fields = {"title", "describe"})
public class ContentSearchParam extends HookQuery<ContentSearchParam, Content> {

    @Range(value = @Base, tag = Range.LE_GE)
    private RangeParam intensity;

    @MultiMatch(
            value = @Base,
            fields = {"title", "describe"},
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

    @Override
    public void setRequestHook() {
        super.requestHook = (h, p) -> h;
    }

    @Override
    public void setResponseHook() {
        super.responseHook = resp -> null;
    }
}