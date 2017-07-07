package com.lorenzotorricelli.ex6;

import com.lorenzotorricelli.solex4.BoxMuller;
import com.lorenzotorricelli.solex5.BiVariateNormalAR;
import com.lorenzotorricelli.solex5.BiVariateNormalInversion;
import com.lorenzotorricelli.solex5.BivariateNormalRandomVariableGenerator;
import com.lorenzotorricelli.solex5.BoxMullerAR;
import com.lorenzotorricelli.solex5.ReturnPair;

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






	public void generateBrownianMotion(Generator generator) {      
		int numberOfTimeSteps=times.getNumberOfTimeSteps(); //number of time steps
		int numberOfTimes=times.getNumberOfTimes();  //number of TIMES = numberOfTimesteps1
		double[][][] brownianIncrements3Array = new double[numberOfTimeSteps][numberOfFactors][numberOfPaths];  //3-tensor. Dimensions are: discetisations, path, and factor
		double[][][] brownianPaths3Array = new double[numberOfTimes][numberOfFactors][numberOfPaths];  //paths have numberOfTimesteps +1 points
        BivariateNormalRandomVariableGenerator selectedGenerator=null;
		
		double[] volatilities = new double[numberOfTimeSteps];  //allocate space for volatilities array
		for(int i=0; i<volatilities.length; i++) {
			volatilities[i] = Math.sqrt(times.getTimeStep(i));  //here is where the structure of the TimeDiscreisation is used; in premutliplication; the mesh may not be equally sized.
		}   


		//loop
		for(int j=0; j<numberOfPaths; j++) {  
			for(int h=0; h<numberOfFactors; h++) {
                    
				brownianPaths3Array[0][h][j]=initialValue; 
				// Generate uncorrelated Brownian increment
				for(int i=0; i<numberOfTimeSteps; i++) {   
                    switch( generator ){              // A FACTORY !!!!
                    default:
                    case INV: selectedGenerator=new BiVariateNormalInversion(0.0, 1.0);
                    case BM_AR: selectedGenerator=new BoxMullerAR(0.0, 1.0); 
                    case AR: selectedGenerator=new BiVariateNormalAR(0.0, 1.0);
                    case BM: selectedGenerator=new BoxMuller(0.0, 1.0);
                    }
					brownianIncrements3Array[i][h][j] = 
				selectedGenerator.generate().getFirstPoint() * volatilities[i];
					brownianPaths3Array[i+1][h][j]=brownianPaths3Array[i][h][j]+brownianIncrements3Array[i][h][j];

				}			
			}
		}

		// Allocate memory for RandomVariable wrapper objects.
		brownianIncrements = new RandomVariable[numberOfTimeSteps][numberOfFactors];
		brownianPaths = new RandomVariable[numberOfTimeSteps+1][numberOfFactors];

		// Wrap the values in RandomVariable objects
		for(int j=0; j<numberOfFactors; j++) {
			brownianPaths[0][j]=new RandomVariable(0, initialValue); //using overloaded constructor of Random variable in case the RV is indeed a constant
			for(int i=0; i<numberOfTimeSteps; i++) {
				brownianIncrements[i][j] =new RandomVariable(i+1, brownianIncrements3Array[i][j]);        //the random variable represents the path; the time is passed from the timediscretization fields
				brownianPaths[i+1][j] =new RandomVariable(i+1, brownianPaths3Array[i+1][j]);   //be careful on the indexes here
			}
		}// same as yesterdays
	}


	public TimeDiscretization getTimeDiscretization() {
		return times;
	}








	public RandomVariable[][] getBrownianIncrements() {
		return brownianIncrements;
	}






	public RandomVariable[] getBrownianPathsOfGivenFactor(int factor) {
		              // brownianPaths[][factor-1]; Java is not happy with this, it is not an array to her.
		 RandomVariable[] paths=new RandomVariable[times.getNumberOfTimes()];
		 for (int i=0; i< paths.length; i++)
			 paths[i]=brownianPaths[i][factor-1];
		 return paths;
	}









	public RandomVariable getBrownianIncrement(int timeIndex, int factor) {  //gets the increment of a factor at a time


		return brownianIncrements[timeIndex][factor];
	}

	public RandomVariable getPath(int timeIndex, int factor) { // gets a whole path
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



