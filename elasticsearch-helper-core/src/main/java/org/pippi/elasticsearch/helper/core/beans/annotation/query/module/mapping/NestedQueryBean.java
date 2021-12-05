package org.pippi.elasticsearch.helper.core.beans.annotation.query.module.mapping;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.NestedQueryBuilder;

/**
 * ExtNestedBean
 *
 * author     JohenTeng
 * date      2021/9/24
 */
public class NestedQueryBean extends QueryBean<NestedQueryBuilder> {

    private String path;

    private ScoreMode scoreMode;

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
}
