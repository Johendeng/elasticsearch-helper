package org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.EsComplexParam;

import java.util.function.Supplier;

/**
 * MoreLikeThisParam
 *
 * author     JohenTeng
 * date      2021/9/29
 */
public class MoreLikeThisParam implements EsComplexParam {

    private Supplier<MoreLikeThisQueryBuilder.Item[]> itemsSupplier;

    private String[] texts;

    public MoreLikeThisParam(String[] texts) {
        this.texts = texts;
        itemsSupplier = () -> null;
    }

    public MoreLikeThisParam(Supplier<MoreLikeThisQueryBuilder.Item[]> itemsSupplier, String[] texts) {
        this.itemsSupplier = itemsSupplier;
        this.texts = texts;
    }

    public Supplier<MoreLikeThisQueryBuilder.Item[]> getItemsSupplier() {
        return itemsSupplier;
    }

    public void setItemsSupplier(Supplier<MoreLikeThisQueryBuilder.Item[]> itemsSupplier) {
        this.itemsSupplier = itemsSupplier;
    }

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }
}
