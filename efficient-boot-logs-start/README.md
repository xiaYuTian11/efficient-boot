## 使用

1.引入依赖

```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>efficient-boot-logs-start</artifactId>
    <version>对应版本号</version>
</dependency>
```

2.application.yml中添加配置

```yaml
com:
  efficient:
    logs:
      level: info
logging:
  level:
    com.efficient.*.dao: debug
```