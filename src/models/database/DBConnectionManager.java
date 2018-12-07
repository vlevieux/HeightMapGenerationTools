/**
 * DBConnect.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package models.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;

public class DBConnectionManager {
	
	private static String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
	private static Connection con;
	private static int tries = 0;

	/*
	 * Method to established connection with the database.
	 */
    public static Connection getConnection() {
        try {
        	Class.forName(driverName);
            try {
    			con = DriverManager.getConnection("jdbc:derby:heightmapdb;create=true");
            } catch (SQLException ex) {
            	System.out.println("Database Corrupted");
            	Path rootPath = Paths.get("heightmapdb").toAbsolutePath();
            	tries+=1;
            	System.out.println("Trying again, tries : "+tries);
            	if (tries<=2) {
	            	try {
	            		if (Files.exists(rootPath)) {
							Files.walk(rootPath)
								.sorted(Comparator.reverseOrder())
								.map(Path::toFile)
								.peek(System.out::println)
								.forEach(File::delete);
	            		} else {
	            			System.out.println("Any Files");
	            		}
						con = DriverManager.getConnection("jdbc:derby:heightmapdb;create=true");
					} catch (IOException | SQLException e) {
						e.printStackTrace();
					}
            	} else {
            		System.out.println("Impossible to create database");
            		System.exit(-1);
            	}
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found."); 
            ex.printStackTrace();
        }
        return con;
    }
}
