package controllers;

import java.util.logging.Handler;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.database.DaoModel;

public class Main extends Application {
	
	public final static Logger LOGGER = Logger.getLogger( LoggerAlgorithm.class.getName());
	
	public Stage stage2;
	LoadingController controller;
	@Override
	public void start(Stage stage) {
		setupLogger();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loadingView.fxml"));
			Parent root = (Parent) loader.load();
			controller = (LoadingController)loader.getController();
            Scene scene = new Scene(root);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
		Platform.runLater(new Runnable() {
            @Override public void run() {
        		loading(stage);
            }
        });		
	}
	
	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}

	private void loading(Stage stage) {
		checkTable();
		
		try {
			stage.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/licenseView.fxml"));
			Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            stage2 = new Stage();
            stage2.initStyle(StageStyle.DECORATED);
	        stage2.setTitle("Licence Activation");
	        stage2.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage2.setScene(scene);
	        stage2.setResizable(false);
	        //stage.setOnHiding( event -> {for(Handler h:LOGGER.getHandlers()){h.close();}} );
	        stage2.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void setupLogger() {
		LOGGER.setUseParentHandlers(false);
		Logger globalLogger = Logger.getLogger("global");
		Handler[] handlers = globalLogger.getHandlers();
		for(Handler handler : handlers) {
		    globalLogger.removeHandler(handler);
		}
		System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");
	}
	
	private void checkTable() {
		String [] tables = {"HEIGHTMAP_PARAMETERS","HEIGHTMAP_STATISTICS", "LICENSES"};
		for (int tableNumber = 0; tableNumber<3; tableNumber++) {
			String table = tables[tableNumber];
			int result = DaoModel.checkExistingTable(table);
			
			switch(result) {
				case 1:
					// The table does not exist
					switch(tableNumber) {
						case 0:
							DaoModel.createTableMapParameters();
							break;
						case 1:
							DaoModel.createTableMapStatistics();
							break;
						case 2:
							DaoModel.createTableLicenses();
							DaoModel.insertTableLicensesAdmin();
							break;	
					}
					break;
				case 2:
					//The table exist but is empty
					if (tableNumber == 2) {
						DaoModel.insertTableLicensesAdmin();
					}
					
					break;
				case 3:
					//The table exist and is not empty
					if (tableNumber==0 || tableNumber==1) {
						DaoModel.deleteTables();
					}
				break;
			}
		}	
	}
}
