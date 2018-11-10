/**
 * Map.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	
	private int size;
	private double[][] map;
	
	public Map(int size){
		this.setSize(size);
		map = new double[this.size][this.size];
		setZeros();
	}
	
	/**
	 * Set value in at position i,j.
	 * @param i coordinate (must be positive)
	 * @param j coordinate (must be positive)
	 * @param value value to set
	 */
	public void set(int i, int j, double value) {
		if (i>=0 & i<this.size & j>=0 & j<this.size)
			this.map[i][j] = value;
	}
	
	public double get(int i, int j) {
		return map[i][j];
	}
	
	/**
	 *  Filled the map with 0.
	 */
	public void setZeros() {
		// use fill method
		this.fill(0);
	}
	
	/**
	 * Filled with a specific value.
	 * 
	 * @param value Filled with this value
	 */
	public void fill(double value) {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				map[i][j] = value;
			}
		}
	}
	
	/**
	 * Add an offset to the whole map.
	 * 
	 * @param value offset to add
	 */
	public void offset(double value) {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				map[i][j]+= value;
			}
		}
	}
	
	/**
	 *  Get the size of the map.
	 * 
	 * @return the size of the map
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 *  Set the size of the map.
	 * 
	 * @param size Size of the map (must differ from 0)
	 */
	public void setSize(int size) {
		if (size != 0) {
			//Negative values are set positive.
			this.size = Math.abs(size);
		}
	}
	
	/**
	 *  Check if double is an integer.
	 * 
	 * @return true if is an integer else false
	 */
	private boolean isInteger(double i) {
		return ((i % 1) == 0);
	}
	
	/**
	 *  Find the max of the map.
	 * 
	 * @return the max of the map
	 */
	public double getMax() {
		double max = this.map[0][0];
		for (double[] row : this.map) {
			for (double item : row) {
				if (item > max)
					max = item;
			}
		}
		return max;
	}
	
	public void generateImage() {
		BufferedImage img = new BufferedImage(this.getSize(), this.getSize(),BufferedImage.TYPE_INT_RGB);
		for (int j=0;j<this.getSize(); j++) {
			for (int i=0; i<this.getSize(); i++) {
				img.setRGB(i, j, (int)this.get(i,j)+((int)this.get(i,j)<<8)+((int)this.get(i,j)<<16));
			}
		}
		try {
			File f = new File("test.png");
			ImageIO.write(img,  "png", f);
		}catch (IOException e) {
			System.out.println("Error: "+e);
		}
	}
	
	/**
	 * Find the min of the map.
	 * 
	 * @return the min of the map
	 */
	public double getMin() {
		double min = this.map[0][0];
		for (double[] row : this.map) {
			for (double item : row) {
				if (item < min)
					min = item;
			}
		}
		return min;
	}
	
	/**
	 * Calculate the average of the map.
	 * 
	 * @return the average of the map
	 */
	public double getAverage() {
		double sum = 0;
		for (double[] row : this.map) {
			for (double item : row) {
				sum+=item;
			}
		}
		System.out.println(sum);
		return sum/(this.size*this.size);
	}
	
	public String toString() {
		StringBuilder r = new StringBuilder();
		for (double[] row : this.map) {
			for (double item : row) {
				if (isInteger(item)) {
					r.append((int)item +" ");
				}
				else {
					r.append(item + " ");
				}
			}
			r.append("\n");
		}
		return r.toString();
	}

}
