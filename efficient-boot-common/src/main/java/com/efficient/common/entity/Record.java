package com.efficient.common.entity;

import cn.hutool.core.date.DateUtil;
import com.efficient.common.util.JackSonUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author TMW
 * @since 2024/5/15 10:16
 */
public class Record implements Serializable {
    private static final long serialVersionUID = 905784513600884082L;

    private Map<String, Object> columns;    // = getColumnsMap();	// getConfig().containerFactory.getColumnsMap();	// new HashMap<String, Object>();

    // 用于 RecordBuilder 中注入 Map。也可以通过调用 CPI.setColumnsMap(record, columns) 实现
    void setColumnsMap(Map<String, Object> columns) {
        this.columns = columns;
    }

    /**
     * Remove attribute of this record.
     *
     * @param column the column name of the record
     */
    public Record remove(String column) {
        getColumns().remove(column);
        return this;
    }

    /**
     * Return columns map.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getColumns() {
        return columns;
    }

    /**
     * Set columns value with map.
     *
     * @param columns the columns map
     */
    public Record setColumns(Map<String, Object> columns) {
        this.getColumns().putAll(columns);
        return this;
    }

    /**
     * Set columns value with Record.
     *
     * @param record the Record object
     */
    public Record setColumns(Record record) {
        getColumns().putAll(record.getColumns());
        return this;
    }

    /**
     * Set columns value with Model object.
     *
     * @param model the Model object
     */

    /**
     * Remove columns of this record.
     *
     * @param columns the column names of the record
     */
    public Record remove(String... columns) {
        if (columns != null)
            for (String c : columns)
                this.getColumns().remove(c);
        return this;
    }

    /**
     * Remove columns if it is null.
     */
    public Record removeNullValueColumns() {
        for (java.util.Iterator<Map.Entry<String, Object>> it = getColumns().entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> e = it.next();
            if (e.getValue() == null) {
                it.remove();
            }
        }
        return this;
    }

    /**
     * Keep columns of this record and remove other columns.
     *
     * @param columns the column names of the record
     */
    public Record keep(String... columns) {
        if (columns != null && columns.length > 0) {
            Map<String, Object> newColumns = new HashMap<String, Object>(columns.length);    // getConfig().containerFactory.getColumnsMap();
            for (String c : columns)
                if (this.getColumns().containsKey(c))    // prevent put null value to the newColumns
                    newColumns.put(c, this.getColumns().get(c));

            this.getColumns().clear();
            this.getColumns().putAll(newColumns);
        } else
            this.getColumns().clear();
        return this;
    }

    /**
     * Keep column of this record and remove other columns.
     *
     * @param column the column names of the record
     */
    public Record keep(String column) {
        if (getColumns().containsKey(column)) {    // prevent put null value to the newColumns
            Object keepIt = getColumns().get(column);
            getColumns().clear();
            getColumns().put(column, keepIt);
        } else
            getColumns().clear();
        return this;
    }

    /**
     * Remove all columns of this record.
     */
    public Record clear() {
        getColumns().clear();
        return this;
    }

    /**
     * Set column to record.
     *
     * @param column the column name
     * @param value  the value of the column
     */
    public Record set(String column, Object value) {
        getColumns().put(column, value);
        return this;
    }

    /**
     * Get column of any mysql type
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String column) {
        return (T) getColumns().get(column);
    }

    /**
     * Get column of any mysql type. Returns defaultValue if null.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String column, Object defaultValue) {
        Object result = getColumns().get(column);
        return (T) (result != null ? result : defaultValue);
    }

    public Object getObject(String column) {
        return getColumns().get(column);
    }

    public Object getObject(String column, Object defaultValue) {
        Object result = getColumns().get(column);
        return result != null ? result : defaultValue;
    }

    /**
     * Get column of mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
     */
    public String getStr(String column) {
        // return (String)getColumns().get(column);
        Object s = getColumns().get(column);
        return s != null ? s.toString() : null;
    }

    /**
     * Get column of mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
     */
    public Integer getInt(String column) {
        Number n = getNumber(column);
        return n != null ? n.intValue() : null;
    }

    /**
     * Get column of any type that extends from Number
     */
    public Number getNumber(String column) {
        return (Number) getColumns().get(column);
    }

    /**
     * Get column of mysql type: bigint
     */
    public Long getLong(String column) {
        Number n = getNumber(column);
        return n != null ? n.longValue() : null;
    }

    /**
     * Get column of mysql type: unsigned bigint
     */
    public java.math.BigInteger getBigInteger(String column) {
        return (java.math.BigInteger) getColumns().get(column);
    }

    /**
     * Get column of mysql type: date, year
     */
    public java.util.Date getDate(String column) {
        Object ret = getColumns().get(column);

        if (ret instanceof Temporal) {
            if (ret instanceof LocalDateTime) {
                return DateUtil.date((LocalDateTime) ret);
            }
            if (ret instanceof LocalDate) {
                return DateUtil.date((LocalDate) ret);
            }
            if (ret instanceof LocalTime) {
                return DateUtil.date((LocalTime) ret);
            }
        }

        return (java.util.Date) ret;
    }

    public LocalDateTime getLocalDateTime(String column) {
        Object ret = getColumns().get(column);

        if (ret instanceof LocalDateTime) {
            return (LocalDateTime) ret;
        }
        if (ret instanceof LocalDate) {
            return ((LocalDate) ret).atStartOfDay();
        }
        if (ret instanceof LocalTime) {
            return LocalDateTime.of(LocalDate.now(), (LocalTime) ret);
        }
        if (ret instanceof java.util.Date) {
            return DateUtil.toLocalDateTime((java.util.Date) ret);
        }

        return (LocalDateTime) ret;
    }

    /**
     * Get column of mysql type: time
     */
    public java.sql.Time getTime(String column) {
        return (java.sql.Time) getColumns().get(column);
    }

    /**
     * Get column of mysql type: timestamp, datetime
     */
    public java.sql.Timestamp getTimestamp(String column) {
        return (java.sql.Timestamp) getColumns().get(column);
    }

    /**
     * Get column of mysql type: real, double
     */
    public Double getDouble(String column) {
        Number n = getNumber(column);
        return n != null ? n.doubleValue() : null;
    }

    /**
     * Get column of mysql type: float
     */
    public Float getFloat(String column) {
        Number n = getNumber(column);
        return n != null ? n.floatValue() : null;
    }

    public Short getShort(String column) {
        Number n = getNumber(column);
        return n != null ? n.shortValue() : null;
    }

    public Byte getByte(String column) {
        Number n = getNumber(column);
        return n != null ? n.byteValue() : null;
    }

    /**
     * Get column of mysql type: bit, tinyint(1)
     */
    public Boolean getBoolean(String column) {
        return (Boolean) getColumns().get(column);
    }

    /**
     * Get column of mysql type: decimal, numeric
     */
    public BigDecimal getBigDecimal(String column) {
        Object n = getColumns().get(column);
        if (n instanceof BigDecimal) {
            return (BigDecimal) n;
        } else if (n != null) {
            return new BigDecimal(n.toString());
        } else {
            return null;
        }
    }

    /**
     * Get column of mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob
     * I have not finished the test.
     */
    public byte[] getBytes(String column) {
        return (byte[]) getColumns().get(column);
    }

    public int hashCode() {
        return getColumns().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Record))
            return false;
        if (o == this)
            return true;
        return getColumns().equals(((Record) o).getColumns());
    }

    public String toString() {
        if (columns == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        boolean first = true;
        for (Map.Entry<String, Object> e : getColumns().entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            Object value = e.getValue();
            if (value != null) {
                value = value.toString();
            }
            sb.append(e.getKey()).append(':').append(value);
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Return column names of this record.
     */
    public String[] getColumnNames() {
        Set<String> attrNameSet = getColumns().keySet();
        return attrNameSet.toArray(new String[attrNameSet.size()]);
    }

    /**
     * Return column values of this record.
     */
    public Object[] getColumnValues() {
        java.util.Collection<Object> attrValueCollection = getColumns().values();
        return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
    }

    /**
     * Return json string of this record.
     */
    public String toJson() {
        return JackSonUtil.toJson(getColumns());
    }

}
