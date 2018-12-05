package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	//public final static Logger LOGGER = Logger.getLogger( LoggerAlgorithm.class.getName());
	
	@Override
	public void start(Stage stage) {
		System.out.println(System.getProperty("os.name"));
		System.exit(0);
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
		//int table1 = checkExistingTable(HEIGHTMAP_PARAMETERS);
		//int table2 = checkExistingTable(HEIGHTMAP_STATISTICS);
		
	}

}
