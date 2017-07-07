package com.lorenzotorricelli.solex4;

public class ExponentialRandomVariable implements RandomVariable{
	private double lambda;

	public ExponentialRandomVariable(double lambda){ //Constructor
		this.lambda=lambda;
	}

	public double getLambda(){ //geeter
		return lambda;
	}




	@Override
	public double quantileFunction(double x){ //quantile
		return -Math.log(1-x)/lambda;

	}

	@Override
	public double generate(){  //generation
		return quantileFunction(Math.random());
	}	


	@Override
	public double getMean(){
		return 1.0/getLambda();
	}

	@Override
	public double getStdDeviation(){
		return 1.0/getLambda();

	}


	@Override
	public double getSampleMean(int n){
		double sm=0;
		for(int i=0; i<n; i++)
			sm+=generate();
		return sm/n;
	}


	@Override
	public double densityFunction(double x){
		return lambda*Math.exp(-lambda*x);
	}

	@Override
	public double getParamterUpperBoundConfidenceInterval(double level, MeanConfidenceInterval confidenceIntervalCalulator) { //how do you call these methods?
		
		return 1.0/confidenceIntervalCalulator.getLowerBoundConfidenceInterval(level);
	}

	@Override
	public double getParamterLoweerBoundConfidenceInterval(double level, MeanConfidenceInterval confidenceIntervalCalulator) {
		return 1.0/confidenceIntervalCalulator.getUpperBoundConfidenceInterval(level);
		}

	@Override
	public double cdfFunction(double x) {
		return (1-Math.exp(-lambda*x));
	}

	@Override
	public double getSampleStdDeviation(int n) {
		double value=0;
		double mean=getMean();
		double sm=0;
		 for(int i=0; i<n; i++){
    		 value=generate();
		     sm+=(value-mean)*(value-mean);
		 }
		return Math.sqrt(sm/(n-1));
	}





}







 



