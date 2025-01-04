package org.waterwood.io.DataBase;

import java.sql.*;

/**
 * A class to create SQLite connection
 * @since 1.1.0
 * @author Danburen
 */
public abstract class SQLite {
    protected Connection connection;
    public SQLite(String dbFilePath) {
        connection = connectToDatabase(dbFilePath);
    }

    public static Connection connectToDatabase(String dbFilePath) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    protected static void executeCreateSQL(Connection connection,String SQL)throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.execute(SQL);
    }

    protected static void executeUpdateSQL(Connection connection, String SQL)throws SQLException{
        Statement stmt = connection.prepareStatement(SQL);
        stmt.executeUpdate(SQL);
    }

    public void closeConnection() {
        try{
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        }catch (SQLException e){
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
