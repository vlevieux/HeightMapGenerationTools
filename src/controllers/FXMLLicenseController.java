package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

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
import models.database.DaoModel;

public class FXMLLicenseController {

	Preferences sessionPreferences = Preferences.userRoot();
	
    @FXML
    private TextField license_textfield_license;

    @FXML
    private Button license_button_activate;
    
    @FXML
    private Text license_text_error_message;
        
    @FXML
    void activate(ActionEvent event) {
    	if (license_textfield_license.getText().matches("^([A-Z0-9]{4}-){3}[A-Z0-9]{4}$")) {
    		int licenseType = checkLicense(license_textfield_license.getText());
    		if (licenseType>0) {
    			sessionPreferences.putInt("LICENSE_TYPE", licenseType);
    			this.close();
    		} else {
    			license_text_error_message.setText("The license you have entered is not valid.");
    		}
    	} else {
    		license_text_error_message.setText("The license you have entered does not match the license format.");
    	}
    }
    
    private void close() {
    	Stage stage = (Stage) license_button_activate.getScene().getWindow();
		stage.close();
    	changeStage(stage);
    }
    
    private void changeStage(Stage s) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
			Scene scene = new Scene(root);
	        Stage stage = s;
	        stage.setTitle("Height Map Generation Tool");
	        stage.setMinWidth(1000);
	        stage.setMinHeight(900);
	        stage.setResizable(true);
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // TODO: C. check license return true if license is ok, false is not.
    private int checkLicense(String license) {
    	ResultSet rs = DaoModel.retrieveLicense(license);
    	try {
			if (rs.next()) {
				return 1;
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return 0;
    	//return Arrays.asList(validLicenses).contains(license);
    	
    }
}
