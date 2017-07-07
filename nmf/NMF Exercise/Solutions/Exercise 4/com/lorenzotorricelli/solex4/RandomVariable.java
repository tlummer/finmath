package com.lorenzotorricelli.solex4;

public interface RandomVariable {
	public double generate() throws Exception;
	public double quantileFunction(double x);
	double getMean();
	double densityFunction(double x);
	public double cdfFunction(double x);
	public double getSampleMean(int n);  //note that the implementation of those two is common to all RVs. 
	//so one could argue a better way was to define Random Variable as an abstract class in first place and do the implementation here as non abstract methods
	public double getSampleStdDeviation(int n);
	double getStdDeviation();
	double getParamterUpperBoundConfidenceInterval(double level, MeanConfidenceInterval confidenceIntervalCalculator);  //how do you call these methods?
	double getParamterLowerBoundConfidenceInterval(double level, MeanConfidenceInterval confidenceIntervalCalculator);
	
}
