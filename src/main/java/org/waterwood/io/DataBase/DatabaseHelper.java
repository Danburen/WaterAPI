package org.waterwood.io.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class to handle simple exceptions when sql query occurred errors
 * @since 1.1.0
 * @author Danburen
 */
public class DatabaseHelper {

    public static void executeSQL(Connection conn, String sql,SQLStatementSetter setter) {
        executeSQL(conn, sql, setter, "");
    }

    public static void executeSQL(Connection conn, String sql,SQLStatementSetter setter,SQLResultSetHandler handler) {
        executeSQL(conn, sql, setter, "",handler);
    }

    /**
     * Execute SQL. Auto handle SQL Exception when it occurred.
     * @param conn connection to sql
     * @param sql sql statement
     * @param setter setter for query
     * @param successMessage success message when query success
     * @param errorMessage error message when query throw an error
     */
    public static void executeSQL(Connection conn, String sql,SQLStatementSetter setter,String successMessage,String errorMessage) {
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            setter.setValue(stmt);
            stmt.executeUpdate();
            System.out.println(successMessage);
        }catch (SQLException e) {
            System.out.println("SQL Error: " + errorMessage  + " \nsource: " + e.getMessage());
        }
    }

    /**
     * Execute SQL statement. Auto handle SQL Exception when it occurred.
     * @param conn connection to sql
     * @param sql sql statement
     * @param setter setter for query
     * @param handler the result handler for query
     * @param errorMessage error message when query throw an error
     */
    public static void executeSQL(Connection conn, String sql, SQLStatementSetter setter, String errorMessage, SQLResultSetHandler handler) {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setValue(stmt);
            ResultSet rs = stmt.executeQuery();
            handler.handleResultSet(rs);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + errorMessage + " \nsource: " + e.getMessage());
        }
    }

    public static void executeSQL(Connection conn, String sql,SQLStatementSetter setter,String errorMessage) {
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            setter.setValue(stmt);
            stmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("SQL Error: " + errorMessage  + " \nsource: " + e.getMessage());
        }
    }

    public interface SQLStatementSetter{
        void setValue(PreparedStatement stmt) throws SQLException;
    }

    public interface SQLResultSetHandler {
        void handleResultSet(ResultSet rs) throws SQLException;
    }
}
