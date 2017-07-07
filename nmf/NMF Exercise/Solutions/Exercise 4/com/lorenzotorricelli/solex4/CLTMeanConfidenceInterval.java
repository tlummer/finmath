package com.lorenzotorricelli.solex4;

import org.apache.commons.math3.distribution.NormalDistribution;

public class CLTMeanConfidenceInterval extends MeanConfidenceInterval {

	public CLTMeanConfidenceInterval(RandomVariable randomVariable, int sampleSize) {
		this.randomVariable=randomVariable;
		this.sampleSize=sampleSize;
	}
	@Override
	public double getLowerBoundConfidenceInterval( double level ){
		NormalDistribution normal=new NormalDistribution();
		return randomVariable.getSampleMean(sampleSize)-randomVariable.getStdDeviation()/Math.sqrt(sampleSize)*normal.inverseCumulativeProbability((1+level)/2);
		
	}
	
	@Override
	public double getUpperBoundConfidenceInterval( double level){
		NormalDistribution normal=new NormalDistribution();
		return randomVariable.getSampleMean(sampleSize)+randomVariable.getStdDeviation()/Math.sqrt(sampleSize)*normal.inverseCumulativeProbability((1+level)/2);
		
	}
	
	}



