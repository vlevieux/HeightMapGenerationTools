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
		int pointDone = 0;
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				pointDone++;
				this.setProgress((double)(pointDone/((double)this.size*this.size)));
				this.publishProgress(this.pB::setProgress);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.map.set(i, j, ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1));
			}
		}
	}
	
	@Override
	public void getParameters() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setParameters(Map<String, String> parametersMap) {
		this.minRandom = Integer.valueOf(parametersMap.get("min"));
		this.maxRandom = Integer.valueOf(parametersMap.get("max"));
	}
	
}
