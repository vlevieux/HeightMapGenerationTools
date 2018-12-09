package controllers;

import java.util.logging.Handler;
import java.util.logging.Logger;


import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.database.DaoModel;

public class Main extends Application {
	
	public final static Logger LOGGER = Logger.getLogger( LoggerAlgorithm.class.getName());
	
	@Override
	public void start(Stage stage) {
		//setupLogger();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loadingView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		InitializingTask task = new InitializingTask();
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
			    new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent event) {
						stage.close();
						loadingLicenseScreen();			
					}});
		Thread t = new Thread(task);
		t.start();
	}
	
	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}

	private void setupLogger() {
		LOGGER.setUseParentHandlers(false);
		Logger globalLogger = Logger.getLogger("");
		Handler[] handlers = globalLogger.getHandlers();
		for(Handler handler : handlers) {
		    globalLogger.removeHandler(handler);
		}
		System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");
	}
	
	public static void checkTable() {
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
			}
		}	
	}
	
	private void loadingLicenseScreen() {
		try {
			Stage stageLicense;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/licenseView.fxml"));
			Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            stageLicense = new Stage();
            stageLicense.initStyle(StageStyle.DECORATED);
	        stageLicense.setTitle("Licence Activation");
	        stageLicense.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stageLicense.setScene(scene);
	        stageLicense.setResizable(false);
	        stageLicense.setOnHiding( event -> {for(Handler h:LOGGER.getHandlers()){h.close();}} );
	        stageLicense.show();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
