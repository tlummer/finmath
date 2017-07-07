package com.lorenzotorricelli.ex10;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import net.finmath.montecarlo.RandomVariable;


public class Exercise10 {
	
	public static void main(String[] args){
		
   double lambda=4.0;
   double mu=0.0;
   double jumpMean=-0.2;
   double jumpStdv=0.3;
   
   double initialValue=0.0;
   
   double finalTime=1.0;
   int  numberOfRealisations=100000;
   int numberOfTimeSteps=1;
   
   
   int samplePath=0;
   
   
   System.out.println("Exercise 1");
   
   Poisson poisson=new Poisson(lambda);
   
   
   double sum=0.0;
   
   for (int i=0; i<100000; i++){
	   sum+=poisson.generatePoisson();
   }
   
   System.out.println("Testing the mean: it is " + sum/100000);
   
   
   
      
   System.out.println("Exercise 2");
   
   
   
   ExponentialSum expSum= new ExponentialSum(lambda,  mu, jumpMean,  jumpStdv,  finalTime, initialValue, numberOfTimeSteps, 1);
      ConditionalPoisson conditional= new ConditionalPoisson(lambda,  mu, jumpMean,  jumpStdv,  finalTime, initialValue, numberOfTimeSteps, 1);
      LevySimulation levy= new LevySimulation(lambda,  mu, jumpMean,  jumpStdv,  finalTime, initialValue, numberOfTimeSteps, numberOfRealisations);
      
      System.out.println("\n");
      System.out.println("Method 1: exponential waiting times");
      System.out.println("\n");
      long lStartTime = System.currentTimeMillis();
    
	expSum.generate();
	long lEndTime = System.currentTimeMillis();

	System.out.println("Generation time " + (lEndTime-lStartTime) + " ms" );

	
	System.out.println("Jump times" + expSum.getJumpTimes());
	System.out.println(
	"Jumps" + expSum.getJumps());

	System.out.println(
			"A sample Path" + Arrays.toString(expSum.getPath(samplePath)));

	   System.out.println("\n");
	
	  System.out.println("Method 2: conditioning on the Poisson Realisation");
	   
	  System.out.println("\n"); 
		
	  lStartTime = System.currentTimeMillis();
		conditional.generate();
		
		lEndTime = System.currentTimeMillis();
		
		System.out.println("Generation time " + (lEndTime-lStartTime) + " ms" );
		 
		System.out.println("A sample Path: " + Arrays.toString(conditional.getPath(samplePath)));
		System.out.println("Jump Times: " + conditional.getJumpTimes());
		System.out.println("Jump Sizes: " + conditional.getJumpSizes());
	
		System.out.println("\n");
		
	  System.out.println("Method 3: Levy process realisations using Random Variable");
       System.out.println("\n");
	  
	    lStartTime = System.currentTimeMillis();        //generating using Levy Structure
		levy.generate();
		lEndTime = System.currentTimeMillis();
		System.out.println("Generation time of Levy process: " + (lEndTime-lStartTime) + " ms" );

		System.out.println("A sample Path: " + Arrays.toString(levy.getPath(samplePath)));
		System.out.println("Jump Numbers: " + Arrays.toString(levy.getJumpNumberForPath(samplePath)));
		System.out.println("Jump Sizes: " + Arrays.toString(levy.getJumpSizesForPath(samplePath)));
		
		//double[] intervalPoints=new double[100];
		
	//	for(int i=0; i<5; i+=0.1)
//			intervalPoints[i]=i-5;
		
		int intervalPoints=500;
		double stdDeviations=5;
		
			NumberFormat formatDec2 = new DecimalFormat("0.0000");

			 System.out.println("\n");
				
		 System.out.println("Histogram: exponential Waiting time");
		 
		 

		 double[] expSumRealisations=new double[numberOfRealisations];      //Histogram for ExpSum
		    for(int i=0; i<numberOfRealisations; i++){
		    	expSum.generate();
		    	expSumRealisations[i]=expSum.getFinalValue(samplePath);
		    }
		   
		    RandomVariable expSumRalisationRv=new RandomVariable(0.0, expSumRealisations);
		    double[] expSumHistogram= expSumRalisationRv.getHistogram(intervalPoints, stdDeviations)[1];
			   
	           
		   for(int i=0; i<intervalPoints; i++){
				System.out.println(formatDec2.format(expSumHistogram[i]) + "\t");

			}
		   System.out.println("\n");
			
			 System.out.println("Histogram: conditional Poisson");
			 
		 
			
			 double[] conditionalRealisations=new double[numberOfRealisations];      //Histogram for Conditional
		    for(int i=0; i<numberOfRealisations; i++){
		    	conditional.generate();
		    	conditionalRealisations[i]=conditional.getFinalValue(samplePath);
		    }
		   
		    RandomVariable conditionalRalisationRv=new RandomVariable(0.0, conditionalRealisations);
	           
		   double[] conditionalHistogram= conditionalRalisationRv.getHistogram(intervalPoints, stdDeviations)[1];
		    
		   for(int i=0; i<intervalPoints; i++){
			   
				System.out.println(formatDec2.format(conditionalHistogram[i]) + "\t");

			}
		   
			 System.out.println("\n");
			 
			 System.out.println("Histogram: Levy Process");
			 
			 double[] levyHistogram= levy.getFinalValue().getHistogram(intervalPoints, stdDeviations)[1];
			 
				for(int i=0; i<intervalPoints; i++){   //Histogram for Levy
					System.out.println(formatDec2.format(levyHistogram[i]) + "\t");

				}
		
		
		   
	}
}	
	
	    
		
			//	System.out.println( Arrays.toString(levy.getFinalValue().getHistogram(intervalPoints, stdDeviations)[1]) );
//	
//		 System.out.println("\n");    //printable stuff
//		  expSum.getPrintablePath(samplePath);
//			
// System.out.println("\n");
//		  conditional.getPrintablePath(samplePath);
//		 System.out.println("\n");
//			levy.getPrintablePath(samplePath);
//					  
//
//
//	

	

