package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
public interface Bool<Children> extends Serializable {

    default Children must() {
        return must(true);
    }

    /**
     * 影响召回 & 影响评分
     */
    Children must(boolean condition);

    default Children should() {
        return should(true);
    }

    /**
     * 不影响召回 & 影响评分
     */
    Children should(boolean condition);

    default Children filter() {
        return filter(true);
    }

    /**
     * 影响召回 & 不影响评分
     */
    Children filter(boolean condition);

    default Children mustNot() {
        return mustNot(true);
    }

    /**
     * 影响召回 & 影响评分
     */
    Children mustNot(boolean condition);
}
