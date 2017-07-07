package com.lorenzotorricelli.ex6;


import com.lorenzotorricelli.solex4.NormalRandomVariable;
import com.lorenzotorricelli.solex5.*;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.time.TimeDiscretization;

public class MyBrownianMotion  {


	private TimeDiscretization	times;

	private int			numberOfFactors;
	private int			numberOfPaths;
	private double      initialValue;




	private	RandomVariable[][]	brownianIncrements;      //Matrix of RandomVariableInterface types which is itself a vector; a 3-tensor, an array of Vectors. 
	//Dimensions are the time discretisation, the factor and the number of paths
	private	RandomVariable[][]	brownianPaths;

	public MyBrownianMotion(           // Constructor
			TimeDiscretization timeDiscretization,
			int numberOfFactors,
			int numberOfPaths) {
		this.times = timeDiscretization;
		this.numberOfFactors	= numberOfFactors;
		this.numberOfPaths		= numberOfPaths;
	}

public MyBrownianMotion(
		double initialValue,
	int numberOfTimeSteps,
	double deltaT, 
	int numberOfFactors,
	int numberOfPaths){
	this.numberOfFactors	= numberOfFactors;
	this.numberOfPaths		= numberOfPaths;
	times=new TimeDiscretization(initialValue, numberOfTimeSteps, deltaT);

}


//Generating core method

	public void generateBrownianMotion(Generator generator) {      
		int numberOfTimeSteps=times.getNumberOfTimeSteps(); //number of time steps
		int numberOfTimes=times.getNumberOfTimes();  //number of TIMES = numberOfTimesteps1
		double[][][] brownianIncrements3Array = new double[numberOfTimeSteps][numberOfFactors][numberOfPaths];  //3-tensor. Dimensions are: discetisations, path, and factor
		double[][][] brownianPaths3Array = new double[numberOfTimes][numberOfFactors][numberOfPaths];  //paths have numberOfTimesteps +1 points
		NormalRandomVariable normalRv=new NormalRandomVariable(0.0, 1.0);

		double[] volatilities = new double[numberOfTimeSteps];  //allocate space for volatilities array
		for(int i=0; i<volatilities.length; i++) {
			volatilities[i] = Math.sqrt(times.getTimeStep(i));  //here is where the structure of the TimeDiscreisation is used; in premultiplication; the mesh may not be equally sized.
		}   


		//loop
		for(int j=0; j<numberOfPaths; j++) {  
			for(int h=0; h<numberOfFactors; h++) {

				brownianPaths3Array[0][h][j]=initialValue; 
				// Generate uncorrelated Brownian increments according to different routines
				{   
					switch( generator ){              // A FACTORY !!!!
					default:
					case INV: 
						for(int i=0; i<numberOfTimeSteps; i++){
							brownianIncrements3Array[i][h][j] = 
									normalRv.generate()* volatilities[i];
							brownianPaths3Array[i+1][h][j]=brownianPaths3Array[i][h][j]+brownianIncrements3Array[i][h][j];
						}

					case BM_AR: 	for(int i=0; i<numberOfTimeSteps; i++){
						brownianIncrements3Array[i][h][j] = 
								normalRv.generateARBoxMuller().getFirstValue()* volatilities[i];
						brownianPaths3Array[i+1][h][j]=brownianPaths3Array[i][h][j]+brownianIncrements3Array[i][h][j];
					}

					case AR: for(int i=0; i<numberOfTimeSteps; i++){
						brownianIncrements3Array[i][h][j] = 
								normalRv.generateAR()* volatilities[i];
						brownianPaths3Array[i+1][h][j]=brownianPaths3Array[i][h][j]+brownianIncrements3Array[i][h][j];
					}
					case BM:  
						for(int i=0; i<numberOfTimeSteps; i++){
							brownianIncrements3Array[i][h][j] = 
									normalRv.generateARBoxMuller().getFirstValue()* volatilities[i];
							brownianPaths3Array[i+1][h][j]=brownianPaths3Array[i][h][j]+brownianIncrements3Array[i][h][j];
						}
					}

				}			
			}
		}

		// Allocate memory for RandomVariable wrapper objects.
		brownianIncrements = new RandomVariable[numberOfTimeSteps][numberOfFactors];
		brownianPaths = new RandomVariable[numberOfTimeSteps+1][numberOfFactors];

		// Wrap the values in RandomVariable objects
		for(int j=0; j<numberOfFactors; j++) {
			brownianPaths[0][j]=new RandomVariable(0, initialValue); //using overloaded constructor of Random variable in case the RV is indeed a constant
			for(int timeIndex=0; timeIndex<numberOfTimeSteps; timeIndex++) {
				brownianIncrements[timeIndex][j] =new RandomVariable(times.getTime(timeIndex), brownianIncrements3Array[timeIndex][j]);        //the random variable represents the path; the time is passed from the timediscretization fields
				brownianPaths[timeIndex+1][j] =new RandomVariable(times.getTime(timeIndex+1), brownianPaths3Array[timeIndex+1][j]);   //be careful on the indexes here
			}
		}
	}


	public TimeDiscretization getTimeDiscretization() {
		return times;
	}








	public RandomVariable[][] getBrownianIncrements() {
		return brownianIncrements;
	}






	public RandomVariable[] getFactor(int factor) {
		 RandomVariable[] paths=new RandomVariable[times.getNumberOfTimes()];
		 for (int i=0; i< paths.length; i++)     //you must copy the relevant dimension in another array of RandomVariable type
			 paths[i]=brownianPaths[i][factor-1];
		 return paths;
	}




	public double[] getPath(int factor, int path) {
		  double[] paths=new double[times.getNumberOfTimes()];
		 for (int i=0; i< paths.length; i++)    
			 paths[i]=brownianPaths[i][factor-1].get(path-1);
		 return paths;
	}




	public RandomVariable getBrownianIncrement(int timeIndex, int factor) {  //gets the increment of a factor at a time


		return brownianIncrements[timeIndex][factor];
	}

	public RandomVariable getSimulations(int timeIndex, int factor) { // gets a whole path
		return brownianPaths[timeIndex][factor];
	}

	public void printPath(int pathnumber, int factor){
		for(int i=0; i < times.getNumberOfTimes(); i++){
			System.out.println(brownianPaths[i][factor-1].get(pathnumber-1));
		}
	}

	public void printIncrement(int timeindex, int factor, int pathnumber){
		System.out.println(brownianIncrements[timeindex][factor-1].get(pathnumber-1));			

	}



}



