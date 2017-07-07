package com.lorenzotorricelli.ex7;


import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;

public class HestonPricer {
	private double strike, spotPrice,  interestRate, timeToMaturity;
	
	private double callPrice, putPrice, forwardContractValue; //not "forwardPrice", to avoid confusion between the value of the forward contract and the forward value of the stock
	
	private double kappa, theta, eta, rho, spotVariance;
	
	private int numberOfTimeSteps, numberOfSimulations;
	
	private final int seed=123389;
	
	public HestonPricer(int numberOfTimeSteps, int numberOfSimulations,  double strike, double spotPrice, double spotVariance, double interestRate,
			double timeToMaturity,double kappa, double theta, double eta, double rho) {
		this.numberOfTimeSteps=numberOfTimeSteps;
		this.numberOfSimulations=numberOfSimulations;
		this.strike = strike;
		this.spotPrice = spotPrice;
		this.interestRate = interestRate;
		this.timeToMaturity = timeToMaturity;
		this.kappa=kappa;
		this.theta=theta;
		this.eta=eta;
		this.rho=rho;
		this.spotVariance=spotVariance;
	}

	

	
	public void calculateCallPutPrice(){   //Default version. No truncation in the SDE coefficients, produces NaN (there is a square root...)
		
		EulerHestonPrice assetPrice=new EulerHestonPrice(numberOfTimeSteps, numberOfSimulations, kappa,
			theta, eta, spotVariance, rho, spotPrice,  timeToMaturity, interestRate, seed);
		
		assetPrice.generateEuler();
		
		
		RandomVariableInterface finalPrice=assetPrice.getFinalPrice();
		forwardContractValue=spotPrice-Math.exp(-interestRate*timeToMaturity)*strike;  //no arbitrage Analytic value; no need to simulate....
		
		//forwardContractValue=Math.exp(-interestRate*timeToMaturity)*finalPrice.sub(strike).getAverage(); If we really insist to simulate......
		callPrice=Math.exp(-interestRate*timeToMaturity)*finalPrice.sub(strike).floor(0.0).getAverage();
		putPrice=callPrice-forwardContractValue;
		
		
		 
	}
	
	
	public void calculateCallPutPrice(TruncationFunction f){   //correct ovelroaded version accepting truncation functions as a parameter
		
		EulerHestonPrice assetPrice=new EulerHestonPrice(numberOfTimeSteps, numberOfSimulations, kappa,
			theta, eta, spotVariance, rho, spotPrice,  timeToMaturity, interestRate, seed);
		
		assetPrice.generateEuler(f);
		
		RandomVariableInterface finalPrice=assetPrice.getFinalPrice();
		forwardContractValue=
spotPrice-Math.exp(-interestRate*timeToMaturity)*strike;  //no arbitrage Analytic value; no need to simulate....
		
		//forwardContractValue=Math.exp(-interestRate*timeToMaturity)*finalPrice.sub(strike).getAverage(); If we really insist to simulate......
		callPrice
		=Math.exp(-interestRate*timeToMaturity)
		*finalPrice.sub(strike).floor(0.0).getAverage();
		putPrice=callPrice-forwardContractValue;
		
		 
	}

	public double getCallPrice() {
		return callPrice;
	}



	public double getPutPrice() {
		return putPrice;
	}

	public double getForwardPrice() {
		return forwardContractValue;
	}



}
