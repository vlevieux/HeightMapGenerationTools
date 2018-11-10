/**
 * Map.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package models;

public class Map {
	
	private int size;
	private double[][] map;
	
	Map(int size){
		this.setSize(size);
		map = new double[this.size][this.size];
		setZeros();
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
