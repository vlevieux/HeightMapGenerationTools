package controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.algorithms.AlgorithmModel;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;

public class FXMLController {
	
	// Naming Convention where_what_why
	
	@FXML
    private MenuItem main_menu_run_run;
	
	@FXML
	private MenuButton main_menu_btn_algorithm_list;
	
	@FXML
	private VBox main_vbox_random;
		
	@FXML
	private TextField random_vbox_text_field_size;
	
	@FXML
	private TextField random_vbox_text_field_min;
	
	@FXML
	private Slider random_vbox_slider_min;
	
	@FXML
	private TextField random_vbox_text_field_max;
	
	@FXML
	private Slider random_vbox_slider_max;
	
	@FXML
	private TextField square_diamond_vbox_size;
	
	@FXML
	private VBox main_vbox_square_diamond;
	
	@FXML
	private ImageView main_image_view_map;
	
	@FXML
	private Button main_button_cancel;
	
	@FXML
	private Text main_text_size;
	
	@FXML
	private Label main_label_status;
	
	@FXML
	private Text main_text_status;
	
	@FXML
	private Text main_text_time;
	
	@FXML
	private ProgressBar main_progress_bar_progress_bar;
	
	StringProperty algorithmName = new SimpleStringProperty();
	AlgorithmModel algo;
	
	public void initialize() {

		random_vbox_text_field_size.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue.replaceAll("[^\\d]", "");
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
			        if (value <= 5000) {
			        	random_vbox_text_field_size.setText(String.valueOf(value));
			        } else {
			        	random_vbox_text_field_size.setText(oldValue);
			        }
		        }
		    }
		});
		
		random_vbox_text_field_min.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue.replaceAll("[^\\d]", "");
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
			        if (value <= 255) {
			        	random_vbox_text_field_min.setText(String.valueOf(value));
			        	random_vbox_slider_min.setValue(value);
			        } else {
			        	random_vbox_text_field_min.setText(oldValue);
			        }
		        }
		    }
		});
		
		random_vbox_slider_min.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				random_vbox_text_field_min.setText(String.valueOf((int)random_vbox_slider_min.getValue()));
			}
			
		});
		
		random_vbox_text_field_max.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue.replaceAll("[^\\d]", "");
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
			        if (value <= 255) {
			        	random_vbox_text_field_max.setText(String.valueOf(value));
			        	random_vbox_slider_max.setValue(value);
			        } else {
			        	random_vbox_text_field_max.setText(oldValue);
			        }
		        }
		    }
		});
		
		random_vbox_slider_max.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				random_vbox_text_field_max.setText(String.valueOf((int)random_vbox_slider_max.getValue()));
			}
			
		});
    }
	
	@FXML
    void menuRun(ActionEvent event) {
		int size = 0;
				
		//Checking size != 0
		size = Integer.valueOf(random_vbox_text_field_size.getText());
		if (size == 0) {
			alertDialog("Error in parameters", "Argument size cannot be 0.", "To generate a Map, you must enter a positive integer in the size field.", AlertType.ERROR);
			return;
		}
		switch (algorithmName.getValueSafe()) {
			case "Random":
				
				//Check min>max
				int min = Integer.valueOf(random_vbox_text_field_min.getText());
				int max = Integer.valueOf(random_vbox_text_field_max.getText());
				if (min>max) {
					alertDialog("Error in parameters", "Argument min is greater than max.", "To generate a Map, you must enter 2 value : min and max.", AlertType.ERROR);
					return;
				}
				
				algo = new Random(size);
				Map <String, String> hm = new HashMap<String, String>();
				hm.put("min", String.valueOf(min));
				hm.put("max", String.valueOf(max));
				algo.setParameters(hm);
				break;
			case "Square Diamond":
				algo = new SquareDiamond(129);
				break;
			default:
					alertDialog("Error unknown Algorithm", "Algorithm you have selected is not recognized.", "You must select one algorithm from the menu list.", AlertType.ERROR);
				return;
		}
		
		main_progress_bar_progress_bar.progressProperty().bind(algo.progressProperty());
		main_text_status.textProperty().bind(algo.messageProperty());
		algo.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
			    new EventHandler<WorkerStateEvent>() {
			    @Override
			    public void handle(WorkerStateEvent t) {
			    	main_image_view_map.setImage(algo.getValue());
			    	double height = main_image_view_map.getImage().getHeight();
			    	double width = main_image_view_map.getImage().getWidth();
			    	main_text_size.setText(String.format("%d x %dpx", (int)height, (int)width));
			    	main_progress_bar_progress_bar.progressProperty().unbind();
			    	main_text_status.textProperty().unbind();
			    	main_text_status.setText("Done.");
			    }
			});
		Thread t = new Thread(algo);
		t.start();
    }
	
	@FXML
    void cancelTask(ActionEvent event) {
		if (algo != null)
			algo.cancel();
		main_progress_bar_progress_bar.progressProperty().unbind();
		main_progress_bar_progress_bar.setProgress(0.0);
		main_text_status.textProperty().unbind();
    	main_text_status.setText("Cancelled.");
    }
	
	@FXML
    private void setRandomMenuItem(ActionEvent event) {
		algorithmName.setValue("Random");
		main_menu_btn_algorithm_list.setText(algorithmName.getValueSafe());
		main_vbox_square_diamond.setVisible(false);
		main_vbox_random.setVisible(true);
    }
	
	@FXML
	private void setSquareDiamondMenuItem(ActionEvent event) {
		algorithmName.setValue("Square Diamond");
		main_menu_btn_algorithm_list.setText(algorithmName.getValueSafe());
		main_vbox_random.setVisible(false);
		main_vbox_square_diamond.setVisible(true);
	}
	
	@FXML
    private void showAbout(ActionEvent event) {
		alertDialog("Height Map Generation Tool","Height Map Generation Tool can be used to generate aleatory\nheight maps based on procedural generation algorithms.\nThe generated images can be imported into 3D design software.","Height Map Generation Tool is an original idea of B. HYON & V. LEVIEUX and was directed by C. DELTEL & V. LEVIEUX.", AlertType.INFORMATION);
    }

	@FXML
    private void deleteImage(ActionEvent event) {
		main_image_view_map.setImage(null);
    }
	/**
	 * Generate Alert Dialog
	 * @param title (String) Title of the Alert Dialog
	 * @param header (String) Header of the Alert Dialog
	 * @param content (String) Content of the Alert Dialog
	 * @param type (AlertType) AlertType.INFORMATION, AlerType.ERROR, ...
	 */
	private void alertDialog(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
		alert.showAndWait();
	}
	
}