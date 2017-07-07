package com.lorenzotorricelli.solex4;

public class ChebyshevMeanConfidenceInterval extends MeanConfidenceInterval {

	
	public ChebyshevMeanConfidenceInterval(RandomVariable randomVariable, int sampleSize) {
		this.randomVariable=randomVariable;
		this.sampleSize=sampleSize;
	}
	
	
	@Override
	public double getLowerBoundConfidenceInterval(double level ){
		return randomVariable.getSampleMean(sampleSize)-randomVariable.getStdDeviation()/Math.sqrt((1-level)*sampleSize);
		 
	}
	
	@Override
	public double getUpperBoundConfidenceInterval(double level){
		return randomVariable.getSampleMean(sampleSize)+randomVariable.getStdDeviation()/Math.sqrt((1-level)*sampleSize);
	
}
	
}


