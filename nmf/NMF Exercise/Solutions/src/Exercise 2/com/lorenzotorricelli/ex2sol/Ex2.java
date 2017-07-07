package com.lorenzotorricelli.ex2sol;

public class Ex2 {



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Exercise 1 \n" );

		double  annualRate=0.027;
		int daysOftheYear=365;
		int holdingPeriod=18*30;
		double initialwealth=2700;
		
		double dailyRate=dailyRate(annualRate, daysOftheYear);
		
		double money=bankAccount(initialwealth, dailyRate, holdingPeriod);
		double moneyRounded=bankAccountRounded(initialwealth, dailyRate, holdingPeriod);
		
		double absoluteDifference=money-moneyRounded;
		double relativeDifference=(money-moneyRounded)/moneyRounded*100;
		double arithmeticAverageRate=annualRate/(double) daysOftheYear;
		
		System.out.println("The daily rate equals to " + dailyRate + " while the daily arithmetic average of the annual rate is  "
				+  arithmeticAverageRate  );
		System.out.println("with an error of: " + Math.abs(dailyRate-arithmeticAverageRate)/dailyRate );
		System.out.println("At the end of the period the total in the bank account is " + money);
		System.out.println("By rounding to the lowest eurocent the bank account is reduced to " + moneyRounded);
		System.out.println(" with a difference of " + absoluteDifference  + 
				" euros in absolute terms which corresponds to " + relativeDifference +"% in relative terms." );

		System.out.println("\n" );


		System.out.println("Exercise 2 \n" );

		int numberOfPoints=1000000;
		int numberOfSimulations=100;
		double[] MonteCarloPiSequence=new double[numberOfSimulations];
		System.out.println( "Array of Monte Carlo values for pi: ");

		for(int i=0; i<numberOfSimulations; i++){
			MonteCarloPiSequence[i]=piMonteCarlo(numberOfPoints);
			System.out.print(MonteCarloPiSequence[i] + " ");
		}


		System.out.println("\n" );

		System.out.println( "Mean: " + arrayMean(MonteCarloPiSequence));

		System.out.println( "Standard Deviation: " + arraySTDV(MonteCarloPiSequence));



		System.out.println("\n" );


		System.out.println("Exercise 3 \n" );

		int partitionPoints=1000;
		int numberOfIntegralPoints=100000;
		double[] MonteCarloIntegralSequence=new double[numberOfSimulations];
		double[] TrapezoidIntegralSequence=new double[numberOfSimulations];


		System.out.println( "Array of deterministic integrals using the trapezoidal rule for the sequence pi_n in Exercise 2: ");

		for(int i=0; i<numberOfSimulations; i++){
			TrapezoidIntegralSequence[i]=SineIntegralTrapezoid(-MonteCarloPiSequence[i] , MonteCarloPiSequence[i], partitionPoints )
					;
			System.out.print(TrapezoidIntegralSequence[i] + " ");
		}

		System.out.println("\n" );

		System.out.println( "Mean: " + arrayMean(TrapezoidIntegralSequence));

		System.out.println( "Standard Deviation: " + arraySTDV(TrapezoidIntegralSequence));

		System.out.println("\n" );


		System.out.println( "Array of Monte Carlo Integral values for "
				+ "the sequence pi_n in Exercise 2: ");

		for(int i=0; i<numberOfSimulations; i++){
			MonteCarloIntegralSequence[i]=SineIntegralMonteCarlo(-MonteCarloPiSequence[i] , MonteCarloPiSequence[i], numberOfIntegralPoints )
					;
			System.out.print(MonteCarloIntegralSequence[i] + " ");
		}


		System.out.println("\n" );

		System.out.println( "Mean: " + arrayMean(MonteCarloIntegralSequence));

		System.out.println( "Standard Deviation: " + arraySTDV(MonteCarloIntegralSequence));



	}

	public static double dailyRate(double annualRate, int days){
		return Math.pow(1+annualRate,1.0/(double)days)-1;}

	public static double bankAccount(double money, double rate, int period){
		double finalWealth=money;
		for(int i=1; i<=period; i++)
			finalWealth*=(1+rate);
		return finalWealth;
		//return money*Math.pow((1+rate), period); avoid using built-in functions when you can
	}

	public static double bankAccountRounded(double money, double rate, int period){
		double finalWealth=money*100;
		for(int i=1; i<=period; i++)
			finalWealth=(long)(finalWealth*(1+rate));  //casting always rounds down
		return (double) finalWealth/100.0;
	}

	public static double piMonteCarlo(int n ){ 
		int sum=0;		 
		for(int i=0; i<n; i++){
			double x=Math.random();
			double y=Math.random();
			if(x*x + y*y<1) {sum+=1;}
		}
		return 4*(double) sum/n ;
	}

	public static double SineIntegralMonteCarlo(double b, double a, int n ){ 
		double sum=0;		 
		for(int i=0; i<n; i++){
			double x=a+(b-a)*Math.random();
			sum+=Math.sin(x);
		}
		return (b-a)* sum/n ;
	}

	public static double SineIntegralTrapezoid(double b, double a, int n ){ 
		double step=(b-a)/n;
		double   prev=Math.sin(a);
		double current=0.0;
		double sum=0;           //a version with more variables but less calculations
		for(int i=1; i<n+1; i++){
			current=Math.sin(a+i*step);
			sum+=(current+prev);
			prev=current;
		}
		return  step/2*sum;
	}




	public static double arrayMean(double a[]){
		double mean=0;
		for(double element: a) //foreach syntax
			mean+=element;
		return mean/a.length;	  
	}


	public static double arraySTDV(double a[]){
		double STDV=0;
		double mean=arrayMean(a);
		for(double element: a)       //foreach syntax
			STDV+=(element-mean)*(element-mean);
		return Math.sqrt(STDV/(a.length-1));	// Notice the -1 !  
	}

}
