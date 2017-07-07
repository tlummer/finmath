package com.lorenzotorricelli.ex3sol;
import static java.lang.Math.*;

public class MonteCarloInvCubRoot{

	private int numberOfMonteCarloPoints;
	private double[] values;
	private double leftPoint;
	private double rightPoint;
	private final double varianceOfInverseCubeRootUniformLPRP=0.1875;





	public double getVarianceOfInverseCubeRootUniformLPRP() {
		return varianceOfInverseCubeRootUniformLPRP;
	}



	MonteCarloInvCubRoot(double a, double b,  int numberOfMonteCarloPoints){
		this.numberOfMonteCarloPoints=numberOfMonteCarloPoints;
		this.values=new double[numberOfMonteCarloPoints];
		leftPoint=a;
		rightPoint=b;
		for(int i=0; i<values.length; i++){
			double x=a+(b-a)*random();
			//			print(x);
			values[i]=1.0/Math.pow(x,1.0/3);
			//		     print(values[i]);
		}
	}

	public double monteCarloIntegral(){
		double sum=0;
		for(int i=0; i<values.length; i++){
			sum+=values[i];

		}return (rightPoint-leftPoint)*sum/numberOfMonteCarloPoints;
	}

	public double stDev(){
		double sum=0;
		double mean=monteCarloIntegral()/(rightPoint-leftPoint);
		for(int i=0; i<values.length; i++){
			sum+=(values[i]-mean)*(values[i]-mean);

		}return sqrt(sum/(numberOfMonteCarloPoints-1));
	}






	public static double computeBreachProbability(double a, double b, double analyticValue,
			double interval, int simulations, int points){

		double sum=0.0;
		for(int i=0; i<simulations; i++){
			MonteCarloInvCubRoot integral=new MonteCarloInvCubRoot(a,b, points);

			if( integral.monteCarloIntegral() < interval + analyticValue && integral.monteCarloIntegral() > interval -  analyticValue) 
				sum++;
		}
		return sum/simulations;
	}

	public double confidenceIntervalLowerBound(double alpha, double a, double b){  //stdDev(f_(x_i)=0.1875, analytically calulcated. Alternatively use the 
		//nmethod stDev()
		return monteCarloIntegral()- (b-a)*sqrt(varianceOfInverseCubeRootUniformLPRP)/sqrt(alpha*numberOfMonteCarloPoints) ;  //Chebyshev bounds
	}
	public double confidenceIntervalUpperBound(double alpha, double a, double b){
		return monteCarloIntegral()+ (b-a)*sqrt(varianceOfInverseCubeRootUniformLPRP)/sqrt(alpha*numberOfMonteCarloPoints) ;

	}

}
