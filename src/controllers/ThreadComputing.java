/**
 * ThreadComputing.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package controllers;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import models.Map;
import models.algorithms.AlgorithmModel;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;

public class ThreadComputing implements Runnable {
	
	public AlgorithmModel algorithm;
	public ProgressBar pB;
	
	ThreadComputing(AlgorithmModel algorithm, ProgressBar pB){
		this.algorithm = algorithm;
		this.pB = pB;
	}
	
	public void run() {
		algorithm.pB = this.pB;
		algorithm.apply();
		//img algorithm.generateImage();
	}
}
