package org.pippi.elasticsearch.helper.model.annotations.mapper.query;



import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Base;
import org.pippi.elasticsearch.helper.model.annotations.mapper.base.Query;

import java.lang.annotation.*;

/**
 * more like this query,
 * search the most similar _doc;
 *
 * attention:
 * the Field have to be {@link org.pippi.elasticsearch.helper.core.beans.annotation.query.mapping.extend.MoreLikeThisParam}
 *
 *  minimum_should_match：匹配项（term）的百分比，默认是0.3
 *
 *  min_term_freq：一篇文档中一个词语至少出现次数，小于这个值的词将被忽略，默认是2
 *
 *  max_query_terms：一条查询语句中允许最多查询词语的个数，默认是25
 *
 *  stop_words：设置停止词，匹配时会忽略停止词
 *
 *  min_doc_freq：一个词语最少在多少篇文档中出现，小于这个值的词会将被忽略，默认是无限制
 *
 *  max_doc_freq：一个词语最多在多少篇文档中出现，大于这个值的词会将被忽略，默认是无限制
 *
 *  min_word_len：最小的词语长度，默认是0
 *
 *  max_word_len：最多的词语长度，默认无限制
 *
 *  boost_terms：设置词语权重，默认是1
 *
 *  boost：设置查询权重，默认是1
 *
 *  analyzer：设置使用的分词器，默认是使用该字段指定的分词器
 * @author     JohenTeng
 * @date      2021/9/28
 */
@Query
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MoreLikeThis {

    Base value() default @Base;

    /**
     * 纳入查询评分的字段
     *  ps:
     *   如果索引未设置默认查询字段 {@code [index.query.default_field]}，
     *   那么该字段必填，否则会报查询异常
     */
    String[] fields() default {};

    /**
     * 指定分词器
     */
    String analyzer() default "";

    float boostTerms() default  0;

    boolean failOnUnsupportedField() default  true;

    boolean include() default  false;

    int minTermFreq() default  2;

    int maxQueryTerms() default  25;

    int minDocFreq() default  5;

    int maxDocFreq() default  Integer.MAX_VALUE;

    int minWordLength() default  0;

    int maxWordLength() default  0;

    String minimumShouldMatch() default  "30%";

    String[] stopWords() default  {};
}
