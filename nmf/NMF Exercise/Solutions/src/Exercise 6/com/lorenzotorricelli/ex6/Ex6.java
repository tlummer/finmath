package com.lorenzotorricelli.ex6;



import net.finmath.time.TimeDiscretization;

public class Ex6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Exercise 1 \n");

		int numberOfFactors=2;
		int numberOfPaths=1000;


		double initial=0.0; //time discretisation parameters
		int numberOfTimeSteps=1000;
		double timeHorizon=1.0;
		double deltaT=timeHorizon/numberOfTimeSteps;


		TimeDiscretization timeDiscretization=new TimeDiscretization(initial, numberOfTimeSteps, deltaT);

		MyBrownianMotion bm=
			new MyBrownianMotion(timeDiscretization, numberOfFactors, numberOfPaths);
		Generator g=Generator.AR; // (creating the generator);
		bm.generateBrownianMotion(g);


		int pathSelected=1;
		int factorSelected=1;

		System.out.println("This is path number " + pathSelected + " of factor " + factorSelected + " of the Brownian Motion Simulator \n");
		
		bm.printPath(pathSelected, factorSelected);
		
		System.out.println("\n");

		System.out.println("Exercise 2");

		
		System.out.println();

		double[] means=new double[numberOfTimeSteps+1];


		for(int timeIndex=0; timeIndex<means.length; timeIndex++){
			means[timeIndex]= bm.getSimulations(timeIndex,0).
					mult(bm.getSimulations(timeIndex,1)).getAverage();  //computes the "ultpilication across all the paths, yielding a new RandomVairable object, and then computes the mean
			System.out.println("The correlation of the two factors at time " 
			+ timeDiscretization.getTime(timeIndex) + " is " + " " + means[timeIndex]);
		}

		double  s=0.1;
		double t=0.2;
		int crossCorrelationTestIndex1= timeDiscretization.getTimeIndex(s); // s
		int crossCorrelationTestIndex2= timeDiscretization.getTimeIndex(t); // t
		
		//You will see how much easier is to extract values in the BrownianMotion finmath class


		double crossCorrelationTest 
		=bm.
		getSimulations(crossCorrelationTestIndex1,0).
		mult(bm.getSimulations(crossCorrelationTestIndex2,0)).getAverage();  //computes the cross correlation at certain times for the firs Factor
		
		System.out.println("\n");

		
		System.out.println("The serial correlation at times " + s + " and " + t + " is " + " " + crossCorrelationTest );

		int samplePath=2;

		//pathwise analysis
		double rightHandSide
		=0.5*( bm.getSimulations( numberOfTimeSteps,0).get(samplePath)
			*bm.getSimulations( numberOfTimeSteps,0).get(samplePath)-numberOfTimeSteps*deltaT);

		double leftHandSide=0.0;
		for(int i=0; i<numberOfTimeSteps; i++){
			leftHandSide+=bm.getSimulations(i,0).get(samplePath)*bm.getBrownianIncrement(i, 0).get(samplePath)	;
		}

		System.out.println("\n");

		
		System.out.println("Int_0^T W_t dW_t equals " + leftHandSide + " while (W^2_t-t)/2 equals " + rightHandSide);



	}
}





	
		
		
		
		
	


