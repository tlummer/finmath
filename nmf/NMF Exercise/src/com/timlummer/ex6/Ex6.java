package com.timlummer.ex6;

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
		
		
	}

}
