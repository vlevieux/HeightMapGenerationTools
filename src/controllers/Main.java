package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.database.DaoModel;

public class Main extends Application {
	
	//public final static Logger LOGGER = Logger.getLogger( LoggerAlgorithm.class.getName());
	
	@Override
	public void start(Stage stage) {
		//checkTable();
		setupLogger();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/licenseView.fxml"));
			Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
	        stage.setTitle("Licence Activation");
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.setResizable(false);
	        //stage.setOnHiding( event -> {for(Handler h:LOGGER.getHandlers()){h.close();}} );
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}

	private void setupLogger() {
		//LOGGER.setUseParentHandlers(false);
		/*Logger globalLogger = Logger.getLogger("global");
		Handler[] handlers = globalLogger.getHandlers();
		for(Handler handler : handlers) {
		    globalLogger.removeHandler(handler);
		}*/
		System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");
	}
	
	// TODO: C. Deltel check Table existe
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
							DaoModel.insertTableLicenses();
							break;	
					}
					break;
				case 2:
					//The table exist but is empty
					if (tableNumber == 2) {
						DaoModel.insertTableLicenses();
					}
					
					break;
				case 3:
					//The table exist and is not empty
					switch(tableNumber) {
					case 0:
						DaoModel.deleteTableMapParameters();
						break;
					case 1:
						DaoModel.deleteTableMapStatistics();
						break;
					case 2:
						DaoModel.deleteTableLicenses();
						DaoModel.insertTableLicenses();
						break;	
				}
				break;
			}
		}	
	}
}
