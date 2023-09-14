package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import org.apache.lucene.search.join.ScoreMode;

import java.io.Serializable;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
public interface INested<Param, Children> extends Serializable {


    default Children nested(String path, Param consumer) {
        return nested(true, path, consumer);
    }

    Children nested(boolean condition, String path, Param consumer);

    default Children nested(String path, ScoreMode scoreMode,  Param consumer) {
        return nested(true, path, scoreMode, consumer);
    }

    Children nested(boolean condition, String path, ScoreMode scoreMode,  Param consumer);

    default Children nested(String path, boolean ignoreUnmapped, Param consumer) {
        return nested(true, path, ignoreUnmapped, consumer);
    }

    Children nested(boolean condition, String path, boolean ignoreUnmapped,  Param consumer);

    default Children nested(String path, ScoreMode scoreMode, boolean ignoreUnmapped, Param consumer) {
        return nested(true, path, scoreMode, ignoreUnmapped, consumer);
    }

    Children nested(boolean condition, String path, ScoreMode scoreMode, boolean ignoreUnmapped, Param consumer);
}
