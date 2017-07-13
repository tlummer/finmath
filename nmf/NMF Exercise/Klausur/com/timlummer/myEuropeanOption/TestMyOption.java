  package com.timlummer.myEuropeanOption;


import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationInterface;
import net.finmath.montecarlo.assetderivativevaluation.BlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloAssetModel;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;
import net.finmath.montecarlo.model.AbstractModel;
import net.finmath.montecarlo.process.AbstractProcess;
import net.finmath.montecarlo.process.ProcessEulerScheme;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationInterface;

public class TestMyOption {

	public static void main(String[] args) throws Exception {
		
		// Model properties
		double	initialValue   = 1.0;
		double	riskFreeRate   = 0.05;
		double	volatility     = 0.30;

		// Process discretization properties
		int		numberOfPaths		= 20000;
		int		numberOfTimeSteps	= 10;
		double	deltaT				= 0.5;
		
		int		seed				= 31415;

		// Product properties
		int		assetIndex = 0;
		double	optionMaturity = 2.0;
		
		double	optionStrike = 1.05;
				
		// Create a model
		AbstractModel model = new BlackScholesModel(initialValue, riskFreeRate, volatility);

		// Create a time discretization
		TimeDiscretizationInterface timeDiscretization = new TimeDiscretization(0.0 /* initial */, numberOfTimeSteps, deltaT);

		// Create a corresponding MC process 	// net.finmath.montecarlo.process
		AbstractProcess process = new ProcessEulerScheme(new BrownianMotion(timeDiscretization, 1 /* numberOfFactors */, numberOfPaths, seed));

		// Using the process (Euler scheme), create an MC simulation of a Black-Scholes model
		AssetModelMonteCarloSimulationInterface monteCarloBlackScholesModel = new MonteCarloAssetModel(model, process);

		/*
		 * Value a call option (using the product implementation)
		 */
		MyEuropeanOption europeanOption = new MyEuropeanOption(optionMaturity, optionStrike);
		
		double value = europeanOption.getValue(monteCarloBlackScholesModel);
		
		double valueAnalytic = AnalyticFormulas.blackScholesOptionValue(initialValue, riskFreeRate, volatility, optionMaturity, optionStrike);

		System.out.println("value using Monte-Carlo.......: " + value);
	

	}

}
