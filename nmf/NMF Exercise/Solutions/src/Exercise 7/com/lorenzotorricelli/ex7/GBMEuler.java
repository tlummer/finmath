package com.lorenzotorricelli.ex7;

import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;

public class GBMEuler {


	private double  timeHorizon;

	private int numberOfTimeSteps;
	private int numberOfSimulations;

	private double deltaT;



	private double initialValue;

	private double drift;
	private double volatility;

	private RandomVariableInterface[] paths;

	TimeDiscretization times;


	private final int seed;


	public GBMEuler(int numberOfTimeSteps, int numberOfSimulations, 
			double volatility, double drift,
			double initialValue, double timeHorizon,  int seed) {
		this.timeHorizon = timeHorizon;      
		this.numberOfTimeSteps = numberOfTimeSteps;
		this.numberOfSimulations = numberOfSimulations;
		this.volatility=volatility;
		this.drift=drift;
		this.initialValue = initialValue;
		deltaT=timeHorizon/numberOfTimeSteps;
		this.seed=seed;

		times=new TimeDiscretization(0.0, numberOfTimeSteps, deltaT);
		paths=new RandomVariableInterface[times.getNumberOfTimes()];


	}

	public void generateEuler(){  

		BrownianMotion brownianMotion= 
				new BrownianMotion(times, 1 , numberOfSimulations, seed);



		paths[0]=new RandomVariable(0.0,initialValue);  //Initialise the SDEs at x0

		RandomVariableInterface processDrift;
		RandomVariableInterface processDiffusion;







		for(int timeIndex=1; timeIndex< times.getNumberOfTimes(); timeIndex++){   

			processDrift=paths[timeIndex-1].
					mult(drift).mult(times.getTimeStep(timeIndex-1)); //or .mult(deltaT) 
			processDiffusion=paths[timeIndex-1].mult(volatility).
					mult(brownianMotion.getBrownianIncrement(timeIndex-1,0));

			paths[timeIndex]=paths[timeIndex-1].add(processDrift).add(processDiffusion); 

		}   


	}







	public void setInterestRate(double interestRate){
		this.drift=interestRate;
	}

	public void setSpotPrice(double spotPrice){
		this.initialValue=spotPrice;
	}




	public double getTimeHorizon() {   //Just some getters and setters...
		return timeHorizon;
	}

	public void setTimeHorizon(double timeHorizon) {
		this.timeHorizon = timeHorizon;
	}




	public RandomVariableInterface[] getPaths() {
		return paths;
	}



	public int getNumberOfTimeInstants() {
		return times.getNumberOfTimes();
	}

	public int getNumberOfSimulations() {
		return numberOfSimulations;
	}



	public double getInitialValue() {
		return initialValue;
	}

	public double getDrift() {
		return drift;
	}

	public RandomVariableInterface getPosition(int timeInstant){  //return the whole realisation at time t
		return paths[timeInstant];
	}

	public double[] getPath(int pathNumber){;
	double samplePath[]=new double[times.getNumberOfTimes()-1];
	for(int pathIndex=0; pathIndex<samplePath.length; pathIndex++){
		samplePath[pathIndex]=paths[pathIndex].get(pathNumber);
	}
	return samplePath;
	}




	public void printAPath(int pathNumber){
		double[] samplePath=getPath(pathNumber);
		for(int i=0; i<samplePath.length; i++)
			System.out.println(samplePath[i]);
	}





	public RandomVariableInterface getFinalValue(){   
		double[] finalState=new double[numberOfSimulations];
		for(int i=0; i<finalState.length; i++)
			finalState[i]=paths[times.getNumberOfTimes()-1].get(i);
		return  new RandomVariable(numberOfTimeSteps,  finalState);  //defensive copy

	}






}
