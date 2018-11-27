package models.algorithms;

import java.util.concurrent.ThreadLocalRandom;

import models.Map;

public class Random extends AlgorithmModel {
	
	private static int minRandom = 0;
	private static int maxRandom = 255;
	
	public Random(int size){
		super(size);
	}
	public void apply() {
		size = this.map.getSize();
		int pointDone = 0;
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				pointDone++;
				this.setProgress((int)(100*pointDone/(this.size*this.size)));
				this.map.set(i, j, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
			}
		}
	}

}
