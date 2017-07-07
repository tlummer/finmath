package com.lorenzotorricelli.solex5;


import com.lorenzotorricelli.solex4.ExponentialRandomVariable;
import com.lorenzotorricelli.solex4.MeanConfidenceInterval;
import com.lorenzotorricelli.solex4.RandomVariable;


public class GammaRandomVariable implements RandomVariable{
	private double lambda;
	private double alpha;
	private final double GammaBound=100;  //bpund for Gamma integral
	private int numberOfSimpsonIntervals=10000;


	GammaRandomVariable(double alpha, double lambda){ //Constructor
		this.alpha=alpha;
		this.lambda=lambda;
	}

	public double getLambda(){ //getter
		return lambda;
	}

	public double getAlpha(){ //getter
		return alpha;
	}



	@Override
	public double generate() throws Exception {  //Aceptance rejection generation
		if(alpha<=1.0){
			Exception exception=
		new Exception("Cannot generate Gamma variables with this AR implemetation for such a value of alpha");
			exception.printStackTrace();
			throw exception;
		}
		double u,y;

		double beta=lambda/alpha;  //minimum beta for which an exponential dominates the Gamma variable
		ExponentialRandomVariable exponential=new ExponentialRandomVariable(beta);
		
		
		
		
		do{

			u=Math.random();
			y=exponential.generate();
		} while(u>Math.exp(y*(beta-lambda))*Math.pow(y, alpha-1) ) ;  //when this condition is met the variate is accepted as a good sample, 
		//and the do-while loop stops
		return y;
	}	

	public double calculateEulerGamma(double alpha){
		return Trapezoid.trapezoidMethod(new GammaIntegrand(alpha), 0.0,GammaBound, numberOfSimpsonIntervals);
	}

	@Override
	public double densityFunction(double x){
		if(x<0) return 0;
		return  Math.pow(lambda, alpha)*Math.pow(x, alpha-1)*Math.exp(- lambda*x)/calculateEulerGamma(alpha);

	}
	//Using abramowitz and stegun 	

	@Override
	public double getMean(){
		return getAlpha()/getLambda() ;
	}



	@Override
	public double getSampleMean(int n){
		double sm=0;
		for(int i=0; i<n; i++)
			try {
				sm+=generate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return sm/n;
	}


	@Override
	public double getStdDeviation() {
		return Math.sqrt(getAlpha())/getLambda();
	}


	@Override
	public double getSampleStdDeviation(int n) {

		double average = getMean();


		double sum = 0.0;
		for(int i=0; i<n; i++) {
			double simulation=0.0;
			try {
				simulation = generate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			double value	= (simulation - average)*( simulation - average); // - errorOfSum;
			sum+= value;
		}
		return Math.sqrt(sum/ (double)(n-1));
	}



	//The following methods have not been implemented



	@Override
	public double getParamterUpperBoundConfidenceInterval(double level,
			MeanConfidenceInterval confidenceIntervalCalculator) {

		return 0;
	}



	@Override
	public double getParamterLoweerBoundConfidenceInterval(double level,
			MeanConfidenceInterval confidenceIntervalCalculator) {
		return 0;
	}

	@Override   //This needs implementation of the incomplete Gamma function!
	public double cdfFunction(double x) {

		return 0;
	}



	@Override 
	public double quantileFunction(double x){ //not known in analytical form
		return 0;
	}   



}

     
	


	


	
	
	
	
