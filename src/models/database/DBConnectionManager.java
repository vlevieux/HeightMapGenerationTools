/**
 * DBConnect.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package models.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
	
	private static String driverName = "com.mysql.jdbc.Driver";
	private static Connection con;
	// Code database URL
	private static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/510labs?autoReconnect=true&useSSL=false";
	// Database credentials
	private static final String USERNAME = ""; 
	private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            } catch (SQLException ex) {
            	System.out.println("Failed to create the database connection."); 
            	ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found."); 
            ex.printStackTrace();
        }
        return con;
    }
}
