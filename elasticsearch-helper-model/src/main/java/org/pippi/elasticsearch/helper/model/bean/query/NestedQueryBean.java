package org.pippi.elasticsearch.helper.model.bean.query;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.pippi.elasticsearch.helper.model.bean.QueryBean;

/**
 * ExtNestedBean
 *
 * @author     JohenTeng
 * @date      2021/9/24
 */
public class NestedQueryBean extends QueryBean<NestedQueryBuilder> {

    private String path;

    private ScoreMode scoreMode;

    /**
     * 不确定field的类型，使用ignoreUnmapped而不是unmappedType
     */
    private boolean ignoreUnmapped;

    @Override
    public void configQueryBuilder(NestedQueryBuilder queryBuilder) {
        queryBuilder.ignoreUnmapped(ignoreUnmapped);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ScoreMode getScoreMode() {
        return scoreMode;
    }

    public void setScoreMode(ScoreMode scoreMode) {
        this.scoreMode = scoreMode;
    }

    public boolean isIgnoreUnmapped() {
        return ignoreUnmapped;
    }

    public void setIgnoreUnmapped(boolean ignoreUnmapped) {
        this.ignoreUnmapped = ignoreUnmapped;
    }
}
