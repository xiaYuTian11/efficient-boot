package com.efficient.elasticsearch.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.efficient.common.page.Page;
import com.efficient.common.util.JackSonUtil;
import com.efficient.elasticsearch.entity.ResponseEntity;
import com.efficient.elasticsearch.parser.SqlParser;
import com.efficient.elasticsearch.parser.TableNameParser;
import com.efficient.elasticsearch.properties.ElasticSearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author TMW
 * @since 2024/5/15 10:06
 */
@Slf4j
public class ElasticSearchService {
    public final static String PK_FIELD_NAME = "id";
    public final static Long MAX_BUCKETS = 20000000L;
    public RestHighLevelClient restHighLevelClient = null;
    private boolean dateToTimestamp = false;
    private RestClient restClient = null;
    private SqlParser sqlParser = null;

    private ElasticSearchProperties properties;

    /**
     * 初始化
     */
    public void init(ElasticSearchProperties elasticSearchProperties) {
        this.properties = elasticSearchProperties;
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));

        RestClientBuilder builder = RestClient.builder(new HttpHost(properties.getIp(), properties.getPort()))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setMaxConnTotal(properties.getMaxConnectNum())
                        .setMaxConnPerRoute(properties.getMaxConnectPerRoute())
                )
                .setRequestConfigCallback(restClientBuilder -> {
                    restClientBuilder.setConnectTimeout(properties.getConnectTimeout());
                    restClientBuilder.setSocketTimeout(properties.getSocketTimeout());
                    restClientBuilder.setConnectionRequestTimeout(properties.getConnectionRequestTimeout());

                    return restClientBuilder;
                });

        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
        sqlParser = new SqlParser(properties);
        dateToTimestamp = properties.isDateToTimestamp();
    }

    /**
     * 注销
     */
    public boolean destroy() {
        try {
            if (Objects.nonNull(restClient)) {
                restClient.close();
            }
            if (Objects.nonNull(restHighLevelClient)) {
                restHighLevelClient.close();
            }
        } catch (Exception e) {
            log.error("关闭 restHighLevelClient 失败 ", e);
            return false;
        }
        return true;
    }

    /**
     * 创建索引
     * <p>
     * <a href="https://learnku.com/articles/36127">指标解释</a>
     *
     * @param index 索引名称
     * @return 是否成功
     */
    public boolean createIndex(String index) throws IOException {
        if (isIndexExist(index)) {
            log.error("Index is exits!");
            return false;
        }
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(Settings.builder()
                .put("number_of_shards", 1)
                .put("number_of_replicas", 0)
                // 关闭自动刷新,实时刷新会降低索引速度
                // .put("refresh_interval ", 10)
                .put("max_result_window", 20000000)
                .build());
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        log.info("创建索引{}成功", index);
        return response.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index 索引名称
     * @return 是否成功
     */
    public boolean isIndexExist(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param index 索引名称
     * @return 是否成功
     */
    public boolean deleteIndex(String index) throws IOException {
        if (!isIndexExist(index)) {
            log.error("Index is not exits!");
            return false;
        }
        // 删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        // 执行客户端请求
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        log.info("删除索引{}成功", index);
        return delete.isAcknowledged();
    }

    /**
     * 保存
     *
     * @param index 索引名称
     * @param data  数据集合
     * @return 是否成功
     */
    public boolean save(String index, Map<String, Object> data) {
        IndexRequest request = new IndexRequest(index);
        // 刷新策略
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        formatMap(data);
        request.source(data).opType(DocWriteRequest.OpType.INDEX);
        final IndexResponse response;
        try {
            response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            final DocWriteResponse.Result result = response.getResult();
            if (Objects.equals(result, DocWriteResponse.Result.UPDATED)) {
                log.warn("新增文档已存在，默认执行了修改操作：" + JackSonUtil.toJson(data));
                return true;
            }
            log.info("索引为: {}, 新增数据成功", index);
            return Objects.equals(result, DocWriteResponse.Result.CREATED);
        } catch (IOException e) {
            log.error(index + " 批量保存失败", e);
            return false;
        }
    }

    /**
     * 格式化 数据
     *
     * @param map 数据
     * @return 格式化后数据
     */
    private Map<String, Object> formatMap(Map<String, Object> map) {
        map.keySet().forEach(key -> map.compute(key, (k, value) -> formatValue(value)));
        return map;
    }

    private Object formatValue(Object value) {
        if (Objects.isNull(value)) {
            return null;
        } else if (value instanceof java.sql.Date) {
            final long dateLong = ((java.sql.Date) value).getTime();
            if (dateToTimestamp) {
                return processDateLong(dateLong);
            }
            return new Date(dateLong);
        } else if (value instanceof java.sql.Timestamp) {
            final long dateLong = ((java.sql.Timestamp) value).getTime();
            if (dateToTimestamp) {
                return processDateLong(dateLong);
            }
            return new Date(dateLong);
        } else if (value instanceof Date) {
            final Date date = (Date) value;
            if (dateToTimestamp) {
                return DateUtil.beginOfDay(date).getTime();
            }
            return date;
        }
        return value;
    }

    private Long processDateLong(long dateLong) {
        return DateUtil.beginOfDay(new Date(dateLong)).getTime();
    }

    /**
     * 保存
     *
     * @param index 索引名称
     * @param data  数据集合
     * @return 是否成功
     */
    public boolean saveByPkField(String index, Map<String, Object> data) {
        return saveByPkField(index, PK_FIELD_NAME, data);
    }

    /**
     * 保存
     *
     * @param index       索引名称
     * @param data        数据集合
     * @param pkFieldName 主键字段名称
     * @return 是否成功
     */
    public boolean saveByPkField(String index, String pkFieldName, Map<String, Object> data) {
        IndexRequest request = new IndexRequest(index);
        // 刷新策略
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        formatMap(data);
        request.source(data).opType(DocWriteRequest.OpType.INDEX);
        if (StrUtil.isNotBlank(pkFieldName)) {
            request.id(String.valueOf(data.get(pkFieldName)));
        }
        final String id = request.id();
        final IndexResponse response;
        try {
            response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            final DocWriteResponse.Result result = response.getResult();
            if (Objects.equals(result, DocWriteResponse.Result.UPDATED)) {
                log.warn("新增文档已存在，默认执行了修改操作：" + JackSonUtil.toJson(data));
                return true;
            }
            log.info("索引为: {}, id为: {} 新增数据成功", index, id);
            return Objects.equals(result, DocWriteResponse.Result.CREATED);
        } catch (IOException e) {
            log.error(index + " 批量保存失败", e);
            return false;
        }
    }

    /**
     * 批量保存
     *
     * @param index      索引名称
     * @param recordList 数据集合
     * @return 是否成功
     */
    public boolean batchSaveWithRecord(String index, List<Map<String, Object>> recordList) {
        BulkRequest request = new BulkRequest();
        // 刷新策略
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        recordList.forEach(record -> request.add(new IndexRequest(index).source(record)));
        final BulkResponse bulk;
        try {
            bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            return !bulk.hasFailures();
        } catch (IOException e) {
            log.error(index + " 批量保存失败", e);
            return false;
        }
    }

    /**
     * 批量保存
     *
     * @param index   索引名称
     * @param mapList 数据集合
     * @return 是否成功
     */
    public boolean batchSave(String index, List<Map<String, Object>> mapList) {
        BulkRequest request = new BulkRequest();
        mapList.forEach(record -> request.add(new IndexRequest(index).source(formatMap(record))));
        final BulkResponse bulk;
        try {
            bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            return !bulk.hasFailures();
        } catch (IOException e) {
            log.error(index + " 批量保存失败", e);
            return false;
        }
    }

    /**
     * 批量保存
     *
     * @param index   索引名称
     * @param mapList 数据集合
     * @return 是否成功
     */
    public boolean batchSaveByPkField(String index, List<Map<String, Object>> mapList) {
        return batchSaveByPkField(index, PK_FIELD_NAME, mapList);
    }

    /**
     * 批量保存
     *
     * @param index       索引名称
     * @param mapList     数据集合
     * @param pkFieldName 主键字段名称
     * @return 是否成功
     */
    public boolean batchSaveByPkField(String index, String pkFieldName, List<Map<String, Object>> mapList) {
        BulkRequest request = new BulkRequest();
        // 刷新策略
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        mapList.forEach(map -> {
            final IndexRequest indexRequest = new IndexRequest(index)
                    .source(formatMap(map));
            if (StrUtil.isNotBlank(pkFieldName)) {
                indexRequest.id(String.valueOf(map.get(pkFieldName)));
            }
            request.add(indexRequest);
        });
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        final BulkResponse bulk;
        try {
            bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            log.info("索引为: {}, 批量新增数据条数: {}, {}", index, mapList.size(), !bulk.hasFailures());
            return !bulk.hasFailures();
        } catch (IOException e) {
            log.error(index + " 批量保存失败", e);
            return false;
        }
    }

    /**
     * 根据条件修改数据
     *
     * @param index        索引
     * @param pkFieldName  主键字段
     * @param documentList 更新文档
     * @return 更新条数
     */
    public boolean batchUpdateByPkField(String index, String pkFieldName, List<Map<String, Object>> documentList) {
        BulkRequest bulkRequest = new BulkRequest(index);
        // 刷新策略
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        for (Map<String, Object> map : documentList) {
            formatMap(map);
            bulkRequest.add(new UpdateRequest(index, String.valueOf(map.get(pkFieldName))).doc(map));
        }
        BulkResponse response;
        try {
            response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("索引为: {}, 批量修改数据数据量: {}, {}", index, documentList.size(), !response.hasFailures());
            return !response.hasFailures();
        } catch (IOException e) {
            log.error(index + " 根据主键修改数据", e);
            return false;
        }
    }

    /**
     * 根据条件修改数据
     *
     * @param index    索引
     * @param query    查询条件
     * @param document 更新文档
     * @return 更新条数
     * @throws Exception
     */
    public long updateWithQuery(String index, QueryBuilder query, Map<String, Object> document) throws Exception {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(index);
        updateByQueryRequest.setQuery(query);
        formatMap(document);
        StringBuilder script = new StringBuilder();
        Set<String> keys = document.keySet();
        for (String key : keys) {
            String appendValue = "";
            Object value = formatValue(document.get(key));
            if (value instanceof Number) {
                appendValue = value.toString();
            } else if (value instanceof String) {
                appendValue = "'" + value + "'";
            } else if (value instanceof List) {
                appendValue = JackSonUtil.toJson(value);
            } else {
                appendValue = value.toString();
            }
            script.append("ctx._source.").append(key).append("=").append(appendValue).append(";");
        }
        updateByQueryRequest.setScript(new Script(script.toString()));
        final BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);

        return bulkByScrollResponse.getTotal();
    }

    /**
     * 根据主键修改数据
     *
     * @param index    索引
     * @param document 更新文档
     * @return 更新条数
     */
    public boolean updateByPkField(String index, Map<String, Object> document) {
        return updateByPkField(index, PK_FIELD_NAME, document);
    }

    /**
     * 根据主键修改数据
     *
     * @param index       索引
     * @param pkFieldName 文档主键名称
     * @param document    更新文档
     * @return 更新条数
     */
    public boolean updateByPkField(String index, String pkFieldName, Map<String, Object> document) {
        final String id = String.valueOf(document.get(pkFieldName));
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        // 刷新策略
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        formatMap(document);
        updateRequest.doc(document);

        final ActionListener<UpdateResponse> listener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                log.info("索引为: {}, id为: {} 修改数据成功", index, id);
            }

            @Override
            public void onFailure(Exception e) {
                log.error("索引为: {}, id为: {} 修改数据失败, 异常信息: {}", index, id, e.getMessage());
            }
        };
        restHighLevelClient.updateAsync(updateRequest, RequestOptions.DEFAULT, listener);
        return true;

    }

    /**
     * 根据主键修改数据
     *
     * @param index    索引
     * @param id       文档主键
     * @param document 更新文档
     * @return 更新条数
     */
    public boolean updateById(String index, String id, Map<String, Object> document) {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        // 刷新策略
        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        formatMap(document);
        updateRequest.doc(document);
        final UpdateResponse response;
        try {
            response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("索引为: {}, id为: {} 修改数据成功", index, id);
            return Objects.equals(response.getResult(), DocWriteResponse.Result.UPDATED);
        } catch (IOException e) {
            log.error(index + " 根据主键修改数据", e);
            return false;
        }
    }

    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param id    数据ID
     */
    public boolean deleteDataById(String index, String id) {
        try {
            DeleteRequest request = new DeleteRequest(index, id);
            // 刷新策略
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            log.info("索引为: {}, id为: {} 删除数据成功", index, id);
            return true;
        } catch (IOException e) {
            log.error("索引为: " + index + ", id为: " + id + " 删除数据失败", e);
            return false;
        }
    }

    /**
     * 根据条件修改数据
     *
     * @param index 索引
     * @param ids   主键字段集合
     * @return 更新条数
     */
    public boolean batchDelete(String index, List<String> ids) {
        BulkRequest bulkRequest = new BulkRequest(index);
        // 刷新策略
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        if (CollUtil.isEmpty(ids)) {
            return true;
        }
        ids.forEach(id -> bulkRequest.add(new DeleteRequest(index, id)));
        BulkResponse response;
        try {
            response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("索引为: {}, 批量删除数据条数: {} 成功", index, ids.size());
            return !response.hasFailures();
        } catch (IOException e) {
            log.error(index + " 根据主键删除数据异常", e);
            return false;
        }
    }

    public ResponseEntity initTest() throws Exception {
        log.info("EsPlugin Init Test ...");
        Request request = new Request("POST", "/_sql?format=json");
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", "show tables");
        jsonObject.put("fetch_size", 100);
        final String json = jsonObject.toString();
        request.setJsonEntity(json);
        final Response response = restClient.performRequest(request);
        final String resultStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        return JackSonUtil.toObject(resultStr, ResponseEntity.class);
    }

    /**
     * 查找当前最大桶数量
     *
     * @return 最大桶数量
     * @throws Exception
     */

    public Long findMaxBuckets() throws Exception {
        Request request = new Request("GET", "/_cluster/settings");
        final Response response = restClient.performRequest(request);
        final String resultStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        return JSONUtil.parseObj(resultStr).getByPath("persistent.search.max_buckets", Long.class);
    }

    /**
     * 设置最大桶数量
     *
     * @param maxBuckets 最大桶数量
     * @return 是否成功
     * @throws Exception
     */
    public boolean putMaxBuckets(Integer maxBuckets) throws Exception {
        Request request = new Request("PUT", "/_cluster/settings");
        final JSONObject json = new JSONObject();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.set("search.max_buckets", maxBuckets);
        json.set("persistent", jsonObject);
        final String jsonStr = json.toString();
        request.setJsonEntity(jsonStr);
        final Response response = restClient.performRequest(request);
        final String resultStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        return JSONUtil.parseObj(resultStr).getByPath("acknowledged", Boolean.class);
    }

    /**
     * 根据 sql 分页查询
     *
     * @param tableName        表名
     * @param pageNum          当前页
     * @param pageSize         每页数量
     * @param sql              sql语句
     * @param includeFieldList 包含字段
     * @return Page<Map < String, Object>> 查询结果
     */
    public Page<Map<String, Object>> findPageBySql(String tableName, Integer pageNum, Integer pageSize, String sql, List<String> includeFieldList) {
        SearchResponse searchResponse = null;
        try {
            SearchSourceBuilder searchSourceBuilder = sqlParser.parse(sql);
            /*
             * 跟踪总点击量
             * 该  trackTotalHits参数允许您控制应如何跟踪总命中数
             * 设置为true搜索响应时，将始终准确跟踪与查询匹配的匹配数 反之返回命中数的下限10000
             * 如果您不需要很准确的跟踪命中数 那么此处可设置为false，以便于加速搜索
             */
            searchSourceBuilder.trackTotalHits(true);
            searchSourceBuilder.from((pageNum - 1) * pageSize);
            searchSourceBuilder.size(pageSize);
            if (CollUtil.isNotEmpty(includeFieldList)) {
                // 过滤字段
                final FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includeFieldList.toArray(new String[]{}), Strings.EMPTY_ARRAY);
                searchSourceBuilder.fetchSource(fetchSourceContext);
            }
            log.info("query dsl: " + searchSourceBuilder);
            SearchRequest searchRequest = new SearchRequest(tableName);
            searchRequest.source(searchSourceBuilder);
            RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
            builder.setHttpAsyncResponseConsumerFactory(
                    new HttpAsyncResponseConsumerFactory
                            .HeapBufferedResponseConsumerFactory(1000 * 1024 * 1024));
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("sql to dsl fail!", e);
        }
        // return searchResponse;
        return this.responseToPage(pageNum, pageSize, searchResponse);
    }

    /**
     * 转成分页数据
     *
     * @param pageNum        当前页
     * @param pageSize       每页数
     * @param searchResponse 查询结果
     * @return 返回分页数据
     */
    private Page<Map<String, Object>> responseToPage(Integer pageNum, Integer pageSize, SearchResponse searchResponse) {
        Page<Map<String, Object>> recordPage = new Page<>(pageNum, pageSize, -1, null);
        if (Objects.isNull(searchResponse)) {
            return recordPage;
        }
        final SearchHits hits = searchResponse.getHits();
        final int total = Math.toIntExact(hits.getTotalHits().value);
        List<Map<String, Object>> mapList = new ArrayList<>(pageSize);
        Arrays.stream(hits.getHits()).forEach(hit -> mapList.add(hit.getSourceAsMap()));

        recordPage.setPages(((total - 1) / pageSize));
        recordPage.setTotal(total);
        recordPage.setCurrent(pageNum);
        recordPage.setCurrent(pageSize);
        recordPage.setRecords(mapList);
        return recordPage;
    }

    /**
     * 根据 sql 语法查询数据
     *
     * @param sql sql 语句
     * @return ResponseEntity 结果
     */
    public ResponseEntity findBySql(String sql) {
        Request request = new Request("POST", "/_sql?format=json");
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", sql);
        jsonObject.put("fetch_size", MAX_BUCKETS);
        final String json = jsonObject.toString();
        request.setJsonEntity(json);
        try {
            final Response response = restClient.performRequest(request);
            final String resultStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            return JackSonUtil.toObject(resultStr, ResponseEntity.class);
        } catch (Exception e) {
            log.error("执行查询语句失败：" + sql, e);
        }
        return ResponseEntity.EMPTY_OBJ;
    }

    /**
     * 根据 sql 语法查询数据
     *
     * @param sql sql 语句
     * @return 结果
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.8/java-rest-high-count.html">Elastic Count API</a>
     */
    public Number findCountBySql(String sql) {
        // 添加索引 多个年度数据同时存在es单节点中时,不传表名会从全部文档中进行查询,造成数据重复
        Collection<String> tableList = new TableNameParser(sql).tables();
        Optional<String> optional = tableList.stream().findFirst();
        String tableName = "";
        if (optional.isPresent()) {
            tableName = optional.get().replaceAll("\"", "");
        }
        // 通过CountRequest查询获得count
        CountRequest countRequest = new CountRequest();
        try {
            SearchSourceBuilder searchSourceBuilder = sqlParser.parse(sql);
            if (StrUtil.isNotBlank(tableName)) {
                countRequest.indices(tableName);
            }
            countRequest.source(searchSourceBuilder);
            CountResponse response = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            long count = response.getCount();
            return new Long(count).intValue();
        } catch (Exception e) {
            log.error("执行查询语句失败：" + sql, e);
            return 0;
        }
    }

    private String processDateStr(long dateLong) {
        return DateUtil.formatDate(new Date(dateLong));
    }

    private Date processDate(long dateLong) {
        return DateUtil.beginOfDay(new Date(dateLong)).toJdkDate();
    }

    /**
     * 通过ID获取数据
     *
     * @param index 索引，类似数据库
     * @param id    数据ID
     * @return 查询结果
     */
    public Map<String, Object> findById(String index, String id) throws IOException {
        return findById(index, id, null);
    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return 查询结果
     */
    public Map<String, Object> findById(String index, String id, String fields) throws IOException {
        GetRequest request = new GetRequest(index, id);
        if (StrUtil.isNotEmpty(fields)) {
            // 只查询特定字段。如果需要查询所有字段则不设置该项。
            request.fetchSourceContext(new FetchSourceContext(true, fields.split(","),
                    Strings.EMPTY_ARRAY));
        }
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        Map<String, Object> map = response.getSource();
        // 为返回的数据添加id
        map.put("_id", response.getId());
        return map;
    }

    /**
     * 获取索引名称
     *
     * @param indexName 索引名称
     * @throws IOException
     */
    public String getMappings(String indexName) throws IOException {
        // 构建请求
        GetMappingsRequest request = new GetMappingsRequest().indices(indexName);
        // 使用RestHighLevelClient发起请求
        GetMappingsResponse response = restHighLevelClient.indices().getMapping(request, RequestOptions.DEFAULT);
        Map<String, MappingMetadata> mappingMap = response.mappings();
        MappingMetadata indexMapping = mappingMap.get(indexName);
        Map<String, Object> mapping = indexMapping.sourceAsMap();
        return JackSonUtil.toJson(mapping);
    }

    /**
     * 创建 mappings
     * <p>
     * https://www.cnblogs.com/xiaoyh/p/16061594.html
     *
     * @param indexName 索引名称
     * @param mappings  映射
     * @return 是否成功
     * @throws IOException
     */
    public boolean createMappings(String indexName, String mappings) throws IOException {
        // 创建索引对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        // 设置参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards", "1").put("number_of_replicas", "0"));
        // 指定映射
        createIndexRequest.mapping(mappings, XContentType.JSON);
        // 额外参数
        // 设置超时时间
        createIndexRequest.setTimeout(TimeValue.timeValueMinutes(2));
        // 设置主节点超时时间
        createIndexRequest.setMasterTimeout(TimeValue.timeValueMinutes(1));
        // 在创建索引API返回响应之前等待的活动分片副本的数量，以int形式表示
        createIndexRequest.waitForActiveShards(ActiveShardCount.from(2));
        createIndexRequest.waitForActiveShards(ActiveShardCount.DEFAULT);

        // 执行创建索引库
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

        // 得到响应（全部）
        boolean acknowledged = createIndexResponse.isAcknowledged();
        // 得到响应 指示是否在超时前为索引中的每个分片启动了所需数量的碎片副本
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        return acknowledged;
    }

    /**
     * 创建 mappings
     * <p>
     * https://www.cnblogs.com/xiaoyh/p/16061594.html
     *
     * @param indexName      索引名称
     * @param contentBuilder 映射
     * @return 是否成功
     * @throws IOException
     */
    public boolean createMappings(String indexName, XContentBuilder contentBuilder) throws IOException {
        // 创建索引对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        // 设置参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards", "1").put("number_of_replicas", "0"));
        // 指定映射
        createIndexRequest.mapping(contentBuilder);
        // 额外参数
        // 设置超时时间
        createIndexRequest.setTimeout(TimeValue.timeValueMinutes(2));
        // 设置主节点超时时间
        createIndexRequest.setMasterTimeout(TimeValue.timeValueMinutes(1));
        // 在创建索引API返回响应之前等待的活动分片副本的数量，以int形式表示
        createIndexRequest.waitForActiveShards(ActiveShardCount.from(2));
        createIndexRequest.waitForActiveShards(ActiveShardCount.DEFAULT);

        // 执行创建索引库
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

        // 得到响应（全部）
        boolean acknowledged = createIndexResponse.isAcknowledged();
        // 得到响应 指示是否在超时前为索引中的每个分片启动了所需数量的碎片副本
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        return acknowledged;
    }

    /**
     * 修改索引mappings
     *
     * @param indexName 索引名称
     * @param mappings  映射
     * @return
     * @throws IOException
     */
    public boolean putMappings(String indexName, String mappings) throws IOException {
        // 创建修改索引映射的请求
        PutMappingRequest request = new PutMappingRequest(indexName)
                .source(mappings, XContentType.JSON);

        // 发送请求并获取响应
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }

    /**
     * 修改索引mappings
     *
     * @param indexName      索引名称
     * @param contentBuilder 映射
     * @return
     * @throws IOException
     */
    public boolean putMappings(String indexName, XContentBuilder contentBuilder) throws IOException {
        // 创建修改索引映射的请求
        PutMappingRequest request = new PutMappingRequest(indexName)
                .source(contentBuilder);

        // 发送请求并获取响应
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }
}
