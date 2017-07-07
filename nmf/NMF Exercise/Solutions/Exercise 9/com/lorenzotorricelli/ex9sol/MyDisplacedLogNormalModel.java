package com.lorenzotorricelli.ex9sol;


import net.finmath.exception.CalculationException;

//Euler simulation for Displace LogNormal Model

import net.finmath.montecarlo.*;
import net.finmath.stochastic.*;
import net.finmath.time.TimeDiscretization;

public class MyDisplacedLogNormalModel {

	private int		numberOfTimeSteps;
	private TimeDiscretization times;
	private double	deltaT;
	private int		numberOfSimulations;
	private double	initialPrice;
	private double	a;
	private double  b;
	
	private double riskFreeRate;

	private RandomVariableInterface[]	path;  //the RandomVariable represents the number of factors

	public MyDisplacedLogNormalModel(

			int numberOfTimeSteps,
			double maturity,
			int numberOfSimulations,
			double initialPrice,
			double riskFreeRate,
			double a, double b) {   //a: homogenous part of volatility; b linear additive part
		super();
		this.numberOfTimeSteps = numberOfTimeSteps;
		this.deltaT = maturity/numberOfTimeSteps;
		this.numberOfSimulations = numberOfSimulations;
		this.initialPrice = initialPrice;
		this.a= a;
		this.b=b;
		this.riskFreeRate=riskFreeRate;
		path = new RandomVariableInterface[numberOfTimeSteps+1];   //numberOfTimeInstants=1+NumberOfTimeSteps
		times=new TimeDiscretization(0.0, numberOfTimeSteps, deltaT);

	}
	
	public MyDisplacedLogNormalModel(

			int numberOfTimeSteps,
			double maturity,
			int numberOfSimulations,
			double initialPrice,
			double a, double b, TimeDiscretization times) {   //Overloaded constructor if you want to provide you own timeDiscretisation
		super();
		this.numberOfTimeSteps = numberOfTimeSteps;
		this.deltaT = maturity/numberOfTimeSteps;
		this.numberOfSimulations = numberOfSimulations;
		this.initialPrice = initialPrice;
		this.a= a;
		this.b=b;
		path = new RandomVariableInterface[numberOfTimeSteps+1];   //numberOfTimeInstants=1+NumberOfTimeSteps
		this.times=times;

	}



	 void generateEuler()  {

		BrownianMotionInterface	bM	= new BrownianMotion( 
				times,        
				1,						// numberOfFactors
				numberOfSimulations,             
				3141				// seed
				);



		// Set path at initial price
		path[0]= new RandomVariable(0.0, numberOfSimulations, initialPrice);	         

		RandomVariableInterface priceDrift;
		RandomVariableInterface priceDiffusion;




		for(int timeIndex = 1; timeIndex < numberOfTimeSteps+1; timeIndex++)
		{ //simulates Euler
			priceDrift= path[timeIndex-1].mult(riskFreeRate).mult(bM.getTimeDiscretization().getTimeStep(timeIndex-1)); 
			priceDiffusion=(path[timeIndex-1].mult(a).add(b)).mult(bM.getBrownianIncrement(timeIndex-1, 0));
			path[timeIndex]=path[timeIndex-1].add(priceDrift).add(priceDiffusion);


		}

	}


	public TimeDiscretization getTimeDiscretization(){
		return times;
	}


	public double getDeltaT() {
		return deltaT;
	}

	/**
	 * @return Returns the initialValue.
	 */
	public double getInitialPrice() {
		return initialPrice;
	}

	/**
	 * @return Returns the nPaths.
	 */
	public int getNumberOfSimulations() {
		return numberOfSimulations;
	}

	/**
	 * @return Returns the numberOfTimeSteps.
	 */
	public int getNumberOfTimeSimulations() {
		return numberOfTimeSteps;
	}

	/**
	 * @return Returns the sigma.
	 */


	public double getB() {
		return b;
	}

	public double getA() {
		return a;
	}
	
	public RandomVariableInterface getFinalState() {
		return path[numberOfTimeSteps]; // the array is based at 0!
	} //useless!
	public RandomVariableInterface getAssetValue(double time) {
		return path[times.getTimeIndex(time)]; // the array is based at 0!
	}



}
