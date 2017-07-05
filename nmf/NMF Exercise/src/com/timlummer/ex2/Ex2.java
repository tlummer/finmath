package com.timlummer.ex2;

public class Ex2 {

	public static void main(String[] args) {
	

		System.out.println("Excercise 1");
		
		double AnnualInterest = 0.027;
		
		double DailyInterest = AnnualToDaily(AnnualInterest,365);
		
		System.out.println("Daily Rate is "+ DailyInterest);
		System.out.println("Arethmetic Rate is "+AnnualInterest/365.0 );
		
		double IniValue = 2700.0;
		double days =  18*30;
		
		double EndValue =  IniValue * Math.pow(1+DailyInterest, days);
		
		double EndValueWithRoundings = IniValue;
		
		for(int i = 1; i < days+1;i++){
			
			EndValueWithRoundings = (double) ((long)(EndValueWithRoundings *(1.0 + DailyInterest)*100))/100;
					
		}	    
		
		System.out.println("");
		System.out.println("Without Round "+ EndValue);
		System.out.println("With Round "+EndValueWithRoundings);
		
		
		System.out.println("Excercise 2");
		
		long Sims = 1000000;
		
		int NumOfRuns = 100;
		
		double[] MonteCarloPIs = new double[NumOfRuns];
		double[] PiErrors = new double[NumOfRuns];
		
		
		for(int i = 0;i<NumOfRuns;i++){
			MonteCarloPIs[i] = MonteCarloPi(Sims);
			PiErrors[i] = Math.abs((MonteCarloPIs[i] - Math.PI));
		}
				
		System.out.println("Mean Pi: " + mean(MonteCarloPIs));
		System.out.println("STD Pi: " + STD(MonteCarloPIs));
		
		System.out.println("Mean PiErrors: " + mean(PiErrors));
		System.out.println("STD PiErrors: " + STD(PiErrors));
		
		
	}
	
	
	private static double AnnualToDaily(double rate,int n){
		
		return Math.pow(1.0 + rate, 1.0/n)-1;
		
	}
	
	private static double MonteCarloPi (long numberOfSimulations){
		
	long NumberOfPointsInCircle = 0;
	
	for(long i = 0; i< numberOfSimulations;i++){
		double x = 2.0 * (Math.random() - 0.5);
		double y = 2.0 * (Math.random() - 0.5);
		
		if(x*x + y*y < 1) NumberOfPointsInCircle++;
	}
	
	return 4.0*(double)NumberOfPointsInCircle/(double)numberOfSimulations;
	
	}
	
	private static double mean(double arr[]){
		double sum=0;
		for(double element: arr)
			sum+=element;
		return sum/arr.length;	
			
	}
	
	private static double STD(double arr[]){
		double sum=0;
		double stdmean = mean(arr);
		for(double element: arr)
			sum+= (element-stdmean)*(element-stdmean);
		return Math.sqrt(sum/(arr.length-1));	
			
	}
	
//	private static SinMCInt(double a, double b, int n){
		
	//	for (int i = 0;to)
		
		
		
	
	
}
