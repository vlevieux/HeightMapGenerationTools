/**
 * SquareDiamond.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package models.algorithms;

import java.util.concurrent.ThreadLocalRandom;

import models.Map;

public class SquareDiamond extends AlgorithmModel {

	private  int stepSize;
	private  int halfStepSize;
	private  int variance = 20;
	private  int minRandom = 50;
	private  int maxRandom = 1000;
	public  int progress = 0;
	
	public SquareDiamond(int size){
		super(size);
		this.apply();
		System.out.println(this.map);
		this.generateImage();
	}
	
	public void apply()  {
		map.setZeros();
		size = map.getSize();
		stepSize = size-1;
		
		map.set(0, 0, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
		map.set(size-1, 0, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
		map.set(0, size-1, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
		map.set(size-1, size-1, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
		
		while (stepSize>1) {
			// TODO adapt to size of map with parameters
			this.setProgress(this.getProgress()+100/10);
			halfStepSize = stepSize/2;
			diamond(map);
			square(map);
			stepSize/=2;	
		}
	}
	
	private  void diamond(Map map) {
		double topLeft;
		double topRight;
		double botLeft;
		double botRight;
		double average;
		int rand;
		for (int j = 0;  j<size-1; j+=stepSize) {
			for (int i = 0; i<size-1; i+=stepSize) {
				topLeft = map.get(i,j);
				topRight = map.get(i, j + stepSize);
				botLeft = map.get(i + stepSize, j);
				botRight = map.get(i + stepSize, j + stepSize);
				average = (topLeft + topRight + botLeft + botRight)/4;
				rand = ThreadLocalRandom.current().nextInt(0, variance + 1) - variance /2;
				map.set(i + halfStepSize, j + halfStepSize, average + rand);
			}
		}
	}
	
	private  void square(Map map) {
		int shift = 0;
		int average;
		int rand;
		int n;
		int total;
		for (int j = 0; j<size; j+=halfStepSize) {
			if (shift == 0) {
				shift = halfStepSize;
			} else {
				shift = 0;
			}
			for (int i = shift; i < size; i+=stepSize) {
				n = 0;
				total = 0;
				if (i - halfStepSize > 0) {
					total += map.get(i-halfStepSize, j);
					n+=1;
				}
				if (i + halfStepSize < size) {
					total += map.get(i+halfStepSize, j);
					n+=1;
				}
				if (j-halfStepSize > 0) {
					total += map.get(i,j-halfStepSize);
					n+=1;
				}
				if (j+halfStepSize< size) {
					total += map.get(i,j+halfStepSize);
					n+=1;
			
				}
				average = total / n;
				rand = ThreadLocalRandom.current().nextInt(0, variance + 1) - variance /2;
				map.set(i, j, average + rand);
			}
		}
	}
}
