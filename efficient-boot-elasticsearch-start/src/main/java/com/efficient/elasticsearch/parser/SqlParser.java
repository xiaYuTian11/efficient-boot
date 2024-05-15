package com.efficient.elasticsearch.parser;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLOrderingSpecification;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBetweenExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.efficient.elasticsearch.properties.ElasticSearchProperties;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 将sql 转换成 es 对应的 dsl语法，目前只支持传统的sql语句，特殊语法无效；
 *
 * @author TMW
 * @since 2021/7/27 15:07
 */
public class SqlParser {
    private final static String DB_TYPE = "postgresql";
    private final String DOT = ".";
    private final String QUOTES = "\"";
    private ElasticSearchProperties properties;

    public SqlParser(ElasticSearchProperties properties) {
        this.properties = properties;
    }

    /**
     * 将SQL解析为ES查询
     *
     * @param sql sql 语句
     * @return SearchSourceBuilder
     */
    public SearchSourceBuilder parse(String sql) throws Exception {
        if (Objects.isNull(sql)) {
            throw new IllegalArgumentException("输入 SQL 语句不能为空");
        }
        sql = sql.trim().toLowerCase();
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, DB_TYPE);
        if (stmtList.size() != 1) {
            throw new IllegalArgumentException("必须输入一句完整的查询语句");
        }
        // 使用 Parser 解析生成 abstract syntax tree
        SQLStatement stmt = stmtList.get(0);
        if (!(stmt instanceof SQLSelectStatement)) {
            throw new IllegalArgumentException("输入语句须为Select语句");
        }
        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) stmt;
        SQLSelectQuery sqlSelectQuery = sqlSelectStatement.getSelect().getQuery();
        SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectQuery;

        SQLExpr whereExpr = sqlSelectQueryBlock.getWhere();
        // 生成ES查询条件
        BoolQueryBuilder bridge = QueryBuilders.boolQuery();
        // 处理where
        QueryBuilder whereBuilder = whereHelper(whereExpr);
        bridge.must(whereBuilder);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 处理order by
        SQLOrderBy orderByExpr = sqlSelectQueryBlock.getOrderBy();
        if (Objects.nonNull(orderByExpr)) {
            orderByHelper(orderByExpr, builder);
        }
        builder.query(bridge);
        return builder;
    }

    /**
     * 处理所有order by字段
     *
     * @param orderByExpr 排序表达式
     */
    private void orderByHelper(SQLOrderBy orderByExpr, SearchSourceBuilder builder) {
        // 待排序的列
        List<SQLSelectOrderByItem> orderByList = orderByExpr.getItems();
        for (SQLSelectOrderByItem sqlSelectOrderByItem : orderByList) {
            if (sqlSelectOrderByItem.getType() == null) {
                // 默认升序
                sqlSelectOrderByItem.setType(SQLOrderingSpecification.ASC);
            }
            String orderByColumn = sqlSelectOrderByItem.getExpr().toString();
            builder.sort(orderByColumn,
                    sqlSelectOrderByItem.getType().equals(SQLOrderingSpecification.ASC) ? SortOrder.ASC
                            : SortOrder.DESC);
        }
    }

    /**
     * 递归遍历“where”子树
     *
     * @param expr 条件表达式
     * @return QueryBuilder 查询条件
     */
    private QueryBuilder whereHelper(SQLExpr expr) throws Exception {
        BoolQueryBuilder bridge = QueryBuilders.boolQuery();
        if (Objects.isNull(expr)) {
            return bridge;
            // throw new NullPointerException("节点不能为空!");
        }

        // 二元运算
        if (expr instanceof SQLBinaryOpExpr) {
            // 获取运算符
            SQLBinaryOperator operator = ((SQLBinaryOpExpr) expr).getOperator();
            if (operator.isLogical()) {
                // 逻辑运算 and,or,xor
                return handleLogicalExpr(expr);
            } else if (operator.isRelational()) {
                // 具体的关系运算,位于叶子节点
                return handleRelationalExpr(expr);
            }
        } else if (expr instanceof SQLBetweenExpr) {
            // between运算
            SQLBetweenExpr between = ((SQLBetweenExpr) expr);
            // between or not between ?
            boolean isNotBetween = between.isNot();
            String testExpr = formatSqlField(between.testExpr.toString());
            Object fromStr = formatSqlValue(between.beginExpr.toString());
            Object toStr = formatSqlValue(between.endExpr.toString());
            if (isNotBetween) {
                bridge.must(QueryBuilders.rangeQuery(testExpr).lt(fromStr).gt(toStr));
            } else {
                bridge.must(QueryBuilders.rangeQuery(testExpr).gte(fromStr).lte(toStr));
            }
            return bridge;
        } else if (expr instanceof SQLInListExpr) {
            // SQL的 in语句，ES中对应的是terms
            SQLInListExpr siExpr = (SQLInListExpr) expr;
            // in or not in?
            boolean isNotIn = siExpr.isNot();
            String leftSide = formatSqlField(siExpr.getExpr().toString());
            List<SQLExpr> inSqlList = siExpr.getTargetList();
            List<Object> inList = new ArrayList<>();
            for (SQLExpr in : inSqlList) {
                Object str = formatSqlValue(in.toString());
                inList.add(str);
            }
            if (isNotIn) {
                bridge.mustNot(QueryBuilders.termsQuery(leftSide, inList));
            } else {
                bridge.must(QueryBuilders.termsQuery(leftSide, inList));
            }
            return bridge;
        }
        return bridge;
    }

    /**
     * 逻辑运算符，目前支持and,or
     *
     * @param expr 条件表达式
     * @return QueryBuilder 查询条件
     */
    private QueryBuilder handleLogicalExpr(SQLExpr expr) throws Exception {
        BoolQueryBuilder bridge = QueryBuilders.boolQuery();
        // 获取运算符
        SQLBinaryOperator operator = ((SQLBinaryOpExpr) expr).getOperator();
        SQLExpr leftExpr = ((SQLBinaryOpExpr) expr).getLeft();
        SQLExpr rightExpr = ((SQLBinaryOpExpr) expr).getRight();

        // 分别递归左右子树，再根据逻辑运算符将结果归并
        QueryBuilder leftBridge = whereHelper(leftExpr);
        QueryBuilder rightBridge = whereHelper(rightExpr);
        if (operator.equals(SQLBinaryOperator.BooleanAnd)) {
            bridge.must(leftBridge).must(rightBridge);
        } else if (operator.equals(SQLBinaryOperator.BooleanOr)) {
            bridge.should(leftBridge).should(rightBridge);
        }
        return bridge;
    }

    /**
     * 大于小于等于正则
     *
     * @param expr 条件表达式
     * @return QueryBuilder 查询条件
     */
    private QueryBuilder handleRelationalExpr(SQLExpr expr) {
        SQLExpr leftExpr = ((SQLBinaryOpExpr) expr).getLeft();
        if (Objects.isNull(leftExpr)) {
            throw new NullPointerException("表达式左侧不得为空");
        }
        String leftExprStr = formatSqlField(leftExpr.toString());
        Object rightExprStr = formatSqlValue(((SQLBinaryOpExpr) expr).getRight().toString());
        // 获取运算符
        SQLBinaryOperator operator = ((SQLBinaryOpExpr) expr).getOperator();
        QueryBuilder queryBuilder;
        String valueOf;
        WildcardQueryBuilder wildcardQuery;
        switch (operator) {
            case GreaterThanOrEqual:
                queryBuilder = QueryBuilders.rangeQuery(leftExprStr).gte(rightExprStr);
                break;
            case LessThanOrEqual:
                queryBuilder = QueryBuilders.rangeQuery(leftExprStr).lte(rightExprStr);
                break;
            case Equality:
                queryBuilder = QueryBuilders.boolQuery();
                TermQueryBuilder eqCond = QueryBuilders.termQuery(leftExprStr, rightExprStr);
                ((BoolQueryBuilder) queryBuilder).must(eqCond);
                break;
            case GreaterThan:
                queryBuilder = QueryBuilders.rangeQuery(leftExprStr).gt(rightExprStr);
                break;
            case LessThan:
                queryBuilder = QueryBuilders.rangeQuery(leftExprStr).lt(rightExprStr);
                break;
            case LessThanOrGreater:
                // wildcardQuery 条件判断
                // BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                // List<QueryBuilder> must = boolQueryBuilder.must();
                // //星号表示所有，即：不为空的字段
                // must.add(QueryBuilders.wildcardQuery("字段名称", "*"));
                queryBuilder = QueryBuilders.boolQuery();
                TermQueryBuilder notEq = QueryBuilders.termQuery(leftExprStr, rightExprStr);
                ((BoolQueryBuilder) queryBuilder).mustNot(notEq);
                break;
            case NotEqual:
                queryBuilder = QueryBuilders.boolQuery();
                if (StrUtil.isBlank(String.valueOf(rightExprStr))) {
                    ((BoolQueryBuilder) queryBuilder).must(QueryBuilders.wildcardQuery(leftExprStr, "*"));
                } else {
                    TermQueryBuilder notEqCond = QueryBuilders.termQuery(leftExprStr, rightExprStr);
                    ((BoolQueryBuilder) queryBuilder).mustNot(notEqCond);
                }
                break;
            case RegExp:
                queryBuilder = QueryBuilders.boolQuery();
                RegexpQueryBuilder regCond = QueryBuilders.regexpQuery(leftExprStr, String.valueOf(rightExprStr));
                ((BoolQueryBuilder) queryBuilder).mustNot(regCond);
                break;
            case NotRegExp:
                queryBuilder = QueryBuilders.boolQuery();
                RegexpQueryBuilder notRegCond = QueryBuilders.regexpQuery(leftExprStr, String.valueOf(rightExprStr));
                ((BoolQueryBuilder) queryBuilder).mustNot(notRegCond);
                break;
            case Like:
                valueOf = String.valueOf(rightExprStr).replace("%", "*");
                queryBuilder = QueryBuilders.boolQuery();
                wildcardQuery = QueryBuilders.wildcardQuery(leftExprStr, valueOf);
                ((BoolQueryBuilder) queryBuilder).must(wildcardQuery);
                break;
            case NotLike:
                valueOf = String.valueOf(rightExprStr).replace("%", "*");
                queryBuilder = QueryBuilders.boolQuery();
                wildcardQuery = QueryBuilders.wildcardQuery(leftExprStr, valueOf);
                ((BoolQueryBuilder) queryBuilder).mustNot(wildcardQuery);
                break;
            case Is:
                queryBuilder = QueryBuilders.boolQuery();
                ((BoolQueryBuilder) queryBuilder).mustNot(QueryBuilders.existsQuery(leftExprStr));
                break;
            case IsNot:
                queryBuilder = QueryBuilders.boolQuery();
                ((BoolQueryBuilder) queryBuilder).must(QueryBuilders.existsQuery(leftExprStr));
                break;
            default:
                throw new IllegalArgumentException("暂不支持该运算符!" + operator);
        }
        return queryBuilder;
    }

    /**
     * 格式化 sql 值
     *
     * @param sqlExprStr 格式化前字符
     * @return 格式化后字符
     */
    private Object formatSqlValue(String sqlExprStr) {
        if (StrUtil.isNotBlank(sqlExprStr)) {
            String replaceAll = sqlExprStr.replaceAll("'", "");
            if (StrUtil.isBlank(replaceAll)) {
                return "";
            }
            if (replaceAll.contains("TIMESTAMP")) {
                replaceAll = replaceAll.replaceAll("TIMESTAMP", "").trim();
                if (properties.isDateToTimestamp()) {
                    return DateUtil.parse(replaceAll, "yyyy-MM-dd HH:mm:ss.S").getTime();
                }
                return replaceAll;
            }
            return replaceAll;
        }
        return sqlExprStr;
    }

    /**
     * 格式化 sql 字段
     *
     * @param sqlExprStr 格式化前字符
     * @return 格式化后字符
     */
    private String formatSqlField(String sqlExprStr) {
        if (StrUtil.isNotBlank(sqlExprStr)) {
            if (sqlExprStr.contains(QUOTES)) {
                sqlExprStr = sqlExprStr.replaceAll("\"", "");
            }
            if (sqlExprStr.contains(DOT)) {
                final String[] splits = sqlExprStr.split("\\.");
                if (splits.length == 2) {
                    return splits[1];
                } else {
                    return sqlExprStr;
                }
            }
            return sqlExprStr;
        }
        return sqlExprStr;
    }
}
