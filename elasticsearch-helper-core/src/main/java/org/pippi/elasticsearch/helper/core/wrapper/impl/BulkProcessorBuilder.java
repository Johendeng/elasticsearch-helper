package org.pippi.elasticsearch.helper.core.wrapper.impl;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
public class BulkProcessorBuilder {

    private static final Logger log = LoggerFactory.getLogger("BULK-PROCESSOR-EXECUTOR");

    public static BulkProcessor.Builder conf(RestHighLevelClient client, RequestOptions reqOpt) {
        String processName = "BULK-PROCESS-" + System.currentTimeMillis();
        return BulkProcessor.builder(
                (c, l) -> client.bulkAsync(c, reqOpt, buildActionListener()),
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        log.info("[begin] BULK-PROCESS:{}, exc-id:{}, size:{} ", processName, executionId, request.numberOfActions());
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        log.info("[end] BULK-PROCESS:{}, exc-id:{}, size:{}, error-msg:{} ", processName, executionId,
                                response.getItems().length, response.buildFailureMessage());
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        log.info("[fail] BULK-PROCESS:{}, exc-id:{}, msg:", processName, executionId, failure);
                    }
                },
                processName
        );
    }

    public static ActionListener<BulkResponse> buildActionListener() {
        return new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse response) {
                // do nothing
            }

            @Override
            public void onFailure(Exception e) {
                log.error("exc bulk-req error:", e);
            }
        };
    }
}
