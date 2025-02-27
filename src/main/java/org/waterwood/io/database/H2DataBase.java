package org.waterwood.io.database;

import java.sql.*;

/**
 * A class to create H2DataBase connection
 * @since 1.1.0
 * @author Danburen
 */
public abstract class H2DataBase {
    protected Connection connection;
    public H2DataBase(String dbFilePath) {
        connection = connectToDatabase(dbFilePath);
    }

    public static Connection connectToDatabase(String dbFilePath) {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:file:" + dbFilePath,"sa","");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
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
