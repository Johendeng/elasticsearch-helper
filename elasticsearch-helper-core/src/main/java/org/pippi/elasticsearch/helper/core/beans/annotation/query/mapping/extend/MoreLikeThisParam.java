package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;

import java.util.function.Supplier;

/**
 * MoreLikeThisParam
 *
 * @author     JohenTeng
 * @date      2021/9/29
 */
public class MoreLikeThisParam implements EsComplexParam {

    private MoreLikeThisQueryBuilder.Item[] items;

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
