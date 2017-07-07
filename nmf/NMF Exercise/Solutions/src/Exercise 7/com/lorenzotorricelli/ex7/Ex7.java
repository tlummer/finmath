package com.lorenzotorricelli.ex7;



public class Ex7 {

	public static void main(String[] args) {


		System.out.println("Exercise 1");

		double mu=0.1;
		double sigma=0.3;
		double T=1.0;
		int n=100;
		double delta=T/n;
		double x0=1.0;

		System.out.println("Analytical value of E[X_T]:=" + x0*Math.exp(mu*T));
		System.out.println("Analytical value of Var[X_T]:=" + x0*x0*Math.exp(2*mu*T)*(Math.exp(sigma*sigma*T)-1));
		System.out.println("Analytical value of E[X^Delta_n]:=" + x0*Math.pow(1+mu*delta , n));

		System.out.println("Analytical value of Var[X^Delta_n]:=" + (x0*x0*Math.pow((1+mu*delta)*(1+mu*delta)+sigma*sigma*delta,n)
				-  x0*x0*Math.pow((1+mu*delta)*(1+mu*delta) , n) )  );  
		//

		int numberOfSimulations=10000;

		int seed=123;

		GBMEuler euler=new GBMEuler(n, numberOfSimulations, sigma , mu, x0,T, seed);

		euler.generateEuler();

		System.out.println("The average of the simulations at time T is: " + euler.getFinalValue().getAverage());

		System.out.println("The variance of the simulations at time T is: " + euler.getFinalValue().getVariance());

		int samplePath=1;

		euler.printAPath(samplePath);
		

		double[][] histogramOfMeans=new double[2][numberOfSimulations];

		int standardDeviations=3;
		int numberOfHistogramPoints=20;
		
		histogramOfMeans=euler.getFinalValue().sub(euler.getFinalValue().getAverage()).getHistogram(numberOfHistogramPoints, standardDeviations);


		System.out.println("\n");
		

		System.out.println(" Histogram for the centered distribution"  );

		System.out.println("\n");
		
		
		for(int binIndex=0; binIndex<histogramOfMeans[1].length; binIndex++){
			System.out.println(histogramOfMeans[1][binIndex]);
		}
		System.out.println("\n");




	}


}


