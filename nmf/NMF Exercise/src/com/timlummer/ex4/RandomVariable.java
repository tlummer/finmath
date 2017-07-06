package com.timlummer.ex4;
	public interface RandomVariable {
		public double generate();
		public double quantileFunction(double x);
		double getMean();
		double densityFunction(double x);
		public double cumulativeDistributionFunction(double x);
		public double getSampleMean(int n); 
		public double getSampleStdDeviation(int n);
		double getStdDeviation();
}
