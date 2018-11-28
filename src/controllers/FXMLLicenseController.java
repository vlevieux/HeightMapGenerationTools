package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLLicenseController {

    @FXML
    private TextField license_textfield_license;

    @FXML
    private Button license_button_activate;

    @FXML
    void activate(ActionEvent event) {
    	if (license_textfield_license.getText().matches("^([A-Z0-9]{4}-){3}[A-Z0-9]{4}$"))
    		if (checklicense(license_textfield_license.getText()))
    				((Stage) license_button_activate.getScene().getWindow()).close();
    }
    
    // TODO: C. check license
    private boolean checklicense(String license) {
    	return true;
    }
}
