package controllers;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Test extends Application {
	
	public final static Logger LOGGER = Logger.getLogger( LoggerAlgorithm.class.getName());
	
	@Override
	public void start(Stage stage) {
		
		LOGGER.log(Level.INFO, "Je mange des cochonets");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/licenseView.fxml"));
			Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
	        stage.setTitle("Licence Activation");
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.setOnHiding( event -> {for(Handler h:LOGGER.getHandlers()){h.close();}} );
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}


}
