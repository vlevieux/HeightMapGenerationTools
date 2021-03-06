package models.algorithms;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Random extends AlgorithmModel {
	
	private int minRandom = 0;
	private int maxRandom = 255;
	
	public Random(int size){
		super(size);
	}
	public void apply() {
		
		size = this.map.getSize();
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				if (isCancelled()) {
					break;
				}
				pointCalculated();
				this.map.set(i, j, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
			}
		}
	}
	
	@Override
	public void setParameters(Map<String, String> parametersMap) {
		super.setParameters(parametersMap);
		this.minRandom = Integer.valueOf(parametersMap.get("min"));
		this.maxRandom = Integer.valueOf(parametersMap.get("max"));
	}
	
}
