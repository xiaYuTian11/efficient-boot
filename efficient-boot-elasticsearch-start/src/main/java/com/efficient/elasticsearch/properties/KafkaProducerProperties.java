package com.efficient.elasticsearch.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TMW
 * @since 2024/5/20 16:22
 */
@Data
public class KafkaProducerProperties implements Serializable {
    private static final long serialVersionUID = 4529138581019905678L;
    /**
     * kafka 服务地址  127.0.0.1:9092
     */
    private String bootstrapServers = "127.0.0.1:9092";
    /**
     * 组ID
     */
    private String groupId = "efficient-group";
    /**
     * 序列化
     */
    private String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * 序列化
     */
    private String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * 每次拉取的消息数量
     */
    private Integer maxPollRecords = 500;
    /**
     * 此配置指示消费者从服务器获取记录时，服务器至少需要返回这么多字节的数据。如果不足这个大小，服务器会等待更多的数据累积到一起再返回，除非达到了fetch.max.wait.ms的限制。例如，"fetch.min.bytes", "10240"意味着每次fetch至少需要从Kafka broker接收10KB的数据。
     */
    private Integer fetchMinBytes = 10240;
    /**
     * 这个配置决定了消费者在没有达到fetch.min.bytes之前，愿意等待服务器累积更多数据的最大时间。如果在这个时间内累积的数据量达到了fetch.min.bytes，或者超过了max.poll.interval.ms（消费者两次poll之间的最大间隔时间），则会结束等待并返回数据。例如，"fetch.max.wait.ms", "500"意味着消费者在没有达到最小字节数要求的情况下，最多等待500毫秒。
     */
    private Integer fetchMaxWaitMs = 500;
    /**
     * checkpoint间隔时间，5秒
     */
    private Integer Checkpointing = 5000;
    /**
     * flink 并发数
     */
    private Integer parallelism = 3;
    /**
     * 是否打印日志
     */
    private boolean printLog = false;
    /**
     * 名称
     */
    private String sourceName = "Efficient Kafka Source";
    /**
     * sink名称
     */
    private String sinkName = "Efficient Sink";
}
