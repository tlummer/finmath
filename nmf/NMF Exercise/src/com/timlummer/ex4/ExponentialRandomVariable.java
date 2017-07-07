package com.timlummer.ex4;

public class ExponentialRandomVariable implements RandomVariable{

private double lambda;

public ExponentialRandomVariable(double lambda){
	this.lambda=lambda;
}

@Override
public double generate() {
	return quantileFunction(Math.random());
}

@Override
public double quantileFunction(double x) {
	return -Math.log(1-x)/lambda;
}

@Override
public double getMean() {
	return 1.0/this.lambda;
}

@Override
public double densityFunction(double x) {
	return lambda*Math.exp(-lambda*x);
}

@Override
public double cumulativeDistributionFunction(double x) {
	return (1-Math.exp(-lambda*x));
}

@Override
public double getSampleMean(int n) {
	double sum=0;
	for(int i=0; i<n; i++) {
		sum+= this.generate();
	}
		return sum/n;

}

@Override
public double getSampleStdDeviation(int n) {
	
	double mean = getSampleMean(n);
	double sum = 0.0;
	
	for(int i=0; i<n; i++) {
		double x = this.generate();
		sum += (x-mean)*(x-mean);
	}
	
	return Math.sqrt(sum/(n-1));
}

@Override
public double getStdDeviation() {
	return 1/this.lambda;
}

@Override
public double getParamterUpperBoundConfidenceInterval(double level,
		MeanConfidenceIntegral confidenceIntervalCalculator) {

	return 1.0/confidenceIntervalCalculator.getUpperBoundConfidenceInterval(level);
}

@Override
public double getParamterLowerBoundConfidenceInterval(double level,
		MeanConfidenceIntegral confidenceIntervalCalculator) {
	// TODO Auto-generated method stub
	return 1.0/confidenceIntervalCalculator.getLowerBoundConfidenceInterval(level);
}
}
