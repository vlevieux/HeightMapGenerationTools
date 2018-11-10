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

	/**
	 * CHECK EXISTING TABLE METHOD
	 * 
	 * @param tableName
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
				ResultSet rs =stmt.executeQuery(sql);	
				int rows =0;
				while(rs.next()){
					rows = rs.getInt("count(*)");
				}
				if(rows==0) {
					System.out.println("The table is empty.");
				}
				else {
					System.out.println("The table is not empty.");
					System.out.println("There are "+rows+" rows in your table.");
				}
			}
			else {
				// Table does not exist
				System.out.println("There is no table called "+ tableName+ "in this database.");
			}
			DBConnectionManager.getConnection().close(); //close db connection 
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
		return 0;
	}// CHECK EXISTING TABLE METHOD

	/**
	 * CREATE TABLE METHOD
	 */
	public void createTable(String tableName) {
		try {

			// Execute create query
			System.out.println("Creating table in given database...");
			stmt = DBConnectionManager.getConnection().createStatement();

			String sql = "CREATE TABLE "+tableName+" (" + 
					"pid INTEGER not NULL AUTO_INCREMENT, " + 
					"id VARCHAR(10), " +
					"income numeric(8,2), " + 
					"pep VARCHAR(4), " + 
					"PRIMARY KEY (pid))";
			stmt.executeUpdate(sql);
			System.out.println("Table created successfully...");
			DBConnectionManager.getConnection().close(); //close db connection 
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}// CREATE TABLE METHOD

	/**
	 * INSERT INTO METHOD
	 * This method allow for the insertion of
	 * 
	 * @param tableName
	 * @param 
	 */
	public void insertImage(String tableName) {
		try {
			// Execute a query
			System.out.println("Inserting image into the table...");
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = 
					"INSERT INTO "+tableName+"(image, parameters) " + 
					"VALUES (' "+image+" ', ' "+parameters+" ')";
			stmt.executeUpdate(sql);
		System.out.println("Image inserted successfully...");
		DBConnectionManager.getConnection().close();
		}
		catch (SQLException se) { 
			se.printStackTrace();  
		}
	}// INSERT INTO METHOD

	/**
	 * This method will return a ResultSet object used for creating output. 
	 * The result set contains record data including your id, income and pep table fields.
	 * 
	 *  @param tableName
	 */ 
	public ResultSet retrieveImages(String tableName) {
		ResultSet rs = null;
		try {
			stmt = DBConnectionManager.getConnection().createStatement();
			String sql = "SELECT image, parameters from "+tableName+" order by pid desc";
			rs = stmt.executeQuery(sql);
			DBConnectionManager.getConnection().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return rs;
	}

	/**
	 * DELETE TABLE METHOD
	 * 
	 * @param tableName
	 */
	public void deleteTable(String tableName) {
		try {

			// Execute delete query
			System.out.println("Deleting table in given database...");

			stmt = DBConnectionManager.getConnection().createStatement();

			String sql = "DELETE FROM "+tableName;
			stmt.executeUpdate(sql);
			System.out.println("Table deleted successfully...");
			DBConnectionManager.getConnection().close(); //close db connection 
		}
		catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}// DELETE TABLE METHOD

}
