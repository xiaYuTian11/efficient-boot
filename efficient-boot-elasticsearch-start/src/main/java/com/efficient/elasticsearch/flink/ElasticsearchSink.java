package com.efficient.elasticsearch.flink;

import cn.hutool.core.bean.BeanUtil;
import com.efficient.common.util.JackSonUtil;
import com.efficient.common.util.ThreadUtil;
import com.efficient.elasticsearch.properties.ElasticSearchProperties;
import com.efficient.elasticsearch.properties.FlinkProperties;
import com.efficient.elasticsearch.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.core.TimeValue;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author TMW
 * @since 2024/5/20 16:30
 */
@Slf4j
public class ElasticsearchSink extends RichSinkFunction<String> {
    private final ElasticSearchProperties elasticSearchProperties;
    private final String indexName;
    private final FlinkProperties flinkProperties;
    private ElasticSearchService elasticSearchService;
    private RestHighLevelClient client;
    private transient BulkProcessor bulkProcessor;

    public ElasticsearchSink(ElasticSearchProperties elasticSearchProperties, String indexName) {
        this.elasticSearchProperties = BeanUtil.copyProperties(elasticSearchProperties, ElasticSearchProperties.class);
        this.indexName = indexName;
        this.flinkProperties = new FlinkProperties();
    }

    public ElasticsearchSink(ElasticSearchProperties elasticSearchProperties, FlinkProperties flinkProperties, String indexName) {
        this.elasticSearchProperties = BeanUtil.copyProperties(elasticSearchProperties, ElasticSearchProperties.class);
        this.indexName = indexName;
        this.flinkProperties = flinkProperties;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        elasticSearchService = new ElasticSearchService();
        elasticSearchService.init(elasticSearchProperties);
        // 构建BulkProcessor
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                log.info("Executing bulk [{}] with {} requests", executionId, request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                if (response.hasFailures()) {
                    log.error("Bulk [{}] executed with failures", executionId);
                } else {
                    log.info("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                log.error("Failed to execute bulk", failure);
            }
        };

        // 构建并初始化BulkProcessor
        BulkProcessor.Builder bulkProcessorBuilder = BulkProcessor.builder((request, listener1) -> client.bulkAsync(request, RequestOptions.DEFAULT, listener1), listener);

        // 配置BulkProcessor的行为
        bulkProcessorBuilder.setBulkActions(flinkProperties.getBulkActions()) // 批处理操作数
                .setBulkSize(new ByteSizeValue(flinkProperties.getBulkSize(), ByteSizeUnit.MB)) // 批处理大小
                .setFlushInterval(TimeValue.timeValueSeconds(flinkProperties.getFlushInterval())) // 刷新间隔
                .setConcurrentRequests(flinkProperties.getConcurrentRequests()) // 并发请求数
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(flinkProperties.getTryWaitTime()), flinkProperties.getTryCount())); // 重试策略
        bulkProcessor = bulkProcessorBuilder.build();
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (bulkProcessor != null) {
            bulkProcessor.flush();
            bulkProcessor.awaitClose(1L, TimeUnit.MINUTES);
        }
        if (elasticSearchService != null) {
            elasticSearchService.destroy();
        }
    }

    @Override
    public void invoke(String data, Context context) throws Exception {
        Map<String, Object> document = JackSonUtil.toMap(data);
        if (Objects.isNull(document)) {
            return;
        }
        ThreadUtil.EXECUTOR_SERVICE.execute(() -> elasticSearchService.saveByPkField(indexName, document));

        // // bulkProcessor.add(new IndexRequest(indexName).source(document));
        // UpdateRequest updateRequest = new UpdateRequest(indexName, String.valueOf(document.get(elasticSearchProperties.getPkFieldName())))
        //         .doc(document) // 数据
        //         .upsert(document); // 如果文档不存在，则插入这些数据
        // bulkProcessor.add(updateRequest);
    }
}
