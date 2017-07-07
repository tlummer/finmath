package com.lorenzotorricelli.ex6;

import com.lorenzotorricelli.solex5.BiVariateNormalInversion;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.time.TimeDiscretization;

public class Ex6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Exercise 1 \n");

		int numberOfFactors=2;
		int numberOfPaths=1000;


		double initial=0.0; //time discretisation parameters
		int numberOfTimeSteps=1000;
		double deltaT=0.001;


		TimeDiscretization timeDiscretization=new TimeDiscretization(initial, numberOfTimeSteps, deltaT);

		MyBrownianMotion bm=new MyBrownianMotion(timeDiscretization, numberOfFactors, numberOfPaths);
		Generator g=Generator.AR; // (creating the generator);
		bm.generateBrownianMotion(g);


		int pathSelected=1;
		int factorSelected=1;

		System.out.println("This is path number " + pathSelected + " of factor " + factorSelected + " of the Brownian Motion Simulator \n");
		bm.printPath(pathSelected, factorSelected);
		System.out.println("\n");

		System.out.println("Exercise 2");

		RandomVariable[] pathsFirstFactor=bm.getBrownianPathsOfGivenFactor(1);
		RandomVariable[] pathsSecondFactor=bm.getBrownianPathsOfGivenFactor(2);

		System.out.println();

		double[] means=new double[numberOfTimeSteps+1];
		System.out.println(means.length);
		System.out.println(pathsFirstFactor.length);


		for(int i=0; i<means.length; i++){
			means[i]= pathsFirstFactor[i].mult(pathsSecondFactor[i]).getAverage();  //computes the "ultpilication across all the paths, yielding a new RandomVairable object, and then computes the mean
			System.out.println("The mean at time " + i + " is " + " " + means[i]);
		}

		int crossCorrelationTestIndex1= 10; // s
		int crossCorrelationTestIndex2= 20; // t


		double crossCorrelationTest 
= pathsFirstFactor[crossCorrelationTestIndex1].mult(pathsFirstFactor[crossCorrelationTestIndex2]).getAverage();  //computes the cross correlation at certain times for the firs Factor
		System.out.println("The cross correlation at times " + crossCorrelationTestIndex1*deltaT + " and " + crossCorrelationTestIndex2*deltaT + " is " + " " + crossCorrelationTest );

		int samplePath=2;

  //pathwise analysis
		double rightHandSide
		=0.5*( bm.getPath( numberOfTimeSteps,0).get(samplePath)
				*bm.getPath( numberOfTimeSteps,0).get(samplePath)-numberOfTimeSteps*deltaT);

		double leftHandSide=0.0;
		for(int i=0; i<numberOfTimeSteps; i++){
			leftHandSide+=bm.getPath(i,0).get(samplePath)*bm.getBrownianIncrement(i, 0).get(samplePath)	;
		}

		System.out.println("Int_0^T W_t dW_t equals " + leftHandSide + " while (W^2_t-t)/2 equals " + rightHandSide);

//		double rightHandSideExpectation=0.5*( bm.getPath( numberOfTimeSteps,0).getAverage()-numberOfTimeSteps*deltaT);
//		double leftHandSideExpectation=0.0;
//		double[] leftHandSideValues=new double[numberOfPaths];
//
//
//		for(samplePath=0; samplePath <numberOfPaths; samplePath++){
//			for(int i=0; i<numberOfTimeSteps; i++){
//				leftHandSideValues[samplePath]+=bm.getPath(i,0).get(samplePath)*bm.getBrownianIncrement(i, 0).get(samplePath)	;
//			}
//			leftHandSideExpectation+=leftHandSideValues[samplePath];
//		}

		System.out.println("Exercise 3");
		
		double mu=0.1;
		double sigma=0.3;
		double T=1.0;
		int n=10000;
		double delta=T/n;
		double x0=1.0;
		
		System.out.println("Analytical value of E[X_T]:=" + x0*Math.exp(mu*T));
		System.out.println("Analytical value of Var[X_T]:=" + x0*x0*Math.exp(2*mu*T)*(Math.exp(sigma*sigma*T)-1));
		System.out.println("Analytical value of E[X^Delta_n]:=" + x0*Math.pow(1+mu*delta , n));
		
	System.out.println("Analytical value of Var[X^Delta_n]:=" + (x0*x0*Math.pow((1+mu*delta)*(1+mu*delta)+sigma*sigma*delta     ,    n)-  x0*x0*Math.pow((1+mu*delta)*(1+mu*delta) , n) )  );
	//		
	}
}





	
		
		
		
		
	


