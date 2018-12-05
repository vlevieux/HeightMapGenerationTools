package models.algorithms;

import java.util.concurrent.ThreadLocalRandom;

public class Hill extends AlgorithmModel {

	private double kradius;
	private int iter;
	private int maximum;
	
	public Hill(int size) {
		super(size);
	}

	@Override
	public void apply() {
		this.maximum = (int)((double)this.size * this.kradius);
		for (int i = 0; i<iter; i++) {
			if (isCancelled()) {
				break;
			}
			step();
		}
	}
	
	public void step() {
		int x = ThreadLocalRandom.current().nextInt(0, this.size + 1);
		int y = ThreadLocalRandom.current().nextInt(0, this.size + 1);
		int radius = ThreadLocalRandom.current().nextInt(0, this.maximum);
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				if (isCancelled()) {
					break;
				}
				double z = Math.pow(radius, 2) - (Math.pow(x-j, 2) + Math.pow(y-i, 2));
				if (z>=0)
					map.set(i, j, map.get(i, j)+z);
				pointCalculated(this.iter*this.size*this.size);
			}
		}
	}

	public void setParameters(java.util.Map<String,String> parametersMap) {
		super.setParameters(parametersMap);
		this.kradius = Double.valueOf(parametersMap.get("kradius"));
		this.iter = Integer.valueOf(parametersMap.get("iteration"));
	}
}
