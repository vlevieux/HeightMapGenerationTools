package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.database.DaoModel;

public class AdminAddController {

    @FXML
    private TextField admin_add_textfield_license;

    @FXML
    private MenuButton admin_add_menu_button_type;

    @FXML
    private MenuButton admin_add_menu_button_duration;

    @FXML
    private Text admin_text_message;
    
    private int licenseType;
    
    private int duration;

    public void initialize() {
    	admin_add_menu_button_type.getItems().stream().forEach((MenuItem menuItem) -> menuItem.setOnAction(ev -> {admin_add_menu_button_type.setText(menuItem.getText());licenseType=(menuItem.getText()).equals("User")?1:2;}));
    	admin_add_menu_button_duration.getItems().stream().forEach((MenuItem menuItem) -> menuItem.setOnAction(ev -> {admin_add_menu_button_duration.setText(menuItem.getText());duration=Integer.parseInt(menuItem.getText().replaceAll("[^0-9]",""));}));
    }
    
    @FXML
    void adminAddLicense(ActionEvent event) {
    	if (admin_add_textfield_license.getText().matches("^([A-Z0-9]{4}-){3}[A-Z0-9]{4}$")) {
    		int add = DaoModel.insertTableLicenses(admin_add_textfield_license.getText(), licenseType, duration);
    		if (add == 1) {
    			admin_text_message.setText("This license already exist in the database.");
    		}
    		else {
    			System.out.println(admin_add_textfield_license.getText()+" "+licenseType+" "+duration);
    		}
    	} else if (licenseType==0) {
    		admin_text_message.setText("You must select a license type.");
    	} else if (duration==0) {
    		admin_text_message.setText("You must select a duration type.");
    	} else {
    		admin_text_message.setText("The license you have entered does not match the license format.");
    	}
    }

}
