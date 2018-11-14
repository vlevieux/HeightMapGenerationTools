/**
 * ThreadComputing.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package controllers;

import models.Map;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;

public class ThreadComputing implements Runnable {
	
	private Map map;
	private int methodId;
	
	ThreadComputing(Map map, Integer methodId) {
		this.map = map;
		this.methodId = methodId;
	}
	
	public void run() {
		switch (this.methodId) {
		case 0:
			Random.apply(this.map);
			break;
		case 1:
			SquareDiamond.apply(this.map);
			break;
		}	
	}
	
	public int getProgress() {
		return SquareDiamond.progress;
	}
}
