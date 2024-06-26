package org.pippi.elasticsearch.helper.model.param;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;

import javax.annotation.Nullable;

/**
 * MoreLikeThisParam
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
public class MoreLikeThisParam implements EsComplexParam {

    @Nullable
    private MoreLikeThisQueryBuilder.Item[] items;

    @Nullable
    private String[] texts;

    public MoreLikeThisParam() {
    }

    public MoreLikeThisParam(String[] texts) {
        this.texts = texts;
        items = null;
    }

    public MoreLikeThisParam(MoreLikeThisQueryBuilder.Item[] itemsSupplier, String[] texts) {
        this.items = itemsSupplier;
        this.texts = texts;
    }

    public MoreLikeThisQueryBuilder.Item[] getItems() {
        return items;
    }

    public void setItems(MoreLikeThisQueryBuilder.Item[] items) {
        this.items = items;
    }

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }
}
