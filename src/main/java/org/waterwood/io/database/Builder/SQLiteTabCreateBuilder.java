package org.waterwood.io.database.Builder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteTabCreateBuilder {
    private String tableName;
    private List<String> columns;
    private List<String> primaryKeys;
    private StringBuilder sql;

    public SQLiteTabCreateBuilder(String tableName, List<String> columns, List<String> primaryKeys) {
        this.tableName = tableName;
        this.columns = new ArrayList<>();
        this.primaryKeys = new ArrayList<>();
        this.sql = new StringBuilder();
    }

    public SQLiteTabCreateBuilder addColumn(String column) {
        this.columns.add(column);
        return this;
    }

    /**
     * Add primary Keys
     * Support union primary key
     * @param keys primary Keys to add
     * @return added SQLiteTabCreateBuilder
     */
    public SQLiteTabCreateBuilder addPrimaryKey(String... keys){
        StringBuilder pk = new StringBuilder("PRIMARY KEY (");
        for(String key : keys){
            pk.append(key).append(", ");
        }
        pk.delete(pk.length() - 2, pk.length());  // 删除最后的逗号
        pk.append(")");
        primaryKeys.add(pk.toString());
        return this;
    }

    // Add constraints (e.g. NOT NULL, UNIQUE, DEFAULT)
    public SQLiteTabCreateBuilder addConstraint(String columnName, String constraint) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).startsWith(columnName)) {
                columns.set(i, columns.get(i) + " " + constraint);
            }
        }
        return this;
    }

    // build the full sql
    public String build() {
        sql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");
        for (String column : columns) {
            sql.append(column).append(", ");
        }
        if (!primaryKeys.isEmpty()) {
            sql.append(primaryKeys.get(0)).append(", ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(");");
        return sql.toString();
    }
}
