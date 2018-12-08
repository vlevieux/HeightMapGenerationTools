package controllers;

import java.io.IOException;
import java.lang.Math;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
    		int check = checkLicense(license_textfield_license.getText());
    		switch(check) {
	    		case 1:
	    			int days = checkDays(license_textfield_license.getText());
	    			FXMLController.alertDialog("Current License", "Time left : "+days+" days.", "When your license will be finished, please see your administrator.", AlertType.INFORMATION);
	    			sessionPreferences.putInt("LICENSE_TYPE", check);
	    			this.close();
	    			break;
	    		case 2:
	    			sessionPreferences.putInt("LICENSE_TYPE", check);
	    			this.close();
	    			break;
	    		case 3:
	    			license_text_error_message.setText("The license you have entered is expired.");
	    			break;
	    		default :
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
    
    /**
     * 
     * @param license
     * @return
     */
    private int checkLicense(String license) {
    	ResultSet rs = DaoModel.retrieveLicense(license);
    	try {
			if (rs.next()) {
				int licenseType = rs.getInt(3);
				// admin user
				if (licenseType == 2) {
					return licenseType;
				}
				// normal user
				else if (licenseType == 1){
					Date date = rs.getDate(4);
					// First activation of the license
					if (date == null) {
						DaoModel.validateLicense(license);
						return licenseType;
					}
					// Check if the license expired
					else {
						// License expired
						int days = checkDays(license);
						if (days < 0) {
							return 3;
						}
						else {
							return licenseType;
						}
					}
				}
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return -1;
    	//return Arrays.asList(validLicenses).contains(license);
    	
    }
    
    /**
     * 
     * @param license
     * @return
     */
    private int checkDays(String license) {
    	ResultSet rs = DaoModel.retrieveLicense(license);
		try {
			if(rs.next()) {
				Date date = rs.getDate(4);
				int authorizedUseTime = rs.getInt(5);
				
				Calendar calLicense = Calendar.getInstance();
				calLicense.setTime(date);
				calLicense.add(Calendar.DATE, authorizedUseTime);
				
				Calendar calLocale = Calendar.getInstance();
				Date localeDate = new Date();
				calLocale.setTime(localeDate);
				
				Date startDate = calLocale.getTime();
				Date endDate = calLicense.getTime();
				long startTime = startDate.getTime();
				long endTime = endDate.getTime();
				long diffTime = endTime - startTime;
				long days = diffTime / (1000 * 60 * 60 * 24);

				return Math.toIntExact(days);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
    }
}
