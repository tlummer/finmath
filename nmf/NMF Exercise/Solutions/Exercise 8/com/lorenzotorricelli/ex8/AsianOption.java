package com.lorenzotorricelli.ex8;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationInterface;
import net.finmath.stochastic.RandomVariableInterface;

import net.finmath.time.TimeDiscretizationInterface;

public class AsianOption {
	private final double maturity;
	private final double strike;
	private final double averagingLength;
	//	private final double averagingFrequency;


	public AsianOption(double maturity, double strike, double averagingLength) {

		this.maturity				= maturity;
		this.strike					= strike;
		this.averagingLength		= averagingLength;

	}

	public RandomVariableInterface getValue
	( AssetModelMonteCarloSimulationInterface model) throws CalculationException {

		TimeDiscretizationInterface times=model.getTimeDiscretization();  //gets the model time discretisation
		int firstIndex= times.getTimeIndexNearestGreaterOrEqual(maturity-averagingLength); //gets the first instant to record the average
		int lastIndex=times.getTimeIndexNearestGreaterOrEqual(maturity);//System.out.println(firstIndex);		
		RandomVariableInterface average = model.getRandomVariableForConstant(0.0);
		for(int i=firstIndex; i<=lastIndex ; i++) {
			RandomVariableInterface underlying	= model.getAssetValue(times.getTime(i),0);   //discrete averaging at each time step
			average = average.add(underlying);
		}
		average = average.div(lastIndex-firstIndex+1);

		// Discounted value = max(average - strike, 0)
		RandomVariableInterface values =
				model.getNumeraire(maturity).mult(
						average.sub(strike).floor(0.0));



		return values;
	}
}