package org.waterwood.io.database.Builder;

import java.util.ArrayList;
import java.util.List;

public class SQLiteInsertBuilder {
    private String tableName;
    private List<String> columns;
    private List<String> values;
    private StringBuilder sql;

    public SQLiteInsertBuilder(String tableName) {
        this.tableName = tableName;
        this.columns = new ArrayList<String>();
        this.values = new ArrayList<>();
        this.sql = new StringBuilder();
    }

    //Insert column and value
    public <T>SQLiteInsertBuilder addColumn(String column,T value) {
        this.columns.add(column);
        if(value instanceof String){
            this.values.add("'" + value.toString() + "'");
        }else{
            this.values.add(value.toString());
        }
        return this;
    }

    public String build() {
        sql.append("INSERT OR REPLACE INTO ").append(tableName).append(" (");

        for (String column : columns) {
            sql.append(column).append(", ");
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(") VALUES (");

        for (String value : values) {
            sql.append(value).append(", ");
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(");");

        return sql.toString();
    }
}