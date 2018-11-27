package models.algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import models.Map;

public abstract class AlgorithmModel {
	
	protected Map map;
	protected int size;
	protected int progress;
	
	AlgorithmModel(int size){
		this.size = size;
		this.map = new Map(this.size);
	}
	
	public abstract void apply();
	
	public void getParameters() {}
	
	public void setParameters() {}
	
	public void setProgress(int progress) {
		if (progress >= 0 && progress <= 100)
			this.progress = progress;
	}
	
	public int getProgress() {
		return this.progress;
	}
	
	public void generateImage() {
		BufferedImage img = new BufferedImage(this.map.getSize(), this.map.getSize(),BufferedImage.TYPE_INT_RGB);
		for (int j=0;j<this.map.getSize(); j++) {
			for (int i=0; i<this.map.getSize(); i++) {
				img.setRGB(i, j, (int)this.map.get(i,j)+((int)this.map.get(i,j)<<8)+((int)this.map.get(i,j)<<16));
			}
		}
		try {
			File f = new File("test.png");
			ImageIO.write(img,  "png", f);
		}catch (IOException e) {
			System.out.println("Error: "+e);
		}
	}
	
	public void reformatValue() {
		double a = 255.0/(map.getMax()-map.getMin());
		double b = 255.0/(map.getMax()-map.getMin())*map.getMin();
		for (int j=0;j<this.map.getSize(); j++) {
			for (int i=0; i<this.map.getSize(); i++) {
					map.set(i, j, a*map.get(i, j)-b);
			}
		}
	}
}
