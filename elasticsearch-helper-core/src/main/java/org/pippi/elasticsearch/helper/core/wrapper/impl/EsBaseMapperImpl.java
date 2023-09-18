package org.pippi.elasticsearch.helper.core.wrapper.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.IndicesRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.pippi.elasticsearch.helper.core.EsQueryEngine;
import org.pippi.elasticsearch.helper.core.reader.impl.CollectionRespReader;
import org.pippi.elasticsearch.helper.core.session.AbstractEsSession;
import org.pippi.elasticsearch.helper.core.utils.EsBeanFieldTransUtils;
import org.pippi.elasticsearch.helper.core.utils.SerializerUtils;
import org.pippi.elasticsearch.helper.core.wrapper.EsWrapper;
import org.pippi.elasticsearch.helper.core.wrapper.mapper.EsBaseMapper;
import org.pippi.elasticsearch.helper.model.bean.EsEntity;
import org.pippi.elasticsearch.helper.model.bean.base.EsQueryIndexBean;
import org.pippi.elasticsearch.helper.model.enums.Operation;
import org.pippi.elasticsearch.helper.model.enums.QueryModel;
import org.pippi.elasticsearch.helper.model.exception.EsHelperException;
import org.pippi.elasticsearch.helper.model.param.EsPage;
import org.pippi.elasticsearch.helper.model.utils.CollectionUtils;
import org.pippi.elasticsearch.helper.model.utils.ExceptionUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author JohenDeng
 * @date 2023/9/12
 **/
@SuppressWarnings({"rawtypes", "unchecked"})
public class EsBaseMapperImpl<T extends EsEntity> implements EsBaseMapper<T> {

    private final Logger log;

    private final String index;

    private final Class<T> clazz;

    private final RestHighLevelClient client;

    private final RequestOptions reqOpt;

    private final boolean statementLogOut;

    public EsBaseMapperImpl(String index, Class<T> clazz, RestHighLevelClient client, RequestOptions reqOpt, boolean statementLogOut, Logger log) {
        this.index = index;
        this.clazz = clazz;
        this.client = client;
        this.reqOpt = reqOpt;
        this.statementLogOut = statementLogOut;
        this.log = log;
    }

    @Override
    public int insert(T entity) {
        Map<String, Object> map = EsBeanFieldTransUtils.toMap(entity);
        IndexRequest req = new IndexRequest(index);
        req.id(entity.getDocId());
        req.source(SerializerUtils.parseObjToJson(map), XContentType.JSON);
        try {
            this.reqLogOut(req.toString(), "insert");
            IndexResponse resp = client.index(req, reqOpt);
            return this.readDocWriteResp(resp, Operation.INSERT);
        } catch (IOException e) {
            throw new EsHelperException(e);
        }
    }

    @Override
    public BulkResponse insertBatch(Collection<T> dataList) {
        BulkRequest bulkReq = new BulkRequest(index);
        this.appendBulkReq(dataList, bulkReq::add);
        try {
            return client.bulk(bulkReq, reqOpt);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc insert-batch io-error", e);
        }
    }

    @Override
    public void insertBatchAsyncBulk(Collection<T> dataList, int refreshSize, long waitTime, TimeUnit unit) {
        BulkProcessor.Builder builder = BulkProcessorBuilder.conf(client, reqOpt);
        int finalRefreshSize = refreshSize == 0 ? 100 : refreshSize;
        builder.setBulkActions(finalRefreshSize);
        try (BulkProcessor process = builder.build()){
            this.appendBulkReq(dataList, process::add);
            process.awaitClose(waitTime, unit);
        } catch (InterruptedException e) {
            throw ExceptionUtils.mpe("exc insert-batch-async-bulk interrupt-error", e);
        }
    }

    private void appendBulkReq (Collection<T> dataList, Consumer<DocWriteRequest> appender) {
        dataList.forEach(bean -> {
            Map<String, Object> map = EsBeanFieldTransUtils.toMap(bean);
            IndexRequest currentInsertReq = new IndexRequest(index);
            currentInsertReq.id(bean.getDocId());
            currentInsertReq.source(SerializerUtils.parseObjToJson(map), XContentType.JSON);
            appender.accept(currentInsertReq);
        });
    }

    @Override
    public int deleteById(Serializable id) {
        DeleteRequest delReq = new DeleteRequest(index);
        delReq.id(id.toString());
        try {
            this.reqLogOut(delReq.toString(), "deleteById");
            DeleteResponse resp = client.delete(delReq, reqOpt);
            return this.readDocWriteResp(resp, Operation.DELETE);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc delete io-error", e);
        }
    }

    @Override
    public BulkByScrollResponse delete(EsWrapper<T> queryEsWrapper) {
        AbstractEsSession<?> session = EsQueryEngine.execute(queryEsWrapper);
        try {
            DeleteByQueryRequest delReq = new DeleteByQueryRequest(index);
            delReq.setQuery(session.getQueryBuilder());
            return client.deleteByQuery(delReq, reqOpt);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc delete-by-query io-error", e);
        }
    }

    @Override
    public BulkResponse deleteBatchIds(Collection<? extends Serializable> idList) {
        BulkRequest bulkReq = new BulkRequest(index);
        idList.forEach(id -> {
            DeleteRequest delReq = new DeleteRequest(index);
            delReq.id(id.toString());
            bulkReq.add(delReq);
        });
        try {
            return client.bulk(bulkReq, reqOpt);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc del-batch io-error", e);
        }
    }

    @Override
    public int updateById(T entity) {
        Map<String, Object> updateMap = EsBeanFieldTransUtils.toUpdateMap(entity);
        if (StringUtils.isBlank(entity.getDocId())) {
            log.error("update-by-id failed, cause id is missing");
            return 0;
        }
        if (CollectionUtils.isEmpty(updateMap)) {
            return 0;
        }
        try {
            UpdateRequest upReq = new UpdateRequest();
            upReq.index(index);
            upReq.id(entity.getDocId());
            upReq.doc(SerializerUtils.parseObjToJson(updateMap), XContentType.JSON);
            this.reqLogOut(upReq.toString(), "updateById");
            UpdateResponse resp = client.update(upReq, reqOpt);
            return this.readDocWriteResp(resp, Operation.UPDATE);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc updateById io-error", e);
        }
    }

    @Override
    public BulkByScrollResponse update(T entity, EsWrapper<T> updateEsWrapper) {
        Map<String, Object> updateMap = EsBeanFieldTransUtils.toUpdateMap(entity);
        if (CollectionUtils.isNotEmpty(updateEsWrapper.updateMap())) {
            updateMap.putAll(updateEsWrapper.updateMap());
        }
        if (CollectionUtils.isEmpty(updateMap)) {
            return null;
        }
        AbstractEsSession<?> session = EsQueryEngine.execute(updateEsWrapper);
        UpdateByQueryRequest upReq = new UpdateByQueryRequest(index);
        upReq.setQuery(session.getQueryBuilder());
        upReq.setScript(new Script(buildUpdateScript(updateMap)));
        try {
            return client.updateByQuery(upReq, reqOpt);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc updateByQuery io-error", e);
        }
    }

    @Override
    public T selectById(Serializable id) {
        EsQueryIndexBean indexBean = new EsQueryIndexBean(index, QueryModel.BOOL);
        AbstractEsSession session = AbstractEsSession.builder().config(indexBean).build();
        session.chain(QueryBuilders.termQuery("_id", id.toString()));
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectById");
            SearchResponse resp = client.search(searchReq, reqOpt);
            List<T> resList = CollectionRespReader.reader().read(clazz, resp);
            if (CollectionUtils.isEmpty(resList)) {
                return null;
            }
            if (resList.size() > 1) {
                throw ExceptionUtils.mpe("exc selectById except one result, but find more then one");
            }
            return resList.get(0);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectById io-error", e);
        }
    }

    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        EsQueryIndexBean indexBean = new EsQueryIndexBean(index, QueryModel.BOOL);
        AbstractEsSession session = AbstractEsSession.builder().config(indexBean).build();
        session.chain(QueryBuilders.termsQuery("_id", idList));
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectBatchIds");
            SearchResponse resp = client.search(searchReq, reqOpt);
            List<T> resList = CollectionRespReader.reader().read(clazz, resp);
            if (CollectionUtils.isEmpty(resList)) {
                return Lists.newArrayList();
            }
            return resList;
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectBatchIds io-error", e);
        }
    }

    @Override
    public T selectOne(EsWrapper<T> queryEsWrapper) {
        AbstractEsSession<?> session = EsQueryEngine.execute(queryEsWrapper);
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectOne");
            SearchResponse resp = client.search(searchReq, reqOpt);
            List<T> resList = CollectionRespReader.reader().read(clazz, resp);
            if (CollectionUtils.isEmpty(resList)) {
                return null;
            }
            if (resList.size() > 1) {
                throw ExceptionUtils.mpe("exc selectOne except one result, but find more then one");
            }
            return resList.get(0);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectOne io-error", e);
        }
    }

    @Override
    public Long selectCount(EsWrapper<T> queryEsWrapper) {
        AbstractEsSession<?> session = EsQueryEngine.execute(queryEsWrapper);
        session.getSource().trackTotalHits(true).from(0).size(0);
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectCount");
            SearchResponse resp = client.search(searchReq, reqOpt);
            return resp.getHits().getTotalHits().value;
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectCount io-error", e);
        }
    }

    @Override
    public List<T> selectList(EsWrapper<T> queryEsWrapper) {
        AbstractEsSession<?> session = EsQueryEngine.execute(queryEsWrapper);
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectList");
            SearchResponse resp = client.search(searchReq, reqOpt);
            return CollectionRespReader.reader().read(clazz, resp);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectList io-error", e);
        }
    }

    @Override
    public List<Map<String, Object>> selectMaps(EsWrapper<T> queryEsWrapper) {
        AbstractEsSession<?> session = EsQueryEngine.execute(queryEsWrapper);
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectMaps");
            SearchResponse resp = client.search(searchReq, reqOpt);
            return CollectionRespReader.reader().readMap(resp);
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectList io-error", e);
        }
    }

    @Override
    public <E extends EsPage<T>> E selectPage(E page, EsWrapper<T> queryEsWrapper) {
        AbstractEsSession<?> session = EsQueryEngine.execute(queryEsWrapper);
        SearchSourceBuilder source = session.getSource();
        source.from(page.getExclude()).size(page.getPageSize());
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectPage");
            SearchResponse resp = client.search(searchReq, reqOpt);
            List<T> data = CollectionRespReader.reader().read(clazz, resp);
            page.setRecords(data);
            return page;
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectList io-error", e);
        }
    }

    @Override
    public <E extends EsPage<Map<String, Object>>> E selectMapsPage(E page, EsWrapper<T> queryEsWrapper) {
        AbstractEsSession<?> session = EsQueryEngine.execute(queryEsWrapper);
        SearchSourceBuilder source = session.getSource();
        source.from(page.getExclude()).size(page.getPageSize());
        try {
            SearchRequest searchReq = session.getRequest();
            this.reqLogOut(searchReq.source().toString(), "selectMapsPage");
            SearchResponse resp = client.search(searchReq, reqOpt);
            List<Map<String, Object>> data = CollectionRespReader.reader().readMap(resp);
            page.setRecords(data);
            return page;
        } catch (IOException e) {
            throw ExceptionUtils.mpe("exc selectList io-error", e);
        }
    }

    private int readDocWriteResp(DocWriteResponse resp, Operation operate) {
        switch (operate) {
            case INSERT:
                return StringUtils.equals(resp.getResult().getLowercase(), Operation.INSERT.getExcFlag()) ? 1 : 0;
            case UPDATE:
                return StringUtils.equals(resp.getResult().getLowercase(), Operation.UPDATE.getExcFlag()) ? 1 : 0;
            case DELETE:
                return StringUtils.equals(resp.getResult().getLowercase(), Operation.DELETE.getExcFlag()) ? 1 : 0;
        }
        return 0;
    }

    private String buildUpdateScript(Map<String, Object> updateMap) {
        StringJoiner joiner = new StringJoiner(";");
        updateMap.forEach((key, value) -> joiner.add("ctx._source['" + key + "']='" + SerializerUtils.parseObjToJson(value) + "'"));
        return joiner.toString();
    }

    private void reqLogOut(String req, String method) {
        if (statementLogOut) {
            log.info("{} execute-es-query-json is\n{}", method, req);
        }
    }
}
