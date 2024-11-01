package com.fpmislata.basespring.persistence.repository.impl.jdbc.common;


import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SqlBuilder {

    public String getAll(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    public String count(String tableName) {
        return "SELECT COUNT(*) FROM " + tableName;
    }

    public String findByColumn(String tableName, String columnName) {
        return "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
    }

    public String findWithJoins(String tableName, String[][] joins, String conditionColumn) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
        for (String[] join : joins) {
            sql.append(" LEFT JOIN ").append(join[0]).append(" ON ").append(join[1]).append(" = ").append(join[2]);
        }
        sql.append(" WHERE ").append(conditionColumn).append(" = ?");
        return sql.toString();
    }

    public String paginatedQuery(String baseQuery, int page, int size) {
        return baseQuery + " LIMIT " + size + " OFFSET " + (page * size);
    }

    public String insert(String tableName, String[] columns) {
        String columnList = String.join(", ", columns);
        String valuePlaceholders = IntStream.range(0, columns.length)
                .mapToObj(i -> "?")
                .collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columnList, valuePlaceholders);
    }

    public String update(String tableName, String[] columns, String conditionColumn) {
        String setClause = IntStream.range(0, columns.length)
                .mapToObj(i -> columns[i] + " = ?")
                .collect(Collectors.joining(", "));

        return String.format("UPDATE %s SET %s WHERE %s = ?", tableName, setClause, conditionColumn);
    }

    public String delete(String tableName, String conditionColumn) {
        return "DELETE FROM " + tableName + " WHERE " + conditionColumn + " = ?";
    }

    public String deleteAll(String tableName) {
        return "DELETE FROM " + tableName;
    }

    public String insertManyToMany(String booksGenres, String bookId, String genreId) {
        return "INSERT INTO " + booksGenres + " (" + bookId + ", " + genreId + ") VALUES (?, ?)";
    }

    public String findByRelation(
            String mainTable,
            String joinTable,
            String relatedTable,
            String mainIdentifierColumn,
            String relatedIdentifierColumn) {

        return String.format("""
                        SELECT %s.* FROM %s
                        JOIN %s ON %s.id = %s.%s
                        WHERE %s.%s = ?
                        """,
                mainTable, mainTable, joinTable, mainTable, joinTable,
                relatedIdentifierColumn, joinTable, mainIdentifierColumn);
    }
}