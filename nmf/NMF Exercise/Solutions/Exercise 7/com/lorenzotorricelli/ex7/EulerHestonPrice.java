package com.lorenzotorricelli.ex7;


import net.finmath.montecarlo.*;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;

public class EulerHestonPrice implements  EulerSimulatorInterface  {


	private double  timeHorizon;

	private int numberOfTimeSteps;
    private int numberOfTimeInstants;
	private int numberOfSimulations;
	private double deltaT;

	private double kappa;
	private double theta;
	private double eta;
	private double rho;

	private double spotPrice;
	private double spotVariance;
	private double interestRate;

	private RandomVariableInterface[][] path;
	
	TimeDiscretization times;
	

	private final int seed;


	public EulerHestonPrice(int numberOfTimeSteps, int numberOfSimulations, double kappa,
			double theta, double eta, double  spotVariance, double rho, double spotPrice, double timeHorizon, double interestRate, int seed) {
		this.timeHorizon = timeHorizon;      
		this.numberOfTimeSteps = numberOfTimeSteps;
		this.numberOfTimeInstants = numberOfTimeSteps+1;
		this.numberOfSimulations = numberOfSimulations;
		this.kappa = kappa;
		this.theta = theta;
		this.eta = eta;
		this.spotPrice = spotPrice;
		this.spotVariance = spotVariance;
		this.interestRate = interestRate;
		deltaT=timeHorizon/numberOfTimeSteps;
		this.rho=rho;
		this.seed=seed;
		times=new TimeDiscretization(0.0, numberOfTimeSteps, deltaT);
		path=new RandomVariableInterface[2][times.getNumberOfTimes()];
		

	}

	public void generateEuler(){  //WRONG. this eventually will produce a Nan
		double[][] factorLoadings={{1.0 , 0.0},{rho, Math.sqrt(1.0-rho*rho)}};


		BrownianMotion uncorrelatedFactors=new BrownianMotion(times, 2 , numberOfSimulations, seed);
		CorrelatedBrownianMotion cbm= new CorrelatedBrownianMotion(uncorrelatedFactors, factorLoadings); 

		// or, to shorten even more the code:
		//		  CorrelatedBrownianMotion cbm= new CorrelatedBrownianMotion(new BrownianMotion(new TimeDiscretization(0.0, numberOfTimeInstants, deltaT), 2 , numberOfSimulations, 3121), factorLoadings); 


		path[0][0]=new RandomVariable(0,spotPrice);  //Initialise the SDEs
		path[1][0]=new RandomVariable(0,spotVariance);
	
		//			//wrapping interest rate and theta in Random variable
		RandomVariable interestRateRv=new RandomVariable(0, interestRate);
		RandomVariable thetaRv=new RandomVariable(0, theta);
		RandomVariable kappaRv=new RandomVariable(0, kappa);
		RandomVariable etaRv=new RandomVariable(0, eta);


		RandomVariableInterface priceDrift;
		RandomVariableInterface priceDiffusion;
		RandomVariableInterface logReturn;
		RandomVariableInterface expReturn;


		RandomVariableInterface volDrift;
		RandomVariableInterface volDiffusion;




		for(int timeIndex=1; timeIndex< numberOfTimeInstants; timeIndex++){   

			priceDrift=interestRateRv.sub(  path[1][timeIndex-1].div(2.0)     ).mult(deltaT);           //step by step implementation
			priceDiffusion=path[1][timeIndex-1].sqrt().mult(cbm.getBrownianIncrement(timeIndex-1,0));
			logReturn=priceDrift.add(priceDiffusion);
			expReturn=logReturn.exp();
			path[0][timeIndex]=path[0][timeIndex-1].mult(expReturn);  //log-Euler

			volDrift=kappaRv.mult(thetaRv.sub(path[1][timeIndex-1])).mult(deltaT);
			volDiffusion=etaRv.mult(path[1][timeIndex-1].sqrt()).mult(cbm.getBrownianIncrement(timeIndex-1,1));
			path[1][timeIndex]=path[0][timeIndex-1].add(volDrift).add(volDiffusion); 


		}   


	}



	public void generateEuler(TruncationFunction f){  //Overloaded version for truncated schemes
		double[][] factorLoadings={{1.0 , 0},{rho, Math.sqrt(1.0-rho*rho)}};
		CorrelatedBrownianMotion cbm= new CorrelatedBrownianMotion(new BrownianMotion(new TimeDiscretization(0.0, numberOfTimeInstants, deltaT), 2 , numberOfSimulations, (int)seed), factorLoadings); //Compressed version of the initialisation of the the default method


		path[0][0]=new RandomVariable(0.0,spotPrice);  //Initialise the SDEs
		path[1][0]=new RandomVariable(0.0,spotVariance);
		//wrapping interest rate and theta in Random variable
		RandomVariable interestRateRv=new RandomVariable(0, interestRate);
		RandomVariable thetaRv=new RandomVariable(0, theta);
		RandomVariable kappaRv=new RandomVariable(0, kappa);
		RandomVariable etaRv=new RandomVariable(0, eta);




		for(int timeIndex=1; timeIndex< numberOfTimeInstants; timeIndex++){   



			path[0][timeIndex]=path[0][timeIndex-1].mult(interestRateRv.sub(  f.evaluate(path[1][timeIndex-1]).div(2.0)  ) .mult(deltaT).add(f.evaluate(path[1][timeIndex-1]).sqrt().mult(cbm.getBrownianIncrement(timeIndex-1,0))).exp());  //log-Euler



			path[1][timeIndex]=path[1][timeIndex-1].add(kappaRv.mult(thetaRv.sub(f.evaluate(path[1][timeIndex-1]))).mult(deltaT)).add(etaRv.mult(f.evaluate(path[1][timeIndex-1]).sqrt()).mult(cbm.getBrownianIncrement(timeIndex-1,1)));  //log-Euler
			//            


			// path[1][timeIndex]=path[1][timeIndex-1].add(f.evaluate(path[1][timeIndex-1]).add( kappaRv.mult(thetaRv.sub(f.evaluate(path[1][timeIndex-1])))).mult(deltaT).add(etaRv.mult(f.evaluate(path[1][timeIndex-1]).sqrt()).mult(cbm.getBrownianIncrement(timeIndex-1,1))));

		}   


	}




	public void setTime(double timeHorizon){  //the timeHorizon, spotPrice, and interestRat parameters are Market, not model. In this implementation they have to be set by the Pricing routine, not from the constructor
		this.timeHorizon=timeHorizon; 
		deltaT=timeHorizon/numberOfTimeInstants;
	}


	public void setInterestRate(double interestRate){
		this.interestRate=interestRate;
	}

	public void setSpotPrice(double spotPrice){
		this.spotPrice=spotPrice;
	}




	public double getTimeHorizon() {   //Just some getters and setters...
		return timeHorizon;
	}

	public void setTimeHorizon(double timeHorizon) {
		this.timeHorizon = timeHorizon;
	}

	public double getKappa() {
		return kappa;
	}

	public void setKappa(double kappa) {
		this.kappa = kappa;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getEta() {
		return eta;
	}

	public void setEta(double eta) {
		this.eta = eta;
	}

	public double getRho() {
		return rho;
	}

	public void setRho(double rho) {
		this.rho = rho;
	}

	public double getSpotVariance() {
		return spotVariance;
	}

	public void setSpotVariance(double spotVariance) {
		this.spotVariance = spotVariance;
	}

	public RandomVariableInterface[][] getPath() {
		return path;
	}

	public void setPath(RandomVariableInterface[][] path) {
		this.path = path;
	}

	public int getNumberOfTimeInstants() {
		return numberOfTimeInstants;
	}

	public int getNumberOfSimulations() {
		return numberOfSimulations;
	}



	public double getSpotPrice() {
		return spotPrice;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public RandomVariableInterface getPosition(int pathNumber, int timeInstant){
		return path[pathNumber][timeInstant];
	}

	public double[] getOnePricePath(int pathNumber){;
	double samplePath[]=new double[times.getNumberOfTimes()-1];
	for(int i=0; i<samplePath.length; i++){
		samplePath[i]=path[0][i].get(pathNumber);
	}
	return samplePath;
	}

	public double[] getOneVariancePath(int pathNumber){
		double samplePath[]=new double[times.getNumberOfTimes()-1];
		for(int i=0; i<samplePath.length; i++)
			samplePath[i]=path[1][i].get(pathNumber);
		return samplePath;
	}


	public void printOnePricePath(int pathNumber){
		double[] samplePath=getOnePricePath(pathNumber);
		for(int i=0; i<samplePath.length; i++)
			System.out.println(samplePath[i]);
	}


	public void printOneVariancePath(int pathNumber){
		double[] samplePath=getOneVariancePath(pathNumber);
		for(int i=0; i<samplePath.length; i++)
			System.out.println(samplePath[i]);
	}



	public RandomVariableInterface getFinalPrice(){   
		double[] finalState=new double[numberOfSimulations];
		for(int i=0; i<finalState.length; i++)
			finalState[i]=path[0][times.getNumberOfTimes()-1].get(i);
		return  new RandomVariable(numberOfTimeSteps,  finalState);  //defensive copy

	}
	
	public RandomVariableInterface getPrice(int timeIndex){   
		double[] finalState=new double[numberOfSimulations];
		for(int i=0; i<finalState.length; i++)
			finalState[i]=path[0][timeIndex].get(i);
		return  new RandomVariable(numberOfTimeSteps,  finalState);  //defensive copy

	}
	

	public RandomVariableInterface getFinalVariance(){   
		double[] finalState=new double[numberOfSimulations];
		for(int i=0; i<finalState.length; i++)
			finalState[i]=path[1][times.getNumberOfTimes()-1].get(i);
		return  new RandomVariable(numberOfTimeSteps,  finalState);  //defensive copy
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


}

	

