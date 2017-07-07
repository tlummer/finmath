package com.lorenzotorricelli.ex9sol;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloBlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;


public class Ex9 {

	public static void main(String[] args) throws CalculationException {
		// TODO Auto-generated method stub


		NumberFormat formatDec4 = new DecimalFormat("00.0000");

		NumberFormat formatDec2 = new DecimalFormat("0.00");
		NumberFormat formatDec0 = new DecimalFormat("00");
		
		int numberOfTimeSteps=100;
		int numberOfSimulations=1000;
		double initialPrice=100.0;
		double finalTime=1.0;
		double a=0.2;
		double b=0.05*initialPrice;
		double strike=100.0;
		
		double riskFreeRate=0.0;
		
		 MyDisplacedLogNormalModel displacedLogNormal=
new MyDisplacedLogNormalModel(numberOfTimeSteps, finalTime, numberOfSimulations,
		initialPrice, riskFreeRate, a, b);
		
		 displacedLogNormal.generateEuler();
//		 System.out.println(displacedLogNormal.getFinalState());
//		 
		 System.out.println("Exercise 1 \n");
		 
		 System.out.println("The test"
		 		+ " of a call option price under the \n"
		 		+ "displaced lognormal model having strike \n" 
		 		+ strike + " maturity " + finalTime +" "
		 				+ "and paramters " + a + " and " + b + " is: " +
		 		displacedLogNormal.getFinalState().sub(strike).floor(0.0).getAverage());
		 System.out.println("\n");
		 
		 //Benchmarking with Black Scholes
		 
	
		  b=0.0; 
		  displacedLogNormal=new MyDisplacedLogNormalModel(numberOfTimeSteps, finalTime, numberOfSimulations, initialPrice, riskFreeRate, a, b);
		  displacedLogNormal.generateEuler();
			 
		 
		// System.out.println(displacedLogNormal.getFinalState());
		 
		 
		 MonteCarloBlackScholesModel bsModel= new	MonteCarloBlackScholesModel(  
					displacedLogNormal.getTimeDiscretization(),  //using the TimeDiscretization version of the BS model constructor
					numberOfSimulations,
					initialPrice,
					riskFreeRate,
					a);  //seeding has been set the same manually...try to improve this by defininf a method extracting the seed in the displaced model and then feed it in the blak Sholes model

			EuropeanOption eo=new EuropeanOption(finalTime, strike);  //random variable
			 
			//System.out.println(bsModel.getAssetValue(maturity, 0));
				
		 b=0.0;
			
		 System.out.println("Benchmarking with B-S: B-S value in the finmath library " 
		 + eo.getValue(0.0, bsModel).getAverage() + "\n" + " value in the displaced model setting b=0.0: "
				 +displacedLogNormal.getFinalState().sub(strike).floor(0.0).getAverage());
//		 
		 System.out.println("\n");
		 
		 double initialStrike=80;
	       double  finalStrike=120;
	       double strikeIncrement=10;
	       
	       double initialMaturity=0.25;
	       double finalMaturity=finalTime;
	       double maturityIncrement=0.25;
	      
	       int numberOfStrikes=(int)((finalStrike-initialStrike)/strikeIncrement);
	       int numberOfMaturities=(int)((finalMaturity-initialMaturity)/maturityIncrement);
	       
	       
	       double[][] pricesTable1 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       double[][] pricesTable2 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       double[][] pricesTable3 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       double[][] pricesTable4 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       

	       double[][] iVolsTable1 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       double[][] iVolsTable2 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       double[][] iVolsTable3 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       double[][] iVolsTable4 =new double[numberOfStrikes+1][numberOfMaturities+1];
	       
	       double[] aS={0.2, 0.15, 0.05, 0.0};
	       double[] bS={0.0, 0.05*initialPrice, 0.15*initialPrice, 0.2*initialPrice};
	       
	       double currentStrike;
	       double currentMaturity;
	       
			System.out.println("Printable Array of European Call Prices for different maturities and strikes. Black Scholes \n"); // The output is pre-formatted: just copy and paste to excel. Th

			System.out.print("\t");
			for(double strikes=initialStrike; strikes<=finalStrike
					; strikes+=strikeIncrement )                                   
				System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
			System.out.println("\n");


			for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
				   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
				
				   System.out.print(
						formatDec2.format(currentMaturity)+ "\t" );


				for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; 
						strikesIndex++){
					currentStrike=initialStrike+ strikesIndex*strikeIncrement;
					    
				pricesTable1[strikesIndex][maturitiesIndex]=displacedLogNormal
						.getAssetValue(currentMaturity).sub(currentStrike).floor(0.0).getAverage();
					System.out.print(
							formatDec4.format(pricesTable1[strikesIndex][maturitiesIndex]) + "\t");
					      }
				System.out.println("\n");			

			 
			} 
			
			//Second table
			
			  displacedLogNormal=new MyDisplacedLogNormalModel(numberOfTimeSteps, finalTime, numberOfSimulations, initialPrice, riskFreeRate, aS[1], bS[1]);
			  displacedLogNormal.generateEuler();
				
			  System.out.println("Printable Array of European Call Prices for different maturities and strikes. a = " + aS[1] + " b " + bS[1] + "\n"); // The output is pre-formatted: just copy and paste to excel. Th

				System.out.print("\t");
				for(double strikes=initialStrike; strikes<=finalStrike
						; strikes+=strikeIncrement )                                   
					System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
				System.out.println("\n");


				for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
					   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
					
					   System.out.print(
							formatDec2.format(currentMaturity)+ "\t" );


					for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; strikesIndex++){
						currentStrike=initialStrike+ strikesIndex*strikeIncrement;
						    
						
							pricesTable2[strikesIndex][maturitiesIndex]=displacedLogNormal.getAssetValue(currentMaturity).sub(currentStrike).floor(0.0).getAverage();
						System.out.print(
								formatDec4.format(pricesTable2[strikesIndex][maturitiesIndex]) + "\t");
						      }
					System.out.println("\n");			

				 
				}  
				//Third Table
				
				 
				  displacedLogNormal=new MyDisplacedLogNormalModel(numberOfTimeSteps, finalTime, numberOfSimulations, initialPrice, riskFreeRate, aS[2], bS[2]);
				  displacedLogNormal.generateEuler();
					
				  System.out.println("Printable Array of European Call Prices for different maturities and strikes. a = " + aS[2] + " b= " + bS[2] + "\n"); // The output is pre-formatted: just copy and paste to excel. Th

					System.out.print("\t");
					for(double strikes=initialStrike; strikes<=finalStrike
							; strikes+=strikeIncrement )                                   
						System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
					System.out.println("\n");


					for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
						   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
						
						   System.out.print(
								formatDec2.format(currentMaturity)+ "\t" );


						for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; strikesIndex++){
							currentStrike=initialStrike+ strikesIndex*strikeIncrement;
							    
							
								pricesTable3[strikesIndex][maturitiesIndex]=displacedLogNormal.getAssetValue(currentMaturity).sub(currentStrike).floor(0.0).getAverage();
							System.out.print(
									formatDec4.format(pricesTable3[strikesIndex][maturitiesIndex]) + "\t");
							      }
						System.out.println("\n");			

					 
					} 
				
					//Fourth Table
		
			  displacedLogNormal=new MyDisplacedLogNormalModel(numberOfTimeSteps, finalTime, numberOfSimulations, initialPrice, riskFreeRate, aS[3], bS[3]);
			  displacedLogNormal.generateEuler();
			
			  
			  System.out.println("Printable Array of European Call Prices for different maturities and strikes. Bachelier \n"); // The output is pre-formatted: just copy and paste to excel. Th
 

				System.out.print("\t");
				for(double strikes=initialStrike; strikes<=finalStrike
						; strikes+=strikeIncrement )                                   
					System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
				System.out.println("\n");


				for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
					   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
					
					   System.out.print(
							formatDec2.format(currentMaturity)+ "\t" );


					for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; strikesIndex++){
						currentStrike=initialStrike+ strikesIndex*strikeIncrement;
						    
						
							pricesTable4[strikesIndex][maturitiesIndex]=displacedLogNormal.getAssetValue(currentMaturity).sub(currentStrike).floor(0.0).getAverage();
						System.out.print(
								formatDec4.format(pricesTable4[strikesIndex][maturitiesIndex]) + "\t");
						      }
					System.out.println("\n");			

				 
				} 
	
    //Implied volatility extraction:
				System.out.println("Printable Array of European Call Implied Volatlities for different maturities and strikes. Black Scholes \n"); // The output is pre-formatted: just copy and paste to excel. Th

				System.out.print("\t");
				for(double strikes=initialStrike; strikes<=finalStrike
						; strikes+=strikeIncrement )                                   
					System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
				System.out.println("\n");


				for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
					   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
					
					   System.out.print(
							formatDec2.format(currentMaturity)+ "\t" );


					for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; strikesIndex++){
						currentStrike=initialStrike+ strikesIndex*strikeIncrement;
						    
						
			iVolsTable1[strikesIndex][maturitiesIndex]=
	AnalyticFormulas.blackScholesOptionImpliedVolatility(initialPrice*Math.exp(riskFreeRate*currentMaturity), 
			currentMaturity, currentStrike, Math.exp(-riskFreeRate*currentMaturity),
											pricesTable1[strikesIndex][maturitiesIndex]);
						System.out.print(
								formatDec4.format(iVolsTable1[strikesIndex][maturitiesIndex]) + "\t");
						      }
					System.out.println("\n");			
				}
					//Table 2
					 System.out.println("Printable Array of European Call Implied Volatilities for different maturities and strikes. a = " + aS[1] + " b= " + bS[1] + "\n"); // The output is pre-formatted: just copy and paste to excel. Th

					System.out.print("\t");
					for(double strikes=initialStrike; strikes<=finalStrike
							; strikes+=strikeIncrement )                                   
						System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
					System.out.println("\n");


					for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
						   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
						
						   System.out.print(
								formatDec2.format(currentMaturity)+ "\t" );


						for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; strikesIndex++){
							currentStrike=initialStrike+ strikesIndex*strikeIncrement;
							    
							
								iVolsTable2[strikesIndex][maturitiesIndex]=
										AnalyticFormulas.blackScholesOptionImpliedVolatility(initialPrice*Math.exp(riskFreeRate*currentMaturity), currentMaturity, currentStrike, Math.exp(-riskFreeRate*currentMaturity),
												pricesTable2[strikesIndex][maturitiesIndex]);
							System.out.print(
									formatDec4.format(iVolsTable2[strikesIndex][maturitiesIndex]) + "\t");
							      }
						System.out.println("\n");
					
				 
				}  
					
					//Table 3
					 System.out.println("Printable Array of European Call Implied Volatilites for different maturities and strikes. a = " + aS[2] + " b= " + bS[2] + "\n"); // The output is pre-formatted: just copy and paste to excel. Th

					System.out.print("\t");
					for(double strikes=initialStrike; strikes<=finalStrike
							; strikes+=strikeIncrement )                                   
						System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
					System.out.println("\n");


					for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
						   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
						
						   System.out.print(
								formatDec2.format(currentMaturity)+ "\t" );


						for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; strikesIndex++){
							currentStrike=initialStrike+ strikesIndex*strikeIncrement;
							    
							
								iVolsTable3[strikesIndex][maturitiesIndex]=
										AnalyticFormulas.blackScholesOptionImpliedVolatility(initialPrice*Math.exp(riskFreeRate*currentMaturity), currentMaturity, currentStrike, Math.exp(-riskFreeRate*currentMaturity),
												pricesTable3[strikesIndex][maturitiesIndex]);
							System.out.print(
									formatDec4.format(iVolsTable3[strikesIndex][maturitiesIndex]) + "\t");
							      }
						System.out.println("\n");
					
				 
				} 	
					//Table 2
					 System.out.println("Printable Array of European Call Implied Volatilities for different maturities and strikes. a = " + aS[3] + " b= " + bS[3] + "\n"); // The output is pre-formatted: just copy and paste to excel. Th

					System.out.print("\t");
					for(double strikes=initialStrike; strikes<=finalStrike
							; strikes+=strikeIncrement )                                   
						System.out.print(formatDec0.format(strikes) + "\t");  //print column headings
					System.out.println("\n");


					for(int maturitiesIndex=0; initialMaturity+ maturitiesIndex*maturityIncrement <= finalMaturity; maturitiesIndex++){	
						   currentMaturity=initialMaturity+ maturitiesIndex*maturityIncrement;
						
						   System.out.print(
								formatDec2.format(currentMaturity)+ "\t" );


						for(int strikesIndex=0; initialStrike+ strikesIndex*strikeIncrement <= finalStrike; strikesIndex++){
							currentStrike=initialStrike+ strikesIndex*strikeIncrement;
							    
							
								iVolsTable4[strikesIndex][maturitiesIndex]=
										AnalyticFormulas.blackScholesOptionImpliedVolatility(initialPrice*Math.exp(riskFreeRate*currentMaturity), currentMaturity, currentStrike, Math.exp(-riskFreeRate*currentMaturity),
												pricesTable4[strikesIndex][maturitiesIndex]);
							System.out.print(
									formatDec4.format(iVolsTable4[strikesIndex][maturitiesIndex]) + "\t");
							      }
						System.out.println("\n");
					
				 
				} 
			
					System.out.println("Exercise 2");
					System.out.println("\n");
					
					   
					 b=0.05*initialPrice;
					
				 DisplacedNormalMontecarlo finDisplacedLogNormal=new	DisplacedNormalMontecarlo(
							displacedLogNormal.getTimeDiscretization(),
							numberOfSimulations,
							initialPrice,
							 riskFreeRate,
							 a,
							b);
				 
				 EuropeanOption eo2=new EuropeanOption(finalTime, strike);
            
				 
				 
					System.out.println("same tests as in Exercise 1, Using " + strike + " maturity " + finalTime +" and paramters " + a + " and " + b + 
							" is: " +eo2.getValue(0.0, finDisplacedLogNormal).getAverage());
//					
					b=0.0;
					
			 finDisplacedLogNormal=new	DisplacedNormalMontecarlo(
							displacedLogNormal.getTimeDiscretization(),
							numberOfSimulations,
							initialPrice,
							 riskFreeRate,
							 a,
							b);
					
			 System.out.println("Benchmarking with B-S" + strike + " maturity " + finalTime +" and paramters " + a + " and " + b + 
						" is: " +eo2.getValue(0.0, finDisplacedLogNormal).getAverage());
			
				
	}

}
