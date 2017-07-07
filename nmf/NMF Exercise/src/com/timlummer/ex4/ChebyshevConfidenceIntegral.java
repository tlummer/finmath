package com.timlummer.ex4;



public class ChebyshevConfidenceIntegral extends MeanConfidenceIntegral {

	public ChebyshevConfidenceIntegral(RandomVariable randomVariable, int sample) {
		this.randomVariable=randomVariable;
		this.sample=sample;
	}
	
	
	@Override
	public double getLowerBoundConfidenceInterval(double level ){
		return randomVariable.getSampleMean(sample)-randomVariable.getStdDeviation()/Math.sqrt((1-level)*sample);
		 
	}
	
	@Override
	public double getUpperBoundConfidenceInterval(double level){
		return randomVariable.getSampleMean(sample)+randomVariable.getStdDeviation()/Math.sqrt((1-level)*sample);
	
}
	
}
