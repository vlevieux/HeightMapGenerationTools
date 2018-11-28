package controllers;

import java.io.IOException;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLLicenseController {

    @FXML
    private TextField license_textfield_license;

    @FXML
    private Button license_button_activate;
    
    @FXML
    private Text license_text_error_message;
    
    //Just basic license store
    private String validLicenses[] = {"1234-1234-1234-1234"};

    @FXML
    void activate(ActionEvent event) {
    	if (license_textfield_license.getText().matches("^([A-Z0-9]{4}-){3}[A-Z0-9]{4}$")) {
    		if (checklicense(license_textfield_license.getText())) {
    			Stage stage = (Stage) license_button_activate.getScene().getWindow();
				stage.close();
		    	changeStage(stage);
    		} else {
    			license_text_error_message.setText("The license you have entered is not valid.");
    		}
    	} else {
    		license_text_error_message.setText("The license you have entered does not match the license format.");
    	}
    	
    }
    
    private void changeStage(Stage s) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
			Scene scene = new Scene(root);
	        Stage stage = s;
	        stage.setTitle("Height Map Generation Tool");
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    // TODO: C. check license
    private boolean checklicense(String license) {
    	return Arrays.asList(validLicenses).contains(license);
    }
}
