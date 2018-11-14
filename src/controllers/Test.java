package controllers;

import models.Map;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Debut");
		Map map = new Map(1025);
		ThreadComputing tc = new ThreadComputing(map, 1);
		Thread t2 = new Thread(tc);
		t2.start();
		for (int i = 0; i < 25; i++) {
			Thread.sleep(1);
			System.out.println(tc.getProgress());
		}
		System.out.println("Thread is already running");
		t2.join();
		tc.getProgress();
		System.out.println("Thread is finished");
		//System.out.println(map);
		
		map.generateImage();
		
		//SquareDiamond.getParameters();
		//SquareDiamond.setParameters(12);
		//SquareDiamond.getParameters();
	}

}
