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
	
	private static String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
	private static Connection con;

	/*
	 * Method to established connection with the database.
	 */
    public static Connection getConnection() {
        try {
        	Class.forName(driverName);
            try {
    			con = DriverManager.getConnection("jdbc:derby:heightmapdb;create=true");
                //con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
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
