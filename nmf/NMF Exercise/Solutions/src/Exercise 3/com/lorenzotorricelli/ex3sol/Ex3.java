package com.lorenzotorricelli.ex3sol;


public class Ex3 {




	public static void main(String[] args) {


		System.out.println("Exercise 1 \n" );


		long seed=13444;
		int numberOfNumbers=5;

		LCG generator=new LCG(seed);

		try {
			generator.generate(numberOfNumbers);
		} catch (Exception e) {
			e.printStackTrace();
		}



		LCG	generatorStaticallyCreatd = LCG.staticGenerate(numberOfNumbers, seed);



		System.out.println("Simulation of: " + numberOfNumbers + " integers with seed " + seed  +
				" using the Java specifications: " +generator.getRandomNumberSequence());

		System.out.println("Simulation of: " + numberOfNumbers + " integers with seed " + seed  +
				" using the Java specifications: " + generatorStaticallyCreatd.getRandomNumberSequence()  );

		System.out.println("\n" );


		System.out.println("Exercise 2 \n" );

		int numberOfMonteCarloPoints=1000;
		double leftPoint=0;
		double rightPoint=8;
		double integralAnalyticValue=6;


		MonteCarloInvCubRoot mc=
				new MonteCarloInvCubRoot(leftPoint, rightPoint, numberOfMonteCarloPoints);

		double mcIntegral=mc.monteCarloIntegral();

		System.out.println("A Monte Carlo approximation of x^(-1/3) is " + mcIntegral);
		System.out.println("\n" );
		System.out.println("The standard deviation of the integral estimator is " + mc.stDev());
		System.out.println("\n" );
		System.out.println ("The std error with the analytical value is " +  Math.abs(mcIntegral-integralAnalyticValue)/mcIntegral );
		System.out.println("\n" );

		final int NUMBER_OF_SIMULATIONS=1000;

		double integralTolerance=0.1;
		double confidence=0.95;

		int numberOfIntegralPointsToBreach=0;
		double prob=0.0;
		int monteCarloOrderStep=100;



		do{
			prob=MonteCarloInvCubRoot.
					computeBreachProbability(leftPoint, rightPoint, 
							integralAnalyticValue ,integralTolerance, NUMBER_OF_SIMULATIONS , numberOfIntegralPointsToBreach);
			//print(confidence);
			//print(prob);
			numberOfIntegralPointsToBreach+=monteCarloOrderStep;

		} while(prob<confidence);

		System.out.println("The approximate number of points in the Integral calculation to be " + confidence*100 + 
				"% sure of approximating the integral with a " + integralTolerance + " confidence using \n"
				+ NUMBER_OF_SIMULATIONS + " integral simulations  is "  + numberOfIntegralPointsToBreach);
		System.out.println("The theoretical value of  such a number is : " + 
				(rightPoint-leftPoint)*mc.getVarianceOfInverseCubeRootUniformLPRP()/(integralTolerance*integralTolerance*(1-confidence)));


		double confidenceLevel2=0.99;

		System.out.println("\n");

		System.out.println("A lower and upper bound of a " + confidenceLevel2*100 + "% confidence interval using " + NUMBER_OF_SIMULATIONS + " integral simulations are " 
				+ mc.confidenceIntervalLowerBound(confidenceLevel2, leftPoint, rightPoint) + " and " + mc.confidenceIntervalUpperBound(confidenceLevel2, leftPoint, rightPoint) );
	}  

}






