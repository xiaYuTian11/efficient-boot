package com.efficient.data.security.db.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.efficient.data.security.annotation.DbEncrypted;
import com.efficient.data.security.db.crypt.DbAES;
import com.efficient.data.security.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * 查询解密
 *
 * @author TMW
 * @since 2023/6/8 16:55
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
@Component
@Slf4j
public class EncryptInterceptor implements Interceptor {

    private static final String MAPPED_STATEMENT = "mappedStatement";
    @Autowired
    private DbAES dbAES;

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!DbAES.dbEncryptEnable) {
            return invocation.proceed();
        }
        final List<Object> results = (List<Object>) invocation.proceed();

        if (results.isEmpty()) {
            return results;
        }

        Object result0 = results.get(0);
        if (Objects.isNull(result0)) {
            return results;
        }
        final Class<?> result0Class = result0.getClass();
        DbEncrypted encryptEnabled = result0Class.getAnnotation(DbEncrypted.class);
        final List<String> list = ReflectionUtil.ENCRYPT_MAP.get(result0Class.getTypeName());
        if (encryptEnabled == null || !encryptEnabled.value() || CollUtil.isEmpty(list)) {
            return results;
        }

        final ResultSetHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        final MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        final MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MAPPED_STATEMENT);
        for (Object obj : results) {
            if (Objects.nonNull(obj)) {
                final MetaObject objMetaObject = mappedStatement.getConfiguration().newMetaObject(obj);
                for (String fieldName : list) {
                    String value = (String) objMetaObject.getValue(fieldName);
                    objMetaObject.setValue(fieldName, dbAES.decrypt(value));
                }
            }
        }
        return results;
    }
}
