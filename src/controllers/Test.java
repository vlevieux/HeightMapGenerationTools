package controllers;

import models.Map;
import models.algorithms.SquareDiamond;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map map = new Map(257);
		map.set(2, 2, 5);
		System.out.println(map);
		map.setZeros();
		map.set(-1, 2, 5);
		System.out.println(map);
		map.setZeros();
		map.set(4, 4, 5);
		System.out.println(map);
		
		map.setZeros();
		SquareDiamond.apply(map);
		System.out.println(map);
		map.generateImage();
	}

}
