# efficient
基于springboot的二次封装框架

## 安装依赖
```text
mvn -Dmaven.test.skip=true install
mvn install:install-file    -DgroupId=com.aspose -DartifactId=aspose-cells -Dversion=21.11 -Dpackaging=jar   -Dfile=./aspose-cells-21.11.jar
mvn install:install-file    -DgroupId=com.aspose -DartifactId=aspose-words -Dversion=21.6 -Dpackaging=jar   -Dfile=./aspose-words-21.11.0-jdk17.jar
```
## 修改版本
```text
mvn versions:set -DnewVersion=
```

## 发布
```text
mvn clean deploy -P oss-release -Dmaven.test.skip=true 
```