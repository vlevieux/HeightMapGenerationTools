package controllers;

import javafx.scene.image.Image;

public interface AlgorithmListener {

	void onProgressUpdate(double progress);
	
	void onFinished(Image img);
}
