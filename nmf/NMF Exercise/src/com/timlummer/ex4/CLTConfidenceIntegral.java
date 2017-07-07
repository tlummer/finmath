package com.timlummer.ex4;

import org.apache.commons.math3.distribution.NormalDistribution;

public class CLTConfidenceIntegral extends MeanConfidenceIntegral{

public CLTConfidenceIntegral(RandomVariable randomVariable, int sample) {
	this.randomVariable=randomVariable;
	this.sample=sample;
}
@Override
public double getLowerBoundConfidenceInterval( double level ){
	NormalDistribution normal=new NormalDistribution();
	return randomVariable.getSampleMean(sample)-randomVariable.getStdDeviation()/Math.sqrt(sample)*normal.inverseCumulativeProbability((1+level)/2);
	
}

@Override
public double getUpperBoundConfidenceInterval( double level){
	NormalDistribution normal=new NormalDistribution();
	return randomVariable.getSampleMean(sample)+randomVariable.getStdDeviation()/Math.sqrt(sample)*normal.inverseCumulativeProbability((1+level)/2);
	
}

}
