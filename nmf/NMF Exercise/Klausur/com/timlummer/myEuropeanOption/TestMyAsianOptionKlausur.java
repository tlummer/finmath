package com.timlummer.myEuropeanOption;


import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationInterface;
import net.finmath.montecarlo.assetderivativevaluation.BlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloAssetModel;
//import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;
import net.finmath.montecarlo.model.AbstractModel;
import net.finmath.montecarlo.process.AbstractProcess;
import net.finmath.montecarlo.process.ProcessEulerScheme;
import net.finmath.stochastic.RandomVariableInterface;
// import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationInterface;

public class TestMyAsianOptionKlausur {

	public static void main(String[] args) throws CalculationException {

		// Model properties
		double initialValue = 1.0;
		final double riskFreeRate = 0.5;
		final double volatility = 0.30;

		// Process discretization properties
		final int numberOfPaths = 20000;
		final int numberOfTimeSteps = 10;
		final double deltaT = 0.5;

		final int seed = 31415;

		// Product properties
		final int assetIndex = 0;
		final double optionMaturity = 2.0;
		final double optionStrike = 1.05;

		//
		int numberEvalTimeSteps = 12;

		// Create a model
		AbstractModel model = new BlackScholesModel(initialValue, riskFreeRate, volatility);

		// Create a time discretization
		double deltaTForEvaluation = optionMaturity / numberEvalTimeSteps;

		TimeDiscretizationInterface timeDiscretizationForEvaluation = new TimeDiscretization(0.0, numberEvalTimeSteps,
				deltaTForEvaluation);
//		TimeDiscretizationInterface timeDiscretization = new TimeDiscretization(0.0 /* initial */, numberOfTimeSteps,
//				deltaT);

		// Create a corresponding MC process
		AbstractProcess process = new ProcessEulerScheme(
				new BrownianMotion(timeDiscretizationForEvaluation, 1 /* numberOfFactors */, numberOfPaths, seed));

//		// Link model and process for delegation
//		process.setModel(model);
//		model.setProcess(process);
//		//
//		/*
//		 * Value a call option - directly
//		 */
//
//
//		RandomVariableInterface asset = process.getProcessValue(timeDiscretizationForEvaluation.getTimeIndex(optionMaturity),
//				assetIndex);
//		RandomVariableInterface numeraireAtPayment = model.getNumeraire(optionMaturity);
//		RandomVariableInterface numeraireAtEval = model.getNumeraire(0.0);
//
//		double ValueUnderlying = asset.div(numeraireAtPayment).mult(numeraireAtEval).getAverage();
//
		// Using the process (Euler scheme), create an MC simulation of a
		// Black-Scholes model
		AssetModelMonteCarloSimulationInterface monteCarloBlackScholesModel = new MonteCarloAssetModel(model, process);

		/*
		 * Value a call option (using the product implementation)
		 */

		MyAsianOption asianOptions = new MyAsianOption(optionMaturity, optionStrike, timeDiscretizationForEvaluation);
		double valueAsianOption = asianOptions.getValue(monteCarloBlackScholesModel);

		System.out.println("AsianOption value using Monte-Carlo.......: " + valueAsianOption);
//		System.out.println("Underlying value using Monte-Carlo...........: " + ValueUnderlying);
	}
}
