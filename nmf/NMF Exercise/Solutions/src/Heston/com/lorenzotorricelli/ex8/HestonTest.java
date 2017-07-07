package com.lorenzotorricelli.ex8;

import java.util.Arrays;

public class HestonTest {

	public static void main(String[] args) {
		

		int numberOfTimeSteps=200;
		int numberOfSimulations=100000;

		double kappa=0.5;   //Risk neutral variance parameters
		double theta=0.2;
		double eta=0.3;
		double rho=-0.8;

		double spotPrice=100;
		double spotVariance=0.2	;
		double interestRate=0.02;

		double  timeToMaturity=1;

		double strike=100;



		TruncationFunction f=new FloorAtZero();
		TruncationFunction g=new AbsoluteValue();




		EulerHestonPrice eu=new EulerHestonPrice(numberOfTimeSteps, numberOfSimulations, kappa, theta, eta, spotVariance, rho, spotPrice, timeToMaturity, interestRate, 3124);

		eu.generateEuler(f);
		
		int samplePath=3;

	   
		
		System.out.println("One price path: \n");
		eu.printOnePricePath(samplePath);
		System.out.println("\n");
		System.out.println("One variance path \n" + Arrays.toString(eu.getOneVariancePath(samplePath))+ "\n");
		eu.printOneVariancePath(samplePath);
		System.out.println("\n");


		HestonPricer pricer= new HestonPricer(numberOfTimeSteps, numberOfSimulations , strike, spotPrice, spotVariance, interestRate,
				timeToMaturity, kappa, theta, eta, rho);

		long start=System.currentTimeMillis();

		pricer.calculateCallPutPrice(f);
		long end=System.currentTimeMillis();

		System.out.println("Elapsed time: " + (double)(end-start)/1000 + " sec.");
		System.out.println("Call price of full truncation scheme: " +pricer.getCallPrice());
		System.out.println("Forward contract price of full truncation scheme " + pricer.getForwardPrice());
		System.out.println("Put option price of full truncation scheme: "+ pricer.getPutPrice()+ "\n");


		start=System.currentTimeMillis();

		pricer.calculateCallPutPrice(g);
		end=System.currentTimeMillis();

		System.out.println("Elapsed time: " + (double)(end-start)/1000 + " sec.");
		System.out.println("Call price of reflection scheme: " + pricer.getCallPrice());
		System.out.println("Forward contract value of reflection scheme: " + pricer.getForwardPrice());
		System.out.println("Put price of reflection scheme: " + pricer.getPutPrice()+ "\n");

		start=System.currentTimeMillis();

		pricer.calculateCallPutPrice();
		end=System.currentTimeMillis();

		System.out.println("Elapsed time: " + (double)(end-start)/1000 + " sec.");
		System.out.println("Call price no modification: " +pricer.getCallPrice());
		System.out.println("Forward contract price no modification " + pricer.getForwardPrice());
		System.out.println("Put option price no modification: "+ pricer.getPutPrice()+ "\n");
		



	}


}
	

