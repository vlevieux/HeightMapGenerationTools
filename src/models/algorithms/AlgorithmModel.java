package models.algorithms;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import controllers.LoggerAlgorithm;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import models.Map;

public abstract class AlgorithmModel extends Task<Image> {
	
	protected Map map;
	protected int size;
	protected java.util.Map<String, String> parameters;
	protected java.util.Map<String, String> statistics;
	
	protected double pointDone = 0;
	protected double progress;
	
	protected boolean reformat = true;
	
	Logger LOGGER = Logger.getLogger( LoggerAlgorithm.class.getName());
	
	AlgorithmModel(int size){
		this.size = size;
		this.map = new Map(this.size);
		this.parameters = new HashMap<String,String>();
		this.setParameter("size", this.size);
		this.statistics = new HashMap<String,String>();
	}
	
	public abstract void apply();
	
	public java.util.Map<String, String> getParameters() {
		return this.parameters;
	}
	
	public String getParameter(String key) {
		return this.parameters.get(key);
	}
	
	public void setParameters(java.util.Map<String,String> parametersMap) {
		reformat = Boolean.parseBoolean(parametersMap.get("reformat"));
		this.parameters = parametersMap;
	}
	
	public void setParameter(String key, Object value) {
		this.parameters.put(key, String.valueOf(value));
	}

	public void setProgress(double progress) {
		if (progress >= 0 && progress <= 1)
			this.progress = progress;
		updateProgress(progress, 1);
	}
	
	protected void pointCalculated() {
		pointDone++;
		this.setProgress((double)(pointDone/((double)this.size*this.size-4)));
	}
	
	protected void pointCalculated(int total) {
		pointDone++;
		this.setProgress((double)(pointDone/((double)total)));
	}
	
	public Image generateImage() {
		if (reformat)
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
	
	public java.util.Map<String, String> getStatistics() {
		return this.statistics;
	}
	
	protected void calculateStatistics() {
		this.statistics.put("size", String.valueOf(this.map.getSize()));
		if (!this.isCancelled())
			this.statistics.put("min", String.valueOf(this.map.getMin()));
		if (!this.isCancelled()) 
			this.statistics.put("max", String.valueOf(this.map.getMax()));
		if (!this.isCancelled()) 
			this.statistics.put("average", String.valueOf(this.map.getAverage()));
		if (!this.isCancelled()) 
			this.statistics.put("median", String.valueOf(this.map.getMedian()));
	}
	
	@Override
	protected Image call() throws Exception {
		updateMessage("In progress...");
		this.log("Applying "+this+ String.format(" on a Map of size : %dx%d",this.size,this.size));
		this.apply();
		updateMessage("Calculating statistics...");
		if (!this.isCancelled()) 
			calculateStatistics();
		updateMessage("Generating image...");
		Image img;
		if (!this.isCancelled()) {	
			img = this.generateImage();
			this.log(String.format("Generated Image, size : %dx%dpx",(int)img.getHeight(),(int)img.getWidth()));
			this.log("Success");
			updateMessage("Displaying image...");
			return img;
		}
		log("Task has been cancelled.");
		return null;
	}
	
	private void log(String msg) {
		try {
			//Uncomment to hide in Console.
			//LOGGER.setUseParentHandlers(false);
			Handler fh = new FileHandler(LoggerAlgorithm.fileLogName, true);
			fh.setEncoding("UTF-8");
			fh.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fh);
			LOGGER.log(Level.INFO, msg);
			fh.close();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.getClass().getName();
	}
	
}
