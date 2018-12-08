package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import models.database.DaoModel;

public class FXMLDatabaseController {
	
    @FXML private MenuButton db_menu_button_action;
    @FXML private TextField db_text_field;

    @FXML private VBox db_vbox_tables;
    
    @FXML private TableView<ArrayList<ArrayList<String>>> db_table_map;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_map_col_id;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_map_col_algorithm;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_map_col_height;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_map_col_width;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_map_col_param;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_map_col_time;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_map_col_date;
    
    @FXML private TableView<ArrayList<ArrayList<String>>> db_table_stat;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_id;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_algorithm;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_min;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_max;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_avg;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_med;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_time;
    @FXML private TableColumn<ArrayList<ArrayList<String>>, String> db_table_stat_col_date;

    @FXML private Text db_text_message;
    
    StringProperty action = new SimpleStringProperty();
    
    @FXML
    void initialize() {
    /*
    	db_table_map_col_id.setCellValueFactory(new Callback<CellDataFeatures<ArrayList<ArrayList<String>>, String>, ObservableValue<String>>() {
    	     public ObservableValue<String> call(CellDataFeatures<ArrayList<ArrayList<String>>, String> p) {
    	         // p.getValue() returns the Person instance for a particular TableView row
    	         return p.getValue().firstNameProperty();
    	     }
    	  });
    	 }
   	*/
    	
    }
    
    
    
	@FXML
    void executeQuery(ActionEvent event){
    	switch (action.getValueSafe()) {
			case "Show Tables":
				try {
					ArrayList<ArrayList<String>> maps  = new ArrayList<>();
					ResultSet rs = DaoModel.retrieveFullMapParameters();
					while(rs.next()) {
						ArrayList<String> map  = new ArrayList<>();
						for (int colIndex=1; colIndex <= 6; colIndex++) {
							// Add map data to arraylist
							map.add(rs.getString(colIndex));
						}    			 
						maps.add(map);
					}
					ArrayList<ArrayList<String>> stats  = new ArrayList<>();
					rs = DaoModel.retrieveFullMapStatistics();
					while(rs.next()) {
						ArrayList<String> stat  = new ArrayList<>();
						for (int colIndex=1; colIndex <= 7; colIndex++) {
							// Add stat data to arraylist
							stat.add(rs.getString(colIndex)); 
						}    			 
						stats.add(stat);
					}
					db_table_map.getItems().setAll(maps);
					db_table_stat.getItems().setAll(stats);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    		break;
	    	case "Retrieve Parameters":
	    		String mapId = db_text_field.getText();
	    		DaoModel.retrieveMapParameters(mapId);
	    		break;
	    	case "Delete Last Map":
	    		DaoModel.deleteLastMap();
	    		break;
	    	case "Delete Tables":
	    		DaoModel.deleteTables();
	    		break;
    	}    	
    }
    
	@FXML
    private void setTables(ActionEvent event) {
		action.setValue("Show Tables");
		db_menu_button_action.setText(action.getValueSafe());
		db_text_field.setVisible(false);
		db_vbox_tables.setVisible(true);
    }
	
	@FXML
    private void setRetrieveParameters(ActionEvent event) {
		action.setValue("Retrieve Parameters");
		db_menu_button_action.setText(action.getValueSafe());
		db_text_field.setVisible(true);
    }
	
	@FXML
    private void setDelLastMap(ActionEvent event) {
		action.setValue("Delete Last Map");
		db_menu_button_action.setText(action.getValueSafe());
		db_text_field.setVisible(false);
    }
	
	@FXML
    private void setDelTables(ActionEvent event) {
		action.setValue("Delete Tables");
		db_menu_button_action.setText(action.getValueSafe());
		db_text_field.setVisible(false);
    } 
}
