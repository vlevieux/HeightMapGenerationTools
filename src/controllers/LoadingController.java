package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LoadingController {

    @FXML
    private Text text_loading;

    @FXML
    private ImageView loading_image;
    
    public void initialize() {
    	loading_image.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream("images/loading.jpg")));
    }
}
