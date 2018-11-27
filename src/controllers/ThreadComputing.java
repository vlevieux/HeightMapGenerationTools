/**
 * ThreadComputing.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

// Not used anymore

package controllers;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ProgressBar;
import models.algorithms.AlgorithmModel;

//Not Used anymore
public class ThreadComputing implements Runnable {
	
	public AlgorithmModel algorithm;
	public ProgressBar pB;
	public DoubleProperty test;
	
	ThreadComputing(DoubleProperty test){
		this.test = test;
	}
	
	ThreadComputing(AlgorithmModel algorithm, ProgressBar pB){
		this.algorithm = algorithm;
		this.pB = pB;
	}
	
	@Override
	public void run() {
		int i = 0;
		while (i<10) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			test.setValue(i);
			//algorithm.pB = this.pB;
			//algorithm.apply();
			//img algorithm.generateImage();
			i++;
		}
	}
}
