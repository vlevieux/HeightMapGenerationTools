package models.algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.DoubleConsumer;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import models.Map;

public abstract class AlgorithmModel {
	
	//Testing
	public ProgressBar pB;
	
	protected Map map;
	protected int size;
	protected double progress;
	
	AlgorithmModel(int size){
		this.size = size;
		this.map = new Map(this.size);
	}
	
	public abstract void apply();
	
	public abstract void getParameters();
	
	public abstract void setParameters(java.util.Map<String,String> parametersMap);
	
	public void setProgress(double progress) {
		if (progress >= 0 && progress <= 1)
			this.progress = progress;
	}
	
	public double getProgress() {
		return this.progress;
	}
	
	public Image generateImage() {
		this.reformatValue();
		BufferedImage img = new BufferedImage(this.map.getSize(), this.map.getSize(),BufferedImage.TYPE_INT_RGB);
		for (int j=0;j<this.map.getSize(); j++) {
			for (int i=0; i<this.map.getSize(); i++) {
				img.setRGB(i, j, (int)this.map.get(i,j)+((int)this.map.get(i,j)<<8)+((int)this.map.get(i,j)<<16));
			}
		}
		/*try {
			File f = new File("test.png");
			ImageIO.write(img,  "png", f);
		}catch (IOException e) {
			System.out.println("Error: "+e);
		}*/
		return SwingFXUtils.toFXImage(img, null);
	}
	
	protected void reformatValue() {
		double a = 255.0/(map.getMax()-map.getMin());
		double b = 255.0/(map.getMax()-map.getMin())*map.getMin();
		for (int j=0;j<this.map.getSize(); j++) {
			for (int i=0; i<this.map.getSize(); i++) {
					map.set(i, j, a*map.get(i, j)-b);
			}
		}
	}
	
	public void publishProgress(DoubleConsumer progressUpdate) {
		System.out.println(this.getProgress());
		Platform.runLater(() -> progressUpdate.accept(this.getProgress()));
	}
}
