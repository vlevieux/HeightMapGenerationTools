package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.algorithms.Random;

public class FXMLController {
	
	// Naming Convention where_what_why
	
	@FXML
    private MenuItem main_menu_run_run;
	
	@FXML
	private MenuButton main_menu_btn_algorithm_list;
	
	@FXML
	private AnchorPane main_anchor_pane_random;
		
	@FXML
	private TextField random_anchor_text_field_size;
	
	@FXML
	private TextField random_anchor_text_field_min;
	
	@FXML
	private TextField random_anchor_text_field_max;
	
	@FXML
	private AnchorPane main_anchor_pane_square_diamond;
	
	@FXML
	private ImageView main_image_view_map;
	
	@FXML
	private ProgressBar main_progress_bar_progress_bar;
	
	StringProperty algorithmName = new SimpleStringProperty();
	
	private TextField[] numericTextFields;
	
	public void initialize() {
		numericTextFields = new TextField[] {random_anchor_text_field_size, random_anchor_text_field_min, random_anchor_text_field_max};
		for (TextField tf : numericTextFields) {
			tf.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	tf.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			    }
			});
		}
    }
	
	@FXML
    void menuRun(ActionEvent event) {
		int size = 0;
		Image image;
		switch (algorithmName.getValueSafe()) {
			case "Random":
				size = Integer.valueOf(random_anchor_text_field_size.getText());
				if (size == 0) {
					alertDialog("Error in parameters", "Argument size cannot be 0.", "To generate a Map, you must enter a positive integer in the size field.");
					return;
				}
				
				int min = Integer.valueOf(random_anchor_text_field_min.getText());
				int max = Integer.valueOf(random_anchor_text_field_max.getText());
				if (min>max) {
					alertDialog("Error in parameters", "Argument min is greater than max.", "To generate a Map, you must enter 2 value : min and max.");
					return;
				}
				Random algo = new Random(size);
				ThreadComputing tc = new ThreadComputing(algo, main_progress_bar_progress_bar);
				Thread t2 = new Thread(tc);
				t2.start();
				//Random algo = new Random(size);
				//algo.apply();
				//image = algo.generateImage();
				//main_image_view_map.setImage(image);
				break;
			case "Square Diamond":
				break;
		}
    }
	
	@FXML
    void setRandomMenuItem(ActionEvent event) {
		algorithmName.setValue("Random");
		main_menu_btn_algorithm_list.setText(algorithmName.getValueSafe());
		main_anchor_pane_square_diamond.setVisible(false);
		main_anchor_pane_random.setVisible(true);
    }
	
	@FXML
	void setSquareDiamondMenuItem(ActionEvent event) {
		algorithmName.setValue("Square Diamond");
		main_menu_btn_algorithm_list.setText(algorithmName.getValueSafe());
		main_anchor_pane_random.setVisible(false);
		main_anchor_pane_square_diamond.setVisible(true);
	}
	
	private void alertDialog(String title, String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();
	}
}