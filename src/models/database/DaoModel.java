/**
 * DaoModels.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0
 */

package models.database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoModel {
	//Declare DB objects 
	DBConnectionManager con = null;
	static Statement stmt = null;
	
	/**
	 * CHECK EXISTING TABLE METHOD
	 */
	public static int checkExistingTable(String tableName) {
		try {
			// Open a connection
			System.out.println("Connecting to database...");
			DBConnectionManager.getConnection();
			System.out.println("Connected database successfully...");


			DatabaseMetaData dbmd = DBConnectionManager.getConnection().getMetaData();
			ResultSet tables = dbmd.getTables(null, null, tableName, null);

			// Check if "tableName" table is already there
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
	 * CREATE TABLE HEIGHTMAP_PARAMETERS METHOD
	 * This function perform the creation of the first table about the map.
	 */
	public static void createTableMapParameters() {
		try {

			System.out.println("Creating tables in given database...");
			stmt = DBConnectionManager.getConnection().createStatement();
			// First table for the map parameters
			String sql = "CREATE TABLE HEIGHTMAP_PARAMETERS (" + 
					"Map_id INTEGER not NULL AUTO_INCREMENT, " + 
					"Algorithm_name VARCHAR(50), " +
					"Height BIGINT(18), " + 
					"Width BIGINT(18), " +
					"Map_parameters JSON, " +
					"Dt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
					"PRIMARY KEY (Map_id))";
			// Execute create query
			stmt.executeUpdate(sql);
			System.out.println("First Table created successfully...");

			// Close database connection 
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	/**
	 * CREATE TABLE HEIGHTMAP_STATISTICS METHOD
	 * This function perform the creation of the second table about the map.
	 */
	public static void createTableMapStatistics() {
		try {

			System.out.println("Creating tables in given database...");
			stmt = DBConnectionManager.getConnection().createStatement();
			
			// Second table for the map statistics
			String sql = "CREATE TABLE HEIGHTMAP_STATISTICS (" + 
					"Stat_id INTEGER not NULL AUTO_INCREMENT, " + 
					"Algorithm_name VARCHAR(50), " +
					"Max_value SMALLINT(4) UNSIGNED, " + 
					"Min_value SMALLINT(4) UNSIGNED, " +
					"Average_value DOUBLE(6,2), " +
					"Median_value SMALLINT(4) UNSIGNED, " +
					"Height_histogram JSON, " +
					"Dt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
					"PRIMARY KEY (Stat_id))" +
					"FOREIGN KEY(Map_id) REFERENCES HEIGHTMAP_PARAMETERS(Map_id))";
			// Execute create query
			stmt.executeUpdate(sql);
			System.out.println("Second Table created successfully...");

			// Close database connection 
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	/**
	 * CREATE TABLE LICENSE METHOD
	 * This function perform the creation of the table for the license numbers.
	 */
	public static void createTableLicenses() {
		try {
			System.out.println("Creating licenses table in given database...");
			stmt = DBConnectionManager.getConnection().createStatement();

			// Execute create query
			String sql = "CREATE TABLE LICENSES (" + 
					"License_id INTEGER not NULL AUTO_INCREMENT, " + 
					"License_number VARCHAR(30), " +
					"License_type VARCHAR(50), " +
					"Authorized_use_time VARCHAR(50) " +
					"PRIMARY KEY (License_id))";
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully...");
			
			// Close database connection 
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	public static void insertTableLicenses() {
		try {
			System.out.println("Creating licenses table in given database...");
			stmt = DBConnectionManager.getConnection().createStatement();	
			// Execute inserting queries
			String sql = "INSERT INTO LICENSES(License_number, License_type, Authorized_use_time) " + 
							"VALUES ('5483-1890-4832-0233', 'administrator', 'unlimited'),"
							+ "('1234-1234-1234-1234', 'user', '1')," // 1 hour license
							+ "('1234-5678-9101-1121', 'user', '24')," // 24 hour license
							+ "('3242-5262-7282-9303', 'user', '168')," // 1 week license
							+ "('1323-3343-5363-7383', 'user', '720')," // 1 month license
							+ "('9405-0515-2535-4555', 'user', '2160')," // 3 months license
							+ "('6061-7989-5608-8870', 'user', '4320')," // 6 months license
							+ "('6961-3289-5028-1266', 'user', '8640')"; // 1 year license
			stmt.executeUpdate(sql);
			System.out.println("License numbers inserted in table successfully...");
			
			// Close database connection 
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	/**
	 * INSERT INTO METHOD
	 * This method perform the insertion of data into the two tables.
	 */
	// TODO JSON mapParameters, JSON heightHistogram;
	public static void insertTablesMap(String algorithmeName, String height, String width, 	String maxValue, String minValue, String averageValue, String medianValue) {
		try {
			System.out.println("Inserting data into the tables...");
			stmt = DBConnectionManager.getConnection().createStatement();
			// Execute a query
			String sql = 
					"INSERT INTO HEIGHTMAP_PARAMETERS(Algorithm_name, Height, Width) " + 
							"VALUES (' "+algorithmeName+" ', ' "+height+" ', ' "+width+" ')";
			stmt.executeUpdate(sql);
			System.out.println("Data inserted in the first table successfully...");
			// Execute a query
			sql = 
					"INSERT INTO HEIGHTMAP_STATISTICS(Algorithm_name, Max_value, Min_value, Average_value, Median_value) " + 
							"VALUES (' "+algorithmeName+" ', ' "+maxValue+" ', ' "+minValue+" ', ' "+averageValue+" ', ' "+medianValue+" ')";
			stmt.executeUpdate(sql);
			System.out.println("Data inserted in the second table successfully...");
			// Close database connection
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { 
			se.printStackTrace();  
		}
	}

	/**
	 * DELETE MAP METHOD
	 * This function allow the user to delete the last generated map from the database
	 */
	public static void deleteLastMap() {
		try {

			// Execute delete query
			System.out.println("Deleting map in given database...");
			// Database connection
			stmt = DBConnectionManager.getConnection().createStatement();
			// Last map deletion
			String sql = "DELETE FROM HEIGHTMAP_PARAMETERS WHERE Map_id=(SELECT MAX(Map_id) FROM HEIGHTMAP_PARAMETERS)";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM HEIGHTMAP_STATISTICS WHERE Map_id=(SELECT MAX(Map_id) FROM HEIGHTMAP_STATISTICS)";
			stmt.executeUpdate(sql);
			System.out.println("Last Map deleted successfully...");
			
			DBConnectionManager.getConnection().close(); // Close database connection 
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	/**
	 * RETRIEVE DATA METHOD
	 * This method will return a ResultSet object used for creating output. 
	 * The result set contains data including the algorithm name, the height and width of the map, but also the map parameters.
	 */ 
	public ResultSet retrieveMapParameters(String mapId) {
		ResultSet rs = null;
		try {
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "SELECT Algorithm_name, Height, Width, Map_parameters FROM HEIGHTMAP_PARAMETERS WHERE Map_id="+mapId;
			rs = stmt.executeQuery(sql);
			DBConnectionManager.getConnection().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return rs;
	}
	/**
	 * RETRIEVE FULL TABLES METHOD
	 * This function all the data from the two first table in order to display
	 */
	public ResultSet retrieveFullMapParameters() {
		ResultSet rs = null;

		try {
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "SELECT Map_id, Algorithm_name, Height, Width, Map_parameters, Dt FROM HEIGHTMAP_PARAMETERS";
			rs = stmt.executeQuery(sql);
			
			DBConnectionManager.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet retrieveFullMapStatistics() {
		ResultSet rs = null;

		try {
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "SELECT Stat_id, Algorithm_name, Max_value, Min_value, Average_value, Median_value, Height_histogram, Dt FROM HEIGHTMAP_STATISTICS";
			rs = stmt.executeQuery(sql);
			
			DBConnectionManager.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ResultSet retrieveLicense(String licenseNumber) {
		ResultSet rs = null;
		try {
			// Database connection
			stmt = DBConnectionManager.getConnection().createStatement();
			// Execute retrieve query
			String sql = "SELECT License_number, License_type, Authorized_use_time FROM LICENSES WHERE License_number="+licenseNumber;
			rs = stmt.executeQuery(sql);
			// Close database connection
			DBConnectionManager.getConnection().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return rs;
	}

	/**
	 * This function delete the table HEIGHTMAP_PARAMETERS from the database
	 */
	public static void deleteTableMapParameters() {
		try {

			// Execute delete query
			System.out.println("Deleting table in given database...");
			// Database connection
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "DELETE FROM HEIGHTMAP_PARAMETERS";
			stmt.executeUpdate(sql);
			System.out.println("Table deleted successfully...");
			// Close database connection 
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	/**
	 * This function delete the table HEIGHTMAP_STATISTICS from the database
	 */
	public static void deleteTableMapStatistics() {
		try {

			// Execute delete query
			System.out.println("Deleting table in given database...");
			// Database connection
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "DELETE FROM HEIGHTMAP_STATISTICS";
			stmt.executeUpdate(sql);
			System.out.println("Table deleted successfully...");
			// Close database connection 
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	/**
	 * This function delete the table HEIGHTMAP_PARAMETERS from the database
	 */
	public static void deleteTableLicenses() {
		try {

			// Execute delete query
			System.out.println("Deleting table in given database...");
			// Database connection
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "DELETE FROM LICENSES";
			stmt.executeUpdate(sql);
			System.out.println("Table deleted successfully...");
			// Close database connection 
			DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}
}
