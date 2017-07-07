package com.timlummer.ex4;

import org.apache.commons.math3.distribution.*;

public class NormalRandomVariable implements RandomVariable{
	
	private double my;
	private double sigma;
	NormalDistribution nd;
	
	public NormalRandomVariable (double my, double sigma){	
		this.my = my;
		this.sigma = sigma;
		nd = new NormalDistribution(my,sigma);
		 }
		
	@Override
	public double generate() {
		return quantileFunction(Math.random());
	}

	@Override
	public double quantileFunction(double x) {
		return nd.inverseCumulativeProbability(x);
	}

	@Override
	public double getMean() {
		// TODO Auto-generated method stub
		return my;
	}

	@Override
	public double densityFunction(double x) {
		return Math.exp(-x*x/2)/Math.sqrt(2*Math.PI);
	}

	@Override
	public double cumulativeDistributionFunction(double x) {
		return  nd.cumulativeProbability(x);
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

		return sigma;
	}

	@Override
	public double getParamterUpperBoundConfidenceInterval(double level,
			MeanConfidenceIntegral confidenceIntervalCalculator) {
		
		return confidenceIntervalCalculator.getLowerBoundConfidenceInterval(level);
	}

	@Override
	public double getParamterLowerBoundConfidenceInterval(double level,
			MeanConfidenceIntegral confidenceIntervalCalculator) {
	
		return confidenceIntervalCalculator.getLowerBoundConfidenceInterval(level);
	}
	
	public double[] generateBoxMuller(){
		
		double[] Z = new double[2];
		double u,v,r,s;
		do{
			u=2*Math.random()-1;
			v=2*Math.random()-1;
			r=u*u+v*v;
		} while(r>1);
		s=Math.sqrt(-2.0*Math.log(r)/r);
		
		Z[0] =sigma*v*s+my;
		Z[1] =sigma*u*s+my;
		
		return Z;
		
		
	}
	
	public double[] generateBivariateNormal(){
		
		double[] Z = new double[2];
			
		Z[0] = this.generate();
		Z[1] = this.generate();
		
		return Z;
		
		
	}
}
