/**
 * DaoModels.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0
 */

package models.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DaoModel {
	//Declare DB objects 
	DBConnectionManager con = null;
	Statement stmt = null;
	
	// Declare data fields to insert in our database
	String algorithmName = "";
	long height;
	long width;
	// JSON mapParameters;
	int maxValue;
	int minValue;
	int averageValue;
	int medianValue;
	// JSON heightHistogram;

	/**
	 * CHECK EXISTING TABLE METHOD
	 */
	public int checkExistingTable(String tableName) {
		try {

			// Open a connection
			System.out.println("Connecting to database...");
			DBConnectionManager.getConnection();
			System.out.println("Connected database successfully...");


			DatabaseMetaData dbmd = DBConnectionManager.getConnection().getMetaData();
			ResultSet tables = dbmd.getTables(null, null, tableName, null);

			// check if "tableName" table is already there
			if (tables.next()) {
				// Table exist
				System.out.println("The table already exists...");
				stmt = DBConnectionManager.getConnection().createStatement();
				String sql = "SELECT COUNT(*) FROM "+tableName;
				ResultSet rs = stmt.executeQuery(sql);	
				int rows = 0;
				while(rs.next()){
					rows = rs.getInt("count(*)");
				}
				if(rows == 0) {
					// Table exist but is empty
					System.out.println("The table is empty.");
					DBConnectionManager.getConnection().close(); // Close database connection
					return 2;
				}
				else {
					// Table exist and is not empty
					System.out.println("The table is not empty.");
					System.out.println("There are "+rows+" rows in your table.");
					DBConnectionManager.getConnection().close(); // Close database connection
					return 3;
				}
			}
			else {
				// Table does not exist
				System.out.println("There is no table called "+ tableName+ "in this database.");
				DBConnectionManager.getConnection().close(); // Close database connection 
				return 1;
			}
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
		return 0;
	}

	/**
	 * CREATE TABLES METHOD
	 * This function perform the creation of the two tables this application need.
	 */
	public void createTables() {
		try {

			// Execute create query
			System.out.println("Creating table in given database...");
			stmt = DBConnectionManager.getConnection().createStatement();

			String sql = "CREATE TABLE HEIGHTMAP_PARAMETERS (" + 
					"Map_id INTEGER not NULL AUTO_INCREMENT, " + 
					"Algorithm_name VARCHAR(50), " +
					"Height BIGINT(18), " + 
					"Width BIGINT(18), " +
					"Map_parameters JSON, " +
					"Dt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
					"PRIMARY KEY (pid))";
			stmt.executeUpdate(sql);
			System.out.println("First Table created successfully...");

			sql = "CREATE TABLE HEIGHTMAP_STATISTICS (" + 
					"Stat_id INTEGER not NULL AUTO_INCREMENT, " + 
					"Algorithm_name VARCHAR(50), " +
					"Max_value SMALLINT(4) UNSIGNED, " + 
					"Min_value SMALLINT(4) UNSIGNED, " +
					"Average_value FLOAT(6,2), " +
					"Median_value FLOAT(6,2), " +
					"Height_histogram JSON, " +
					"Dt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
					"PRIMARY KEY (pid))" +
					"FOREIGN KEY(Map_id) REFERENCES HEIGHTMAP_PARAMETERS(Map_id))";
			stmt.executeUpdate(sql);
			System.out.println("Second Table created successfully...");

			DBConnectionManager.getConnection().close(); //close db connection 
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	/**
	 * INSERT INTO METHOD
	 * This method perform the insertion of data into the two tables.
	 */
	public void insertTables() {
		try {
			// Execute a query
			System.out.println("Inserting data into the tables...");
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = 
					"INSERT INTO HEIGHTMAP_PARAMETERS(Algorithm_name, Height, Width, Map_parameters) " + 
							"VALUES (' "+algorithmName+" ', ' "+height+" ', ' "+width+" ', ' "+mapParameters+" ')";
			stmt.executeUpdate(sql);
			System.out.println("Data inserted in the first table successfully...");

			sql = 
					"INSERT INTO HEIGHTMAP_STATISTICS(Algorithm_name, Max_value, Min_value, Average_value, Median_value, Height_histogram) " + 
							"VALUES (' "+algorithmName+" ', ' "+maxValue+" ', ' "+minValue+" ', ' "+averageValue+" ', ' "+medianValue+" ', ' "+heightHistogram+" ')";
			stmt.executeUpdate(sql);
			System.out.println("Data inserted in the second table successfully...");
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { 
			se.printStackTrace();  
		}
	}

	/**
	 * RETRIEVE DATA METHOD
	 * This method will return a ResultSet object used for creating output. 
	 * The result set contains data including the algorithm name, the height and width of the map, but also the map parameters.
	 */ 
	public ResultSet retrieveData(String mapId) {
		ResultSet rs = null;
		try {
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "SELECT Algorithm_name, Height, Width, Map_parameters from HEIGHTMAP_PARAMETERS WHERE Map_id="+mapId;
			rs = stmt.executeQuery(sql);
			DBConnectionManager.getConnection().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * DELETE MAP METHOD
	 * This function allow the user to delete the last generated map from the database
	 */
	public void deleteLastMap() {
		try {

			// Execute delete query
			System.out.println("Deleting map in given database...");
			// Database connection
			stmt = DBConnectionManager.getConnection().createStatement();
			// Last map deletion
			String sql = "DELETE FROM HEIGHTMAP_PARAMETERS WHERE Map_id=LAST_INSERT_ID()";
			stmt.executeUpdate(sql);
			System.out.println("Last Map deleted successfully...");
			
			DBConnectionManager.getConnection().close(); // Close database connection 
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	/**
	 * DELETE TABLES METHOD
	 * This function delete the two tables from the database
	 */
	public void deleteTables() {
		try {

			// Execute delete query
			System.out.println("Deleting tables in given database...");
			// Database connection
			stmt = DBConnectionManager.getConnection().createStatement();
			// First Table
			String sql = "DELETE FROM HEIGHTMAP_PARAMETERS";
			stmt.executeUpdate(sql);
			System.out.println("First Table deleted successfully...");
			// Second Table
			sql = "DELETE FROM HEIGHTMAP_STATISTICS";
			stmt.executeUpdate(sql);
			System.out.println("Second Table deleted successfully...");
			
			DBConnectionManager.getConnection().close(); // Close database connection 
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

}
