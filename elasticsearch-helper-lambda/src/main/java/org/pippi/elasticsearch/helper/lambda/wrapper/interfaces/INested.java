package org.pippi.elasticsearch.helper.lambda.wrapper.interfaces;

import org.apache.lucene.search.join.ScoreMode;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author JohenDeng
 * @date 2023/8/31
 **/
public interface INested<Param, Children> extends Serializable {

    default Children nested(String path, Consumer<Param> consumer) {
        return nested(true, path, consumer);
    }

    Children nested(boolean condition, String path, Consumer<Param> consumer);

    default Children nested(String path, ScoreMode scoreMode,  Consumer<Param> consumer) {
        return nested(true, path, scoreMode, consumer);
    }

    Children nested(boolean condition, String path, ScoreMode scoreMode,  Consumer<Param> consumer);

    default Children nested(String path, boolean ignoreUnmapped, Consumer<Param> consumer) {
        return nested(true, path, ignoreUnmapped, consumer);
    }

    Children nested(boolean condition, String path, boolean ignoreUnmapped,  Consumer<Param> consumer);

    default Children nested(String path, ScoreMode scoreMode, boolean ignoreUnmapped, Consumer<Param> consumer) {
        return nested(true, path, scoreMode, ignoreUnmapped, consumer);
    }

    Children nested(boolean condition, String path, ScoreMode scoreMode, boolean ignoreUnmapped, Consumer<Param> consumer);
}
