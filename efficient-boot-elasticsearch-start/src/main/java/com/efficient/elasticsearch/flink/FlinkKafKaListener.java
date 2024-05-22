package com.efficient.elasticsearch.flink;

import cn.hutool.core.util.StrUtil;
import com.efficient.elasticsearch.properties.KafkaProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.Properties;

/**
 * flink kafka 监听器，初始化加载
 *
 * @author TMW
 * @since 2024/5/20 16:11
 */
@Slf4j
public class FlinkKafKaListener {

    public static void run(KafkaProducerProperties kafkaProducerProperties,
                           SinkFunction<String> sinkFunction,
                           String jobName, String... topics) {
        log.info("flink  kafKa listener  {}", jobName);
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty("bootstrap.servers", kafkaProducerProperties.getBootstrapServers());
        kafkaProps.setProperty("group.id", kafkaProducerProperties.getGroupId());
        if (StrUtil.isNotBlank(kafkaProducerProperties.getKeySerializer())) {
            kafkaProps.put("key.serializer", kafkaProducerProperties.getKeySerializer());
        }
        if (StrUtil.isNotBlank(kafkaProducerProperties.getValueSerializer())) {
            kafkaProps.put("value.serializer", kafkaProducerProperties.getValueSerializer());
        }
        kafkaProps.setProperty("max.poll.records", String.valueOf(kafkaProducerProperties.getMaxPollRecords())); // 增加每次拉取的消息数量
        kafkaProps.setProperty("fetch.min.bytes", String.valueOf(kafkaProducerProperties.getFetchMinBytes())); // 根据实际情况调整
        kafkaProps.setProperty("fetch.max.wait.ms", String.valueOf(kafkaProducerProperties.getFetchMaxWaitMs())); // 根据实际情况调整

        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(kafkaProducerProperties.getParallelism());
            env.enableCheckpointing(kafkaProducerProperties.getCheckpointing());
            env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
            env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500); // 检查点之间的最小时间间隔
            env.getCheckpointConfig().setCheckpointTimeout(60000); // 检查点超时时间
            env.getCheckpointConfig().setMaxConcurrentCheckpoints(1); // 最大并发检查点数

            // 构建KafkaSource
            KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                    .setProperties(kafkaProps)
                    .setTopics(topics)
                    .setGroupId(kafkaProducerProperties.getGroupId())
                    // 自动配置，不要手动配置
                    // .setStartingOffsets(OffsetsInitializer.earliest()) // 从最新处开始读取
                    .setValueOnlyDeserializer(new SimpleStringSchema()) // 使用SimpleStringSchema进行反序列化
                    .build();

            // 使用fromSource添加数据源
            DataStream<String> dateStream = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), kafkaProducerProperties.getSourceName());
            boolean printLog = kafkaProducerProperties.isPrintLog();
            dateStream
                    .map(value -> {
                        if (printLog) {
                            log.info("accept message: {}", value);
                        }
                        return value;
                    })
                    // 如果需要，可以在这里添加其他处理逻辑
                    .addSink(sinkFunction).name(kafkaProducerProperties.getSinkName());
            env.execute(jobName);
        } catch (Exception e) {
            log.error("{} fail {}", jobName, e.getMessage(), e);
        }
    }
}
