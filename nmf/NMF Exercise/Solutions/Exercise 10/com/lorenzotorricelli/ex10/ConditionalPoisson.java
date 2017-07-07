package com.lorenzotorricelli.ex10;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.lorenzotorricelli.solex4.NormalRandomVariable;

import net.finmath.montecarlo.RandomVariable;

public class ConditionalPoisson extends JumpProcess {

	private ArrayList<Double> jumpTimesList;
	private ArrayList<Double> jumpSizesList;

	public ConditionalPoisson(double lambda, double mu, double jumpMean, double jumpStdv, double initialValue, double finalTime, int numberOfTimeSteps, int numberOfRealisations){
		super(lambda, mu, jumpMean, jumpStdv,  finalTime, initialValue, numberOfTimeSteps, numberOfRealisations);  	  
	}

	void generate(){
		jumpSizesList=new ArrayList<Double>(); //lazy initialisation
		int totalNumberOfJumps;
		double finalTime=times.getTime(times.getNumberOfTimeSteps()); //get final time

		Poisson poisson=new Poisson(lambda*finalTime); 
		totalNumberOfJumps=poisson.generatePoisson(); //number of jumps
		Double[] jumpTimes=new Double[totalNumberOfJumps];   //allocate memory, conditonal on the Poisson realisation. Uses Double wrapping class because later we need to trun it into a list. The alternative is to define a list 
		// a list directly, but since we have to SORT the list we must then create a "Comparator" object implementing the comparing method....lots of hassle

		for(int jumpTimeIndex=0; jumpTimeIndex<jumpTimes.length; jumpTimeIndex++ ){    //simulate uniformly distributed jump times
			jumpTimes[jumpTimeIndex]=Math.random()*	finalTime;
		}
		Arrays.sort(jumpTimes);  //sort the jumps in ascending order
		jumpTimesList= new ArrayList<Double> (Arrays.asList(jumpTimes)); //defines an Arraylist from the sorted jumptimes array. Useful for scanning without knowing what the index is or how many elements are there

		jumpTimesList.add(finalTime); //dummy jump variable set at final time
		Iterator<Double> jumpTimesIterator=jumpTimesList.iterator();  //iterator for the while jup loop

		path[0]=new RandomVariable(0.0, initialValue); 


		NormalRandomVariable jumpRv=new NormalRandomVariable(jumpMean, jumpStdv);

		//**** WARNING. THE MAIN LOOP FOR GENERATING THE PROCESS CAN BE MADE IDENTICAL TO THAT OF EPONENTIALSUM ONE (!) TRY IT. HOWEVER HERE I AM OFFERING A DIFFERENT STRATEGY (MORE COMPLICATED). 
		//**** EVEN IN THIS STRATEGY WE HAVE TO RESORT TO DEFINING A DUMMY JUMP AT THE FINAL TIME TO COMPLETE THE PATH GENERATING LOOP
		
		// Second simulate jumps
		// Sort the jumps into their buckets
		int timeIndex=0;
		while(jumpTimesIterator.hasNext()){  //while loop for the jumps
			
			double currentJumpTime = jumpTimesIterator.next();

			// Advance to the corresponding bucket - if needed and add drift
			while(currentJumpTime > times.getTime(timeIndex)) {
				timeIndex++;
				
				path[timeIndex]=path[timeIndex-1].add(new RandomVariable(times.getTime(timeIndex), mu*times.getTimeStep(timeIndex-1))); //create a random variable that also keeps track of time progression, otherwise just used add(mu*times.getTimeStep…...)
	
			}

			double jump = jumpRv.generate();
			path[timeIndex]=path[timeIndex].add(jump);
			jumpSizesList.add(jump);

		}
	}
	double[] getPath(int i){                 //getters and setters for path, jump times and jump sizes
		double[] pathI=new double[times.getNumberOfTimes()];
		for (int timeIndex=0; timeIndex<pathI.length; timeIndex++){
			pathI[timeIndex]=path[timeIndex].get(i);
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

	ArrayList<Double> getJumpTimes(){

		return jumpTimesList;

	}

	ArrayList<Double> getJumpSizes(){

		return jumpSizesList;

	}

	double getFinalValue(int pathNumber){
		return path[times.getNumberOfTimes()-1].get(pathNumber);
	}
	
}

