package com.timlummer.ex6;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.time.TimeDiscretization;

public class Ex6 {

	public static void main(String[] args) {
		
		int numberOfFactors=2;
		int numberOfPaths=1000;


		double start=0.0;
		int numberOfTimeSteps=1000;
		double deltaT=0.001;
		
		TimeDiscretization times = new TimeDiscretization(start, numberOfTimeSteps, deltaT);
		
		MyBrownianMotion myBM = new MyBrownianMotion(times, numberOfFactors, numberOfPaths);
		
		myBM.generateBrownianMotion();
		
		System.out.println("realization");
		
		
		System.out.println("Excerciser 2");
	
		
		RandomVariable[] Fact1 = myBM.getBrownianPathsOfGivenFactor(1);
		RandomVariable[] Fact2 = myBM.getBrownianPathsOfGivenFactor(2);
		
		
		int Time1 = 100;
		int Time2 = 150;
		
		double mean1 = Fact1[Time1].getAverage();
		double mean2 = Fact1[Time2].getAverage();
		
		double cov = Fact1[Time1].mult(Fact2[Time2]).getAverage();
		
		System.out.println("COV :"+cov);
		
		System.out.println("mean 1 " + mean1);
		System.out.println("mean 2 " + mean2);
		
		double cor = Fact1[Time1].add(-mean1).mult((Fact1[Time2].add(-mean2))).getAverage();
				
		System.out.println("S min t is 100 : " + cor);
				
		System.out.println("Analytical" + 0.5*Fact1[Time1].mult(Fact1[Time1]).add(-times.getTime(Time1)).getAverage());
		
		double sum = 0.0;
		
		for (int t = 0; t<times.getNumberOfTimeSteps();t++) {
		 sum += Fact1[t].mult((Fact1[t].sub(Fact1[t+1]))).getAverage();	
		}
		
		System.out.println("Brownian Sum :"+ sum);
		
	}
	

}
