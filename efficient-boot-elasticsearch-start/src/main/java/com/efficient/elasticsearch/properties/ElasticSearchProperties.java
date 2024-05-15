package com.efficient.elasticsearch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/8/26 9:59
 */
@ConfigurationProperties("com.efficient.elasticsearch")
@Data
public class ElasticSearchProperties {

    /**
     * 是否启用，默认 true
     */
    private boolean enable = true;
    /**
     * ip 地址
     */
    private String ip = "127.0.0.1";
    /**
     * 端口
     */
    private Integer port = 9200;
    /**
     * 用户名
     */
    private String username = "elastic";
    /**
     * 密码
     */
    private String password;
    /**
     * 是否将时间格式转成时间戳，提高查询效率
     */
    private boolean dateToTimestamp = true;
    /**
     * 连接超时时间,单位毫秒
     * <p>
     * 这个参数指定建立连接的超时时间，即从发起连接请求到建立TCP连接成功之间的最长等待时间，单位是毫秒。如果在这段时间内无法建立连接，则会抛出连接超时异常。
     */
    private Integer connectTimeout = 5000;
    /**
     * socket 超时时间,单位毫秒
     * <p>
     * 这个参数指定从服务器读取数据的超时时间，即在建立连接之后从服务器读取数据的最长等待时间，单位是毫秒。如果在指定的时间内没有读取到数据，则会抛出Socket超时异常。
     */
    private Integer socketTimeout = 10000;
    /**
     * 获取连接的超时时间,单位毫秒
     * <p>
     * 这个参数指定从连接池获取连接的超时时间，单位是毫秒。如果在指定的时间内无法获取到连接，则会抛出超时异常。通常情况下，如果连接池已满且没有可用连接，则会等待一段时间后超时。
     */
    private Integer connectionRequestTimeout = 0;
    /**
     * 最大连接数
     * <p>
     * 这个参数指定了连接池中最大的连接数。超过这个数量的连接将会被阻塞等待。这个参数可以用来控制HttpClient与目标服务器之间的连接数。
     */
    private Integer maxConnectNum = 100;
    /**
     * 最大路由连接数
     * <p>
     * 这个参数指定了每个目标主机的最大连接数。HttpClient在执行请求时会根据目标主机的路由信息从连接池中选择连接。这个参数可以用来限制每个目标主机的最大连接数，以防止某个主机占用过多的连接资源。
     */
    private Integer maxConnectPerRoute = 100;
    /**
     * 主键字段名称
     */
    private String pkFieldName = "id";
    /**
     * 一次最多查询数据量
     */
    private Long maxBuckets = 10000L;
}
