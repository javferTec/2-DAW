package com.fpmislata.basespring.persistence.common;

import org.springframework.stereotype.Component;

@Component
public class SQLQueryGenerator {

    public static String getAll(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    public static String count(String tableName) {
        return "SELECT COUNT(*) FROM " + tableName;
    }

    public static String findByColumn(String tableName, String columnName) {
        return "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
    }

    public static String findWithJoins(String tableName, String[][] joins, String conditionColumn) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
        for (String[] join : joins) {
            sql.append(" LEFT JOIN ").append(join[0]).append(" ON ").append(join[1]).append(" = ").append(join[2]);
        }
        sql.append(" WHERE ").append(conditionColumn).append(" = ?");
        return sql.toString();
    }

    public static String paginatedQuery(String baseQuery, int page, int size) {
        return baseQuery + " LIMIT " + size + " OFFSET " + (page * size);
    }
}