package com.lorenzotorricelli.ex8;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import net.finmath.exception.CalculationException;


import net.finmath.montecarlo.assetderivativevaluation.*;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;
import net.finmath.time.*;

public class Ex8 {


	public static void main(String[] args) throws CalculationException {
		// TODO Auto-generated method stub
		int numberOfTimeSteps=200;
		double finalTime=2;   //final time of Euler simulation
		int numberOfPaths=100000;
		double riskFreeRate=0;
		double initialPrice=100;
		double volatility=0.2;
		double strike=100.0;
		double maturity=1.0;  //options maturity
		double deltaT=finalTime/numberOfTimeSteps;
		
		
		double averagingFactor100=1;    //factor f such that delta=maturity*factor
		double averagingFactor75=0.75;
		double averagingFactor50=0.5;
		double averagingFactor25=0.25;
		
		double averagingTestFactor=averagingFactor100;
		
		double averagingTestLength=maturity*averagingTestFactor;



		NumberFormat formatDec4 = new DecimalFormat("00.0000");

		NumberFormat formatDec2 = new DecimalFormat("0.00");
		NumberFormat formatDec0 = new DecimalFormat("00");


		TimeDiscretization times=new TimeDiscretization(0, numberOfTimeSteps, deltaT);	


		MonteCarloBlackScholesModel bsModel= new	MonteCarloBlackScholesModel(  //using the Timediscretisation version of the BS model constructor
				times,
				numberOfPaths,
				initialPrice,
				riskFreeRate,
				volatility);

		EuropeanOption eo=new EuropeanOption(maturity, strike);  //random variable
		AsianOption ao=new AsianOption(maturity, strike,  averagingTestLength);




		System.out.println("Exercise 1");

		System.out.println("A preliminary test on "
				+ "European Options using the finamth Library \nA List of Payoffs:" + eo.getValue(0, bsModel)); //Prints the payoff Random Variable
        
		System.out.println("A preliminary test on "
				+ "European Options using the finamth Library \nA value:" + eo.getValue(0, bsModel).getAverage()); //Prints the payoff Random Variable

		
		System.out.println("A preliminary test on Asian Options using the finamth Library \nA List of Payoffs:" + ao.getValue(bsModel)); //Prints the payoff Random Variable




		System.out.println("European Option Price of stirke "+ 
		strike+ " and maturity " + maturity + ": " + 
	eo.getValue(0, bsModel).getAverage()); //gets the Value (toString() automatically called)
		System.out.println("Asian Option Price of stirke "+ strike+ 
				" and maturity " + maturity + " with averaging factor " + averagingTestFactor +  " : " + ao.getValue(bsModel).getAverage()); //gets the Value (toString() automatically called)

//		Backtest with finmath
//		AsianOption ao2=new AsianOption(finalTime, strike,  times);





		//		System.out.println(Arrays.toString(bsModel.getAssetValue(numberOfTimeSteps, 0).getRealizations()));  // try block. Used when executing a method throwing an error/exception. If the method throws an error
		// it is "caught" (dealt with) in the catch block, i.e. if n exception is thrown the compiler goes to the instruction in the catch block and executes 
		// those
		//								System.out.println(bsModel.toString());  // try block. Used when executing a method throwing an error/exception. If the method throws an error
		//				System.out.println(bsModel.getVolatility());		
		//				System.out.println(bsModel.getProcess().getBrownianMotion());

		//				System.out.println("A preliminary test on European Options using the finamth Library \nA List of Payoffs:" + ao.getValue(0, bsModel)); //Prints the payoff Random Variable

        double initialStrike=80;
       double  finalStrike=120;
       double strikeIncrement=10;
       
       double initialMaturity=0.25;
       double finalMaturity=finalTime;
       double maturityIncrement=0.25;

		System.out.println("Printable Array of European Call Prices for different maturities and strikes \n"); // The output is pre-formatted: just copy and paste to excel. Th

		System.out.print("\t");
		for(double strikes=initialStrike; strikes<=finalStrike
				; strikes+=strikeIncrement )                                   
			System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
		System.out.println("\n");


		for(double maturities=initialMaturity; maturities <= finalMaturity; maturities+=maturityIncrement){	
			System.out.print(
					formatDec2.format(maturities)+ "\t" );

			for(double strikes=initialStrike; strikes<=finalStrike
					; strikes+=strikeIncrement){
				EuropeanOption newEo=new EuropeanOption(maturities, strikes);


				System.out.print(
						formatDec4.format( newEo.getValue(0, bsModel).getAverage()) + "\t");
			}
			System.out.println("\n");			

		} 

		System.out.println("Printable Array of Asian Call Prices for different maturities and strikes \n averaging factor : " + averagingFactor25); // The output is pre-formatted: just copy and paste to excel. Th

		System.out.print("\t");
		for(double strikes=initialStrike; strikes<=finalStrike
				; strikes+=strikeIncrement )
			System.out.print(formatDec0.format(strikes) + "\t");
		System.out.println("\n");


		for(double maturities=initialMaturity; maturities <= finalMaturity; maturities+=maturityIncrement){	
			System.out.print(
					formatDec2.format(maturities)+ "\t" );

			for(double strikes=initialStrike; strikes<=finalStrike
					; strikes+=strikeIncrement ){
				AsianOption newAo=new AsianOption(maturities, strikes, maturities*averagingFactor25);


				System.out.print(
						formatDec4.format( newAo.getValue(bsModel).getAverage()) + "\t");
			}
			System.out.println("\n");			

		} 

		System.out.println("Printable Array of Asian Call Prices for different maturities and strikes \n averaging factor : " + averagingFactor50); // The output is pre-formatted: just copy and paste to excel. Th

		System.out.print("\t");
		for(double strikes=initialStrike; strikes<=finalStrike
				; strikes+=strikeIncrement )
			System.out.print(formatDec0.format(strikes) + "\t");
		System.out.println("\n");


		for(double maturities=initialMaturity; maturities <= finalMaturity; maturities+=maturityIncrement){	
			System.out.print(
					formatDec2.format(maturities)+ "\t" );

			for(double strikes=initialStrike; strikes<=finalStrike
					; strikes+=10 ){
				AsianOption newAo=new AsianOption(maturities, strikes,  maturities*averagingFactor50);


				System.out.print(
						formatDec4.format( newAo.getValue(bsModel).getAverage()) + "\t");
			}
			System.out.println("\n");			

		} 


		System.out.println("Printable Array of Asian Call Prices for different maturities and strikes \n averaging factor : " + averagingFactor75); // The output is pre-formatted: just copy and paste to excel. Th

		System.out.print("\t");
		for(double strikes=initialStrike; strikes<=finalStrike
				; strikes+=strikeIncrement )
			System.out.print(formatDec0.format(strikes) + "\t");
		System.out.println("\n");


		for(double maturities=initialMaturity; maturities <= finalMaturity; maturities+=maturityIncrement){	
			System.out.print(
					formatDec2.format(maturities)+ "\t" );

			for(double strikes=initialStrike; strikes<=finalStrike
					; strikes+=strikeIncrement){
				AsianOption newAo=new AsianOption(maturities, strikes,  maturities*averagingFactor75);


				System.out.print(
						formatDec4.format( newAo.getValue(bsModel).getAverage()) + "\t");
			}
			System.out.println("\n");			

		} 


		System.out.println("Printable Array of Asian Call Prices for different maturities and strikes \n averaging factor : " + averagingFactor100); // The output is pre-formatted: just copy and paste to excel. Th

		System.out.print("\t");
		for(double strikes=initialStrike; strikes<=finalStrike
				; strikes+=strikeIncrement )
			System.out.print(formatDec0.format(strikes) + "\t");
		System.out.println("\n");


		for(double maturities=initialMaturity; maturities <= finalMaturity; maturities+=maturityIncrement){	
			System.out.print(
					formatDec2.format(maturities)+ "\t" );
           for(double strikes=initialStrike; strikes<=finalStrike
   				; strikes+=strikeIncrement){
//        	     System.out.println(maturity);
//     			
        	   AsianOption newAo=new AsianOption(maturities, strikes,  maturities*averagingFactor100);


				System.out.print(
						formatDec4.format( newAo.getValue(bsModel).getAverage()) + "\t");
			}
			System.out.println("\n");			

		} 
	
	
	
	
	}


    





}

	
	


