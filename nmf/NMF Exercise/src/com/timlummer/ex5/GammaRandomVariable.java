package com.timlummer.ex5;

import com.timlummer.ex4.*;

public class GammaRandomVariable implements RandomVariable {

	private double gamma;
	private double alpha;
	
	public GammaRandomVariable (double alpha, double gamma){
		this.gamma = gamma;	
		this.alpha = alpha;
	}
	
	@Override
	public double generate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double quantileFunction(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMean() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double densityFunction(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double cumulativeDistributionFunction(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleMean(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleStdDeviation(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getStdDeviation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getParamterUpperBoundConfidenceInterval(double level,
			MeanConfidenceIntegral confidenceIntervalCalculator) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getParamterLowerBoundConfidenceInterval(double level,
			MeanConfidenceIntegral confidenceIntervalCalculator) {
		// TODO Auto-generated method stub
		return 0;
	}

}
