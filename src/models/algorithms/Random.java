package models.algorithms;

import java.util.concurrent.ThreadLocalRandom;

import models.Map;

public class Random {
	
	private static int size;
	private static int minRandom = 0;
	private static int maxRandom = 255;
	
	static public void apply(Map map) {
		size = map.getSize();
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				map.set(i, j, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
			}
		}
	}
}
