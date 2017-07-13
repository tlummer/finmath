package com.timlummer.ex7;

import com.timlummer.ex6.MyBrownianMotion;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;


public class EulerMaruyamBlackScholes {
	
	private TimeDiscretization Time;
	private MyBrownianMotion BM;

	private RandomVariableInterface[] Euler;
	
	private double iniVal;
	private double sigma;
	private double my;
	private int numberOfPaths; 
	private int numberOfFactors;
	
	
	public EulerMaruyamBlackScholes (
			TimeDiscretization time,
			double my,
			double sigma,
			double iniVal,
			int numberOfPaths,
			int numberOfFactors){
	
	this.Time = time;
	this.sigma = sigma;
	this.iniVal = iniVal;
	this.my = my;
	this.numberOfPaths = numberOfPaths;
	this.numberOfFactors = numberOfFactors;
	}
			
	public void generate() {
		
		BM = new MyBrownianMotion(Time,numberOfFactors,numberOfPaths);
		BM.generateBrownianMotion();
		
		RandomVariable[] BMRV = BM.getBrownianPathsOfGivenFactor(1);
		
		Euler = new RandomVariable[Time.getNumberOfTimes()];
		
		Euler[0] = new RandomVariable(Time.getTime(0),numberOfPaths,iniVal);		
		
		for (int t = 0; t<Time.getNumberOfTimeSteps();t++) {
			
			Euler[t+1] =  Euler[t].mult(my*(Time.getTime(t+1)-Time.getTime(t))+1.0).add(Euler[t].mult(sigma).mult((BMRV[t+1].sub(BMRV[t]))));
		}
	}
	
	public double GetAverage() {
		return Euler[Time.getNumberOfTimes()-1].getAverage();
		
	}	
		
	public double GetVariance() {
		return Euler[Time.getNumberOfTimes()-1].getVariance();	
		
	}

}
