package controllers;

import models.Map;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		Map map = new Map(9);
		map.fill(200);
		System.out.println(map);
		map.changeSize(17);
		map.offset(50);
		System.out.println(map);
		/*System.out.println("Debut");
		Map map = new Map(1025);
		ThreadComputing tc = new ThreadComputing();
		Thread t2 = new Thread(tc);
		t2.start();
		System.out.println("Thread is already running");
		t2.join();
		System.out.println("Thread is finished");
		//System.out.println(map);*/
		
		//map.generateImage();*/
		
		//SquareDiamond.getParameters();
		//SquareDiamond.setParameters(12);
		//SquareDiamond.getParameters();
	}

}
