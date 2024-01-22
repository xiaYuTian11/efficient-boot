# efficient

基于springboot的二次封装框架

```text
开发使用可以查看demo工程：https://github.com/xiaYuTian11/xxx-project
配套的代码生成器地址可以查看：https://github.com/xiaYuTian11/efficient-generator
```
- [efficient](#efficient)
    * [引入依赖](#%E5%BC%95%E5%85%A5%E4%BE%9D%E8%B5%96)
        + [说明](#%E8%AF%B4%E6%98%8E)
    * [安装依赖](#%E5%AE%89%E8%A3%85%E4%BE%9D%E8%B5%96)
        + [aspose](#aspose)
        + [渝快政](#%E6%B8%9D%E5%BF%AB%E6%94%BF)
    * [指定maven仓库](#%E6%8C%87%E5%AE%9Amaven%E4%BB%93%E5%BA%93)
    * [修改版本](#%E4%BF%AE%E6%94%B9%E7%89%88%E6%9C%AC)
    * [发布](#%E5%8F%91%E5%B8%83)
    * [权限认证模块](#%E6%9D%83%E9%99%90%E8%AE%A4%E8%AF%81%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96)
        + [配置](#%E9%85%8D%E7%BD%AE)
    * [缓存模块](#%E7%BC%93%E5%AD%98%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-1)
        + [配置](#%E9%85%8D%E7%BD%AE-1)
        + [使用](#%E4%BD%BF%E7%94%A8)
    * [通用模块](#%E9%80%9A%E7%94%A8%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-2)
    * [配置模块](#%E9%85%8D%E7%BD%AE%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-3)
    * [文件上传下载模块](#%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E4%B8%8B%E8%BD%BD%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-4)
        + [配置](#%E9%85%8D%E7%BD%AE-2)
        + [建表语句](#%E5%BB%BA%E8%A1%A8%E8%AF%AD%E5%8F%A5)
    * [流量控制](#%E6%B5%81%E9%87%8F%E6%8E%A7%E5%88%B6)
        + [依赖](#%E4%BE%9D%E8%B5%96-5)
        + [配置](#%E9%85%8D%E7%BD%AE-3)
    * [日志模块](#%E6%97%A5%E5%BF%97%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-6)
        + [配置](#%E9%85%8D%E7%BD%AE-4)
        + [使用](#%E4%BD%BF%E7%94%A8-1)
        + [建表sql](#%E5%BB%BA%E8%A1%A8sql)
    * [swagger模块](#swagger%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-7)
        + [配置](#%E9%85%8D%E7%BD%AE-5)
    * [定时任务模块](#%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-8)
        + [配置](#%E9%85%8D%E7%BD%AE-6)
        + [使用](#%E4%BD%BF%E7%94%A8-2)
    * [数据安全模块](#%E6%95%B0%E6%8D%AE%E5%AE%89%E5%85%A8%E6%A8%A1%E5%9D%97)
        + [依赖](#%E4%BE%9D%E8%B5%96-9)
        + [Api数据加密](#api%E6%95%B0%E6%8D%AE%E5%8A%A0%E5%AF%86)
            - [配置](#%E9%85%8D%E7%BD%AE-7)
        + [Db数据加密](#db%E6%95%B0%E6%8D%AE%E5%8A%A0%E5%AF%86)
            - [配置](#%E9%85%8D%E7%BD%AE-8)
            - [注意事项](#%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9)
        + [返回数据脱敏处理](#%E8%BF%94%E5%9B%9E%E6%95%B0%E6%8D%AE%E8%84%B1%E6%95%8F%E5%A4%84%E7%90%86)
    * [渝快政对接](#%E6%B8%9D%E5%BF%AB%E6%94%BF%E5%AF%B9%E6%8E%A5)
        + [安装渝快政本地依赖](#%E5%AE%89%E8%A3%85%E6%B8%9D%E5%BF%AB%E6%94%BF%E6%9C%AC%E5%9C%B0%E4%BE%9D%E8%B5%96)
        + [引入框架依赖](#%E5%BC%95%E5%85%A5%E6%A1%86%E6%9E%B6%E4%BE%9D%E8%B5%96)
        + [服务地址](#%E6%9C%8D%E5%8A%A1%E5%9C%B0%E5%9D%80)
        + [建表语句](#%E5%BB%BA%E8%A1%A8%E8%AF%AD%E5%8F%A5-1)

## 引入依赖

```xml

<parent>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot</artifactId>
    <version>${version}</version>
</parent>
```

### 说明
```text
下面所有的Sql语句都是以postgresql为基础的，使用其他数据库请自行修改
```

## 安装依赖

### aspose

```text
mvn -Dmaven.test.skip=true install

mvn install:install-file    -DgroupId=com.aspose -DartifactId=aspose-cells -Dversion=21.11 -Dpackaging=jar   -Dfile=./aspose-cells-21.11.jar

mvn install:install-file    -DgroupId=com.aspose -DartifactId=aspose-words -Dversion=21.6 -Dpackaging=jar   -Dfile=./aspose-words-21.11.0-jdk17.jar
```

### 渝快政

```text
mvn -Dmaven.test.skip=true install
mvn install:install-file    -DgroupId=com.dcqc -DartifactId=dcqc-uc-oauth-sdk -Dversion=3.0.0-RELEASE -Dpackaging=jar   -Dfile=./dcqc-uc-oauth-sdk-3.0.0-RELEASE.jar

mvn install:install-file    -DgroupId=com.alibaba -DartifactId=zwdd-sdk-java -Dversion=1.2.0 -Dpackaging=jar   -Dfile=./zwdd-sdk-java-1.2.0.jar
```

## 指定maven仓库

```xml

<repositories>
    <repository>
        <id>maven-public</id>
        <name>maven-public</name>
        <url>https://repo.maven.apache.org/maven2/</url>
        <layout>default</layout>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </repository>
    <repository>
        <id>sonatype</id>
        <name>sonatype maven</name>
        <url>https://s01.oss.sonatype.org/content/repositories/releases/</url>
    </repository>
    <repository>
        <id>sonatype</id>
        <name>sonatype maven</name>
        <url>https://oss.sonatype.org/content/repositories/releases/</url>
    </repository>
</repositories>
```

## 修改版本

```text
mvn versions:set -DnewVersion=
```

## 发布

```text
mvn clean deploy -P oss-release -Dmaven.test.skip=true 
```

## 权限认证模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-auth-start</artifactId>
    <version>${efficient.version}</version>
</dependency>
```

### 配置

```yaml
com:
  efficient:
    auth:
      ## 是否启用验证码
      captcha: false
      ## jwt secret
      secret: qwertyuiop0987654321
      ## 重试次数,默认-1，重试次数无限制
      retryCount: -1
      ## 锁定时间，分钟
      lockTime: 30
      ## 同一账号最大在线人数
      maxOnline: -1
      ## 最大允许存活时间
      tokenLive: 3600
      ## 密码是否加密传输
      passwordEncrypt: false
      ## 密码是否加盐
      enableSalt: false
      ## 盐值
      saltValue: "1809"
```

## 缓存模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-cache-start</artifactId>
</dependency>
        <!-- ehcache 依赖-->
<dependency>
<groupId>net.sf.ehcache</groupId>
<artifactId>ehcache</artifactId>
</dependency>
        <!-- redis 依赖-->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 配置

```yaml
## 配置redis缓存需要额外配置spring的redis配置
spring:
  redis:
    database: 0
    host: 127.0.0.1
    port: 6379
    password: 123456
    timeout: 3000
com:
  efficient:
    cache:
      active: redis
```

### 使用

````java

@Autowired
private CacheUtil cacheUtil;
````

ehcache.xml,目前必备的几个缓存配置

```xml

<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd" updateCheck="false">

    <diskStore path="java.io.tmpdir"/>

    <!--
    Mandatory Default Cache configuration. These settings will be applied to caches
    created programmtically using CacheManager.add(String cacheName)
    -->
    <!--
       name:缓存名称。
       maxElementsInMemory：缓存最大个数。
       eternal:对象是否永久有效，一但设置了，timeout将不起作用。
       timeToIdleSeconds：设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
       timeToLiveSeconds：设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
       overflowToDisk：当内存中对象数量达到maxElementsInMemory时，Ehcache将会对象写到磁盘中。
       diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
       maxElementsOnDisk：硬盘最大缓存个数。
       diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
       diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
       memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
       clearOnFlush：内存数量最大时是否清除。
    -->
    <cache
            name="system"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="86400"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
    <cache
            name="idempotence_cache_"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="86400"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
    <cache
            name="auth-captcha-cache"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="60"
            timeToLiveSeconds="60"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
    <cache
            name="auth-cache"
            maxElementsInMemory="10000"
            eternal="false"
            overflowToDisk="false"
            maxElementsOnDisk="10000000"
            timeToIdleSeconds="3600"
            timeToLiveSeconds="86400"
            diskPersistent="false"
            memoryStoreEvictionPolicy="FIFO"/>
</ehcache>
```

## 通用模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-common</artifactId>
</dependency>
```

## 配置模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-configs-start</artifactId>
</dependency>
```

## 文件上传下载模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-file-start</artifactId>
</dependency>
```

### 配置

```yaml
com:
  efficient:
    file:
      ## 存储方式，local，db，minio
      active: local
      ## 临时存储路径
      tempPath: "/temp"
      local:
        ## 本地存储路径
        localPath: "/upload"
        ## 是否添加时间戳
        addDatePrefix: true
```

### 建表语句

```sql
DROP TABLE IF EXISTS "efficient_sys_file_info";
CREATE TABLE public.efficient_sys_file_info (
                                                id varchar(64) NOT NULL, -- 主键
                                                biz_id varchar(64) NULL, -- 业务主键
                                                store_type varchar(10) NULL, -- 存储类型
                                                file_name text NULL, -- 文件名称
                                                file_path text NULL, -- 文件类型
                                                file_content bytea NULL, -- 文件类型
                                                file_size int8 NULL, -- 文件大写，kb单位
                                                create_time timestamp(6) NULL, -- 创建时间
                                                remark text NULL, -- 备注
                                                CONSTRAINT sys_file_info_pkey PRIMARY KEY (id)
);
CREATE INDEX index_efficient_sys_file_info_biz_id ON public.efficient_sys_file_info USING hash (biz_id);
COMMENT ON TABLE public.efficient_sys_file_info IS '文件信息';

-- Column comments

COMMENT ON COLUMN public.efficient_sys_file_info.id IS '主键';
COMMENT ON COLUMN public.efficient_sys_file_info.biz_id IS '业务主键';
COMMENT ON COLUMN public.efficient_sys_file_info.store_type IS '存储类型';
COMMENT ON COLUMN public.efficient_sys_file_info.file_name IS '文件名称';
COMMENT ON COLUMN public.efficient_sys_file_info.file_path IS '文件类型';
COMMENT ON COLUMN public.efficient_sys_file_info.file_content IS '文件类型';
COMMENT ON COLUMN public.efficient_sys_file_info.file_size IS '文件大写，kb单位';
COMMENT ON COLUMN public.efficient_sys_file_info.create_time IS '创建时间';
COMMENT ON COLUMN public.efficient_sys_file_info.remark IS '备注';

```

## 流量控制

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-rate-start</artifactId>
</dependency>
```

### 配置

```yaml
com:
  efficient:
    ## 是否启用全局幂等性校验,启用后所有接口都会校验幂等性
    rate: true
    ## 全局幂等性校验间隔时间，设置后，com.efficient.rate.annotation.RateLimit的过期时间将会失效,最低一秒钟,单位毫秒，非全局接口限流的情况下，请在相应接口添加@RateLimit注解
    expireTime: 1000
```

## 日志模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-logs-start</artifactId>
</dependency>
```

### 配置

```yaml
com:
  efficient:
    logs:
      ## 日志文件名称
      name: xxx-project
      ## 日志等级
      level: info
      ## 日志存储路径
      path: /home/logs/xxx-project/
      sql:
        ## 是否展示方法名
        showMethod: false
        ## 是否展示SQL
        showSql: true
        ## 是否展示执行耗时
        showElapsed: false
        ## 是否展示结果行数
        showRows: false
        ## sql日志等级
        level: debug
        ## dao包的路径
        daoPackage: com.zenith.xxx.dao
      ## 是否存储数据库
      db: false
```

### 使用

```java
/**
 * 需要在controller的方法上添加@Log注解
 */
@Log(logOpt = LogEnum.SAVE)

```

### 建表sql

```sql
DROP TABLE IF EXISTS "efficient_sys_log";
CREATE TABLE public.efficient_sys_log (
                                          id varchar(64) NOT NULL, -- 主键
                                          "module" varchar(255) NULL, -- 模块
                                          user_id varchar(255) NULL, -- 用户ID
                                          user_name varchar(255) NULL, -- 用户名
                                          log_ip varchar(20) NULL, -- 操作IP
                                          log_time timestamp(6) NULL, -- 记录日志时间
                                          request_url text NULL, -- 请求路径
                                          log_opt varchar(10) NULL, -- 操作类型
                                          log_content text NULL, -- 操作内容
                                          params text NULL, -- 参数
                                          result_code varchar(10) NULL, -- 结果
                                          "result" text NULL, -- 返回值
                                          "exception" text NULL, -- 异常信息
                                          CONSTRAINT efficient_sys_log_pkey PRIMARY KEY (id)
);
CREATE INDEX index_efficient_sys_log_log_opt ON public.efficient_sys_log USING btree (log_opt);
CREATE INDEX index_efficient_sys_log_log_time ON public.efficient_sys_log USING btree (log_time);
COMMENT ON TABLE public.efficient_sys_log IS '日志表';

-- Column comments

COMMENT ON COLUMN public.efficient_sys_log.id IS '主键';
COMMENT ON COLUMN public.efficient_sys_log."module" IS '模块';
COMMENT ON COLUMN public.efficient_sys_log.user_id IS '用户ID';
COMMENT ON COLUMN public.efficient_sys_log.user_name IS '用户名';
COMMENT ON COLUMN public.efficient_sys_log.log_ip IS '操作IP';
COMMENT ON COLUMN public.efficient_sys_log.log_time IS '记录日志时间';
COMMENT ON COLUMN public.efficient_sys_log.request_url IS '请求路径';
COMMENT ON COLUMN public.efficient_sys_log.log_opt IS '操作类型';
COMMENT ON COLUMN public.efficient_sys_log.log_content IS '操作内容';
COMMENT ON COLUMN public.efficient_sys_log.params IS '参数';
COMMENT ON COLUMN public.efficient_sys_log.result_code IS '结果';
COMMENT ON COLUMN public.efficient_sys_log."result" IS '返回值';
COMMENT ON COLUMN public.efficient_sys_log."exception" IS '异常信息';

```

## swagger模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-swagger-start</artifactId>
</dependency>
```

### 配置

```yaml
com:
  efficient:
    swagger:
      enable: true
      version: 1.0.1
      description: "描述"
      title: "标题"
      termsOfServiceUrl: "服务网址"
```

## 定时任务模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-task-start</artifactId>
</dependency>
```

### 配置

```yaml
com:
  efficient:
    task:
      enable: true
```

### 使用

```java
/**
 * 需要继承QuartzJobBean类，并在数据库中进行配置
 * INSERT INTO "public"."sys_task" ("id", "task_code", "task_describe", "task_class", "enabled", "cron_expression", "create_time", "task_status") VALUES ('1', 'test', '测试定时任务', 'com.zenith.xxx.service.task.TaskTest', 1, '1 * * * * ?', '2022-08-29 17:04:10', 1);

 */
@Slf4j
public class TaskTest extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("测试定时任务");
    }
}

```

```sql
DROP TABLE IF EXISTS "efficient_sys_task";
CREATE TABLE public.efficient_sys_task (
                                           id varchar(255) NOT NULL, -- 主键
                                           task_code varchar(255) NULL, -- 定时任务code
                                           task_describe varchar(255) NULL, -- 定时任务描述
                                           task_class varchar(255) NULL, -- 定时任务全限定名称
                                           enabled int4 NULL, -- 是否启用
                                           cron_expression varchar(255) NULL, -- 表达式
                                           create_time timestamp(6) NULL, -- 创建时间
                                           task_status int4 NULL, -- 当前定时任务状态
                                           CONSTRAINT sys_task_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE public.efficient_sys_task IS '定时任务表';

-- Column comments

COMMENT ON COLUMN public.efficient_sys_task.id IS '主键';
COMMENT ON COLUMN public.efficient_sys_task.task_code IS '定时任务code';
COMMENT ON COLUMN public.efficient_sys_task.task_describe IS '定时任务描述';
COMMENT ON COLUMN public.efficient_sys_task.task_class IS '定时任务全限定名称';
COMMENT ON COLUMN public.efficient_sys_task.enabled IS '是否启用';
COMMENT ON COLUMN public.efficient_sys_task.cron_expression IS '表达式';
COMMENT ON COLUMN public.efficient_sys_task.create_time IS '创建时间';
COMMENT ON COLUMN public.efficient_sys_task.task_status IS '当前定时任务状态';
```

## 数据安全模块

### 依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-data-security-start</artifactId>
    <version>${version}</version>
</dependency>
```

### Api数据加密

#### 配置

````yaml
com:
  efficient:
    security:
      api:
        ## 请求及返回相关数据加解密配置
        requestEnable: true
        requestEnableType: node
        responseEnable: true
        responseEnableType: node

````

### Db数据加密

#### 配置

````yaml
com:
  efficient:
    security:
      db:
        dbEncryptEnable: true
        dbEncryptModelPath: com.zenith.xxx.model.entity
````

#### 注意事项

````text
在通过Get请求时，参数加密完后还需要额外进行一次urlEncode转化，避免一些特殊字符在get请求时传递到后端后发生改变
````

### 返回数据脱敏处理

````java
/**
 * 在需要进行脱敏处理的字段上添加注解@Sensitive即可，目前只支持String类型
 */
@Data
public class UserTest {
    @Sensitive(rule = SensitiveType.USER_ID)
    private String id;
    @Sensitive(rule = SensitiveType.CHINESE_NAME)
    private String name;
    @Sensitive(rule = SensitiveType.ID_CARD)
    private String idCard;
    @Sensitive(rule = SensitiveType.FIXED_PHONE)
    private String fixedPhone;
    @Sensitive(rule = SensitiveType.MOBILE_PHONE)
    private String mobilePhone;
    @Sensitive(rule = SensitiveType.ADDRESS)
    private String address;
    @Sensitive(rule = SensitiveType.EMAIL)
    private String email;
    @Sensitive(rule = SensitiveType.PASSWORD)
    private String password;
    @Sensitive(rule = SensitiveType.CAR_LICENSE)
    private String carLicense;
    @Sensitive(rule = SensitiveType.BANK_CARD)
    private String bankCard;
}

````

## 渝快政对接

### 安装渝快政本地依赖
```text
mvn -Dmaven.test.skip=true install
mvn install:install-file    -DgroupId=com.dcqc -DartifactId=dcqc-uc-oauth-sdk -Dversion=3.0.0-RELEASE -Dpackaging=jar   -Dfile=./dcqc-uc-oauth-sdk-3.0.0-RELEASE.jar

mvn install:install-file    -DgroupId=com.alibaba -DartifactId=zwdd-sdk-java -Dversion=1.2.0 -Dpackaging=jar   -Dfile=./zwdd-sdk-java-1.2.0.jar
```

### 引入框架依赖
```xml
<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-ykz-start</artifactId>
    <version>${efficent.version}</version>
</dependency>
<dependency>
<groupId>top.tanmw</groupId>
<artifactId>efficient-boot-common</artifactId>
<version>${efficent.version}</version>
</dependency>
```

### 服务地址
```text
用户中心
com.efficient.ykz.controller.YkzUserCenterController
接口对接
com.efficient.ykz.controller.YkzApiController
```

### 建表语句
```sql
DROP TABLE IF EXISTS "efficient_ykz_org";
CREATE TABLE efficient_ykz_org (
                                   id VARCHAR(64) PRIMARY KEY,
                                   ykz_id BIGINT ,
                                   name VARCHAR(255),
                                   org_type VARCHAR(255),
                                   parent_id BIGINT,
                                   display_order BIGINT,
                                   is_deleted INTEGER,
                                   is_enable INTEGER,
                                   create_time BIGINT,
                                   gov_address VARCHAR(255),
                                   gov_division_code VARCHAR(255),
                                   gov_business_strip_codes VARCHAR(255),
                                   gov_institution_level_code VARCHAR(255),
                                   gov_short_name VARCHAR(255),
                                   organization_code VARCHAR(255),
                                   parent_organization_code VARCHAR(255),
                                   principal VARCHAR(255),
                                   update_time BIGINT,
                                   credit_code VARCHAR(255),
                                   remark VARCHAR(255),
                                   area_level VARCHAR(255),
                                   error_info text,
                                   pull_time timestamp
);
COMMENT ON TABLE public.efficient_ykz_org IS '渝快政机构数据';
COMMENT ON COLUMN efficient_ykz_org.id IS '机构id';
COMMENT ON COLUMN efficient_ykz_org.name IS '机构全称';
COMMENT ON COLUMN efficient_ykz_org.org_type IS '机构类型';
COMMENT ON COLUMN efficient_ykz_org.parent_id IS '机构父级id';
COMMENT ON COLUMN efficient_ykz_org.display_order IS '同级排序字段';
COMMENT ON COLUMN efficient_ykz_org.is_deleted IS '删除标识 1 表示删除，0 表示未删除';
COMMENT ON COLUMN efficient_ykz_org.is_enable IS '是否启用 1-启用，0-停用';
COMMENT ON COLUMN efficient_ykz_org.create_time IS '创建时间戳';
COMMENT ON COLUMN efficient_ykz_org.gov_address IS '单位地址';
COMMENT ON COLUMN efficient_ykz_org.gov_division_code IS '行政区划Code';
COMMENT ON COLUMN efficient_ykz_org.gov_business_strip_codes IS '条线Code列表';
COMMENT ON COLUMN efficient_ykz_org.gov_institution_level_code IS '机构/单位级别';
COMMENT ON COLUMN efficient_ykz_org.gov_short_name IS '机构简称';
COMMENT ON COLUMN efficient_ykz_org.organization_code IS '政务钉钉组织机构code';
COMMENT ON COLUMN efficient_ykz_org.parent_organization_code IS '父组织机构code';
COMMENT ON COLUMN efficient_ykz_org.principal IS '单位负责人userCode';
COMMENT ON COLUMN efficient_ykz_org.update_time IS '更新时间';
COMMENT ON COLUMN efficient_ykz_org.credit_code IS '统一信用代码';
COMMENT ON COLUMN efficient_ykz_org.remark IS '备注';
COMMENT ON COLUMN efficient_ykz_org.area_level IS '区域级别';
COMMENT ON COLUMN efficient_ykz_org.pull_time IS '拉取时间';
COMMENT ON COLUMN efficient_ykz_org.error_info IS '错误信息';


DROP TABLE IF EXISTS "efficient_ykz_user";
CREATE TABLE efficient_ykz_user (
                                    id VARCHAR(64) PRIMARY KEY,
                                    ykz_id BIGINT,
                                    name VARCHAR(255),
                                    username VARCHAR(255),
                                    account_id VARCHAR(255),
                                    employee_code VARCHAR(255),
                                    mobile VARCHAR(255),
                                    error_info text,
                                    pull_time timestamp
);

COMMENT ON TABLE efficient_ykz_user IS '渝快政用户信息';

COMMENT ON COLUMN efficient_ykz_user.id IS '用户中心 ID';
COMMENT ON COLUMN efficient_ykz_user.name IS '姓名';
COMMENT ON COLUMN efficient_ykz_user.username IS '用户名';
COMMENT ON COLUMN efficient_ykz_user.account_id IS '政务钉钉 ID';
COMMENT ON COLUMN efficient_ykz_user.employee_code IS '政务钉钉员工编号';
COMMENT ON COLUMN efficient_ykz_user.mobile IS '电话号码';
COMMENT ON COLUMN efficient_ykz_user.pull_time IS '拉取时间';
COMMENT ON COLUMN efficient_ykz_user.error_info IS '错误信息';

DROP TABLE IF EXISTS "efficient_ykz_user_post";
CREATE TABLE efficient_ykz_user_post (
                                         id VARCHAR(64) PRIMARY KEY,
                                         account_id VARCHAR(255),
                                         organization_code VARCHAR(255),
                                         post_type INTEGER,
                                         pos_job VARCHAR(255),
                                         error_info text,
                                         pull_time timestamp
);

COMMENT ON TABLE efficient_ykz_user_post IS '愉快政用户职位信息';

COMMENT ON COLUMN efficient_ykz_user.account_id IS '政务钉钉 ID';
COMMENT ON COLUMN efficient_ykz_user_post.organization_code IS '职务所在机构code';
COMMENT ON COLUMN efficient_ykz_user_post.post_type IS '任职类型 1主职、2兼职、3挂职、4借调';
COMMENT ON COLUMN efficient_ykz_user_post.pos_job IS '职务';
COMMENT ON COLUMN efficient_ykz_user_post.pull_time IS '拉取时间';
COMMENT ON COLUMN efficient_ykz_user_post.error_info IS '错误信息';
```