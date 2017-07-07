package com.lorenzotorricelli.ex10;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import com.lorenzotorricelli.solex4.NormalRandomVariable;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;

public class LevySimulation extends JumpProcess{

	private RandomVariable[] jumpNumber;
	private RandomVariable[] jumpSizes;

	public LevySimulation(double lambda, double mu, double jumpMean, double jumpStdv, double initialValue, double finalTime, int numberOfTimeSteps, int numberOfRealisations){
		super(lambda, mu, jumpMean, jumpStdv,     finalTime, initialValue, numberOfTimeSteps, numberOfRealisations); 
		jumpNumber=new RandomVariable[numberOfTimeSteps+1];
		jumpSizes=new RandomVariable[numberOfTimeSteps+1];

	}

	void generate(){

		path[0]=new RandomVariable(0.0, initialValue);  //initialises the processs
		double numberOfJumps;
		double[][] jumpNumbersVector=new double[times.getNumberOfTimes()][numberOfRealisations];
		double[][] jumpSizesVector=new double[times.getNumberOfTimes()][numberOfRealisations];
		jumpSizesVector[0][0]=0.0;
		jumpNumbersVector[0][0]=0.0;



		NormalRandomVariable jump= new NormalRandomVariable(jumpMean, jumpStdv);

		jumpSizes[0]=new RandomVariable(0.0, 0.0);
		jumpNumber[0]=new RandomVariable(0.0, 0.0);




		for(int timeIndex=1; timeIndex <times.getNumberOfTimes(); timeIndex++ ){ //generates number jumps at every time steps

			path[timeIndex]=path[timeIndex-1].add(mu*times.getTimeStep(timeIndex-1)); // generates drift



			Poisson poisson=new Poisson(lambda*times.getTimeStep(timeIndex-1));  //poisson variable determining the number of jumps in the discretisation time gap

			for(int realisationIndex=0; realisationIndex < numberOfRealisations; realisationIndex++) {	
				jumpSizesVector[timeIndex][realisationIndex]=0.0; //initilaizes at 0 the number of jumps
				jumpNumbersVector[timeIndex][realisationIndex]=0.0; //initilaizes at 0 the number of jumps

				numberOfJumps=poisson.generatePoisson(); //here you can complicate as you like; for example you can store the number of jumps in an array of Random Variables and define them as a field

				for(int jumpIndex=0; jumpIndex < numberOfJumps; jumpIndex++ ){  //loops across all the realisations and simulates the jumps for the given time index
					jumpSizesVector[timeIndex][realisationIndex]+=jump.generate(); // increments the total jump size at each instant
					jumpNumbersVector[timeIndex][realisationIndex]++; //increments the jump counting array
				}	
			}; 


			jumpNumber[timeIndex]=new RandomVariable(timeIndex*times.getTimeStep(timeIndex-1), jumpNumbersVector[timeIndex]); //wraps in a random variable the jump numbers

			jumpSizes[timeIndex]=new RandomVariable(timeIndex*times.getTimeStep(timeIndex-1), jumpSizesVector[timeIndex]); //wraps in a random variable the jump sizes

			path[timeIndex]=path[timeIndex].add(jumpSizes[timeIndex]);  //adds the jumps
		}
	}





	double[] getPath(int realisation){
		double[] pathI=new double[times.getNumberOfTimes()];
		for (int timeIndex=0; timeIndex<pathI.length; timeIndex++){
			pathI[timeIndex]=path[timeIndex].get(realisation);
		}
		return pathI;
	}

	double[] getJumpNumberForPath(int realisation){
		double[] pathI=new double[times.getNumberOfTimes()];
		for (int timeIndex=0; timeIndex<pathI.length; timeIndex++){
			pathI[timeIndex]=jumpNumber[timeIndex].get(realisation);
		}
		return pathI;
	}

	double[] getJumpSizesForPath(int realisation){
		double[] pathI=new double[times.getNumberOfTimes()];
		for (int timeIndex=0; timeIndex<pathI.length; timeIndex++){
			pathI[timeIndex]=jumpSizes[timeIndex].get(realisation);
		}
		return pathI;
	}

	void getPrintablePath(int realisation){
		NumberFormat formatDec2 = new DecimalFormat("0.0000");

		double[] printable=getPath(realisation);
		for(int i=0; i<printable.length; i++){
			System.out.println(formatDec2.format(printable[i]) + "\t");

		}
	}

	RandomVariableInterface getFinalValue(){
		return path[times.getNumberOfTimes()-1];
	}
}
	 
