package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.database.DaoModel;

public class FXMLDatabaseController {
	
    @FXML private MenuButton db_menu_button_action;
    @FXML private TextField db_text_field;

    @FXML private VBox db_vbox_tables;
    
    @FXML private TableView<Data> db_table_map;
    @FXML private TableColumn<Data, String> db_table_map_col_id;
    @FXML private TableColumn<Data, String> db_table_map_col_algorithm;
    @FXML private TableColumn<Data, String> db_table_map_col_height;
    @FXML private TableColumn<Data, String> db_table_map_col_width;
    @FXML private TableColumn<Data, String> db_table_map_col_param;
    @FXML private TableColumn<Data, String> db_table_map_col_time;
    @FXML private TableColumn<Data, String> db_table_map_col_date;
    
    @FXML private TableView<Data> db_table_stat;
    @FXML private TableColumn<Data, String> db_table_stat_col_id;
    @FXML private TableColumn<Data, String> db_table_stat_col_algorithm;
    @FXML private TableColumn<Data, String> db_table_stat_col_min;
    @FXML private TableColumn<Data, String> db_table_stat_col_max;
    @FXML private TableColumn<Data, String> db_table_stat_col_avg;
    @FXML private TableColumn<Data, String> db_table_stat_col_med;
    @FXML private TableColumn<Data, String> db_table_stat_col_time;
    @FXML private TableColumn<Data, String> db_table_stat_col_date;

    @FXML private Text db_text_message;
    
    StringProperty action = new SimpleStringProperty();
    private ObservableList<Data> dataMap;
    private ObservableList<Data> dataStat;
    
    @FXML
    void initialize() {  
    	
    	db_table_map.setEditable(true);
    	db_table_stat.setEditable(true);
    	 
    	db_table_map_col_id = new TableColumn<Data, String>("ID");
    	db_table_map_col_algorithm = new TableColumn<Data, String>();
    	db_table_map_col_height = new TableColumn<Data, String>();
    	db_table_map_col_width = new TableColumn<Data, String>();
    	db_table_map_col_param = new TableColumn<Data, String>();
    	db_table_map_col_time = new TableColumn<Data, String>();
    	db_table_map_col_date = new TableColumn<Data, String>();
    	
    	db_table_map_col_id.setCellValueFactory(new PropertyValueFactory<Data, String>("mapId"));
    	db_table_map_col_algorithm.setCellValueFactory(new PropertyValueFactory<Data, String>("mapAlgorithm"));
    	db_table_map_col_height.setCellValueFactory(new PropertyValueFactory<Data, String>("mapHeight"));
    	db_table_map_col_width.setCellValueFactory(new PropertyValueFactory<Data, String>("mapWidth"));
    	db_table_map_col_param.setCellValueFactory(new PropertyValueFactory<Data, String>("mapParam"));
    	db_table_map_col_time.setCellValueFactory(new PropertyValueFactory<Data, String>("mapTime"));
    	db_table_map_col_date.setCellValueFactory(new PropertyValueFactory<Data, String>("mapDate"));
    	
    	db_table_stat_col_id.setCellValueFactory(new PropertyValueFactory<Data, String>("statId"));
    	db_table_stat_col_algorithm.setCellValueFactory(new PropertyValueFactory<Data, String>("statAlgorithm"));
    	db_table_stat_col_min.setCellValueFactory(new PropertyValueFactory<Data, String>("statMin"));
    	db_table_stat_col_max.setCellValueFactory(new PropertyValueFactory<Data, String>("statMax"));
    	db_table_stat_col_avg.setCellValueFactory(new PropertyValueFactory<Data, String>("statAvg"));
    	db_table_stat_col_med.setCellValueFactory(new PropertyValueFactory<Data, String>("statMed"));
    	db_table_stat_col_time.setCellValueFactory(new PropertyValueFactory<Data, String>("statTme"));
    	db_table_stat_col_date.setCellValueFactory(new PropertyValueFactory<Data, String>("statDate"));    	
    }
      
	@FXML
    void executeQuery(ActionEvent event){
    	switch (action.getValueSafe()) {
			case "Show Tables":
				dataMap = FXCollections.observableArrayList();;
				dataStat = FXCollections.observableArrayList();;
				try {					
					ResultSet rs = DaoModel.retrieveFullMapParameters();
					while(rs.next()) {
						Data row = new Data(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
								rs.getString(6), rs.getString(7));					
						dataMap.add(row);
					}

					rs = DaoModel.retrieveFullMapStatistics();
					while(rs.next()) {
						Data row = new Data(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
								rs.getString(6), rs.getString(7), rs.getString(8));
						dataStat.add(row);
					}
					
					db_table_map.setItems(dataMap);
					db_table_map.getColumns().addAll(db_table_map_col_id, db_table_map_col_algorithm, db_table_map_col_height, db_table_map_col_width, db_table_map_col_param, db_table_map_col_time, db_table_map_col_date);
					db_table_stat.setItems(dataStat);
					db_table_map.getColumns().addAll(db_table_stat_col_id, db_table_stat_col_algorithm, db_table_stat_col_min, db_table_stat_col_max, db_table_stat_col_avg, db_table_stat_col_med, db_table_stat_col_time, db_table_map_col_date);
					
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
	
	public class Data {
		
		private SimpleStringProperty mapId;
		private SimpleStringProperty mapAlgorithm;
		private SimpleStringProperty mapHeight;
		private SimpleStringProperty mapWidth;
		private SimpleStringProperty mapParam;
		private SimpleStringProperty mapTime;
		private SimpleStringProperty mapDate;
		
		private SimpleStringProperty statId;
		private SimpleStringProperty statAlgorithm;
		private SimpleStringProperty statMin;
		private SimpleStringProperty statMax;
		private SimpleStringProperty statAvg;
		private SimpleStringProperty statMed;
		private SimpleStringProperty statTime;
		private SimpleStringProperty statDate;
		
	    private Data(String mapId, String mapAlgorithm, String mapHeight, String mapWidth, String mapParam, String mapTime, String mapDate) {
	        this.mapId = new SimpleStringProperty(mapId);
	        this.mapAlgorithm = new SimpleStringProperty(mapAlgorithm);
	        this.mapHeight = new SimpleStringProperty(mapHeight);
	        this.mapWidth = new SimpleStringProperty(mapWidth);
	        this.mapParam = new SimpleStringProperty(mapParam);
	        this.mapTime = new SimpleStringProperty(mapTime);
	        this.mapDate = new SimpleStringProperty(mapDate);
	    }
	    
	    private Data(String statId, String statAlgorithm, String statMin, String statMax, String statAvg, String statMed, String statTime, String statDate) {
	    	this.statId = new SimpleStringProperty(statId);
	        this.statAlgorithm = new SimpleStringProperty(statAlgorithm);
	        this.statMin = new SimpleStringProperty(statMin);
	        this.statMax = new SimpleStringProperty(statMax);
	        this.statAvg = new SimpleStringProperty(statAvg);
	        this.statMed = new SimpleStringProperty(statMed);
	        this.statTime = new SimpleStringProperty(statTime);
	        this.statDate = new SimpleStringProperty(statDate);
	    }
		
		/**
		 * @return the mapId
		 */
		protected SimpleStringProperty getMapId() {
			return mapId;
		}
		/**
		 * @return the mapAlgorithm
		 */
		protected SimpleStringProperty getMapAlgorithm() {
			return mapAlgorithm;
		}
		/**
		 * @return the mapHeight
		 */
		protected SimpleStringProperty getMapHeight() {
			return mapHeight;
		}
		/**
		 * @return the mapWidth
		 */
		protected SimpleStringProperty getMapWidth() {
			return mapWidth;
		}
		/**
		 * @return the mapParam
		 */
		protected SimpleStringProperty getMapParam() {
			return mapParam;
		}
		/**
		 * @return the mapTime
		 */
		protected SimpleStringProperty getMapTime() {
			return mapTime;
		}
		/**
		 * @return the mapDate
		 */
		protected SimpleStringProperty getMapDate() {
			return mapDate;
		}
		/**
		 * @return the statId
		 */
		protected SimpleStringProperty getStatId() {
			return statId;
		}
		/**
		 * @return the statAlgorithm
		 */
		protected SimpleStringProperty getStatAlgorithm() {
			return statAlgorithm;
		}
		/**
		 * @return the statMin
		 */
		protected SimpleStringProperty getStatMin() {
			return statMin;
		}
		/**
		 * @return the statMax
		 */
		protected SimpleStringProperty getStatMax() {
			return statMax;
		}
		/**
		 * @return the statAvg
		 */
		protected SimpleStringProperty getStatAvg() {
			return statAvg;
		}
		/**
		 * @return the statMed
		 */
		protected SimpleStringProperty getStatMed() {
			return statMed;
		}
		/**
		 * @return the statTime
		 */
		protected SimpleStringProperty getStatTime() {
			return statTime;
		}
		/**
		 * @return the statDate
		 */
		protected SimpleStringProperty getStatDate() {
			return statDate;
		}
		
		
	}
}
