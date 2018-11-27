package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Map;
import models.algorithms.AlgorithmModel;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;

public class Test extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
		    
	        Scene scene = new Scene(root, 300, 275);
	    
	        stage.setTitle("FXML Welcome");
	        stage.setScene(scene);
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Random random = new Random(2); 
		random.reformatValue();
		//launch(args);
	}

}
