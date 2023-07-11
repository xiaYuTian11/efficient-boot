package com.efficient.data.security.db.typehandler;

import com.efficient.data.security.db.alias.DbEncrypt;
import com.efficient.data.security.db.crypt.DbAES;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 只处理 String 类型的数据
 *
 * @author TMW
 * @since 2023/6/8 16:55
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
@MappedTypes(value = DbEncrypt.class)
public class EncryptTypeHandler extends BaseTypeHandler<String> {
    @Autowired
    private DbAES dbAES;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, dbAES.encrypt(parameter));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return dbAES.decrypt(rs.getString(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return dbAES.decrypt(rs.getString(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return dbAES.decrypt(cs.getString(columnIndex));
    }
}