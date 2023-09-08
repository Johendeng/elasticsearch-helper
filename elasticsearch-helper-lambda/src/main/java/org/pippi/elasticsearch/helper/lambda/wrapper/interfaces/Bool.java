package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
public interface Bool<Children> extends Serializable {

    /**
     * 影响召回 & 影响评分
     */
    Children must();

    /**
     * 不影响召回 & 影响评分
     */
    Children should();

    /**
     * 影响召回 & 不影响评分
     */
    Children filter();

    /**
     * 影响召回 & 影响评分
     */
    Children mustNot();
}
