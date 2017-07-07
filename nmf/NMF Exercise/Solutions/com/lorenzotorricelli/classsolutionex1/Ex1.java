package com.lorenzotorricelli.classsolutionex1;

public class Ex1 {
	public static void main(String[] args) {
	double one= 1.0;
	double nan=Double.NaN;
	System.out.println("First comparison" + (one==nan) );
	System.out.println("Second comparison" + (one < Double.NaN)  );	
	System.out.println("Third comparison" + (one < Double.POSITIVE_INFINITY) );	
	System.out.println("Fourth comparison" + (nan !=  Double.NaN) );	
	System.out.println("Fifth comparison" + (Double.NEGATIVE_INFINITY < Double.POSITIVE_INFINITY) );	
	
	double x0=0.3;
	double x= 3f*0.1f;
	System.out.println(x==x0);

	double epsilon=1.0;
	int numberOfIterations=0;
	
	while( x>x0+epsilon && x<x0-epsilon   ){
		epsilon/=2;
		numberOfIterations++;
	}
	System.out.println("Biggest epsilon such that x is different from x0 " + numberOfIterations);
	
	int order=8;
	double value=7*Math.PI;
	
	double taylorApprox=mcLaurinExpansion(value,order);
	System.out.println("Taylor Approximation" +taylorApprox);
	System.out.println("Analytical value"+ Math.cos(value));
	System.out.println("Reduced solution: reduction at point "  + moduloPiReduction(value) + ": value"
+	mcLaurinExpansion(moduloPiReduction(value),order) );
	
	int orderOfHarmonicSum=1000000;
	System.out.println("Result of the forward harmonic sum " + forwardHarmonicSum(orderOfHarmonicSum) );
	System.out.println("Result of the forward harmonic sum " + backwardHarmonicSum(orderOfHarmonicSum) );
	
	}
	
	static double mcLaurinExpansion(double  x, int taylorOrder){
		double sum=0.0;
		for(int term=0; term<taylorOrder; term++){
			sum+=Math.pow(x,2*term)*Math.pow(-1, term)/factorial(2*term);
		}
		return sum;
		}
	
	static double factorial(int n){
		int fact=1;
	for(int i=1; i<=n; i++){
		fact*=i;
	}
	return fact;
	}
	
	static double moduloPiReduction(double  x){
	return (x+Math.PI)%(2*Math.PI)-Math.PI;
	}
	
	
	static double forwardHarmonicSum(int n){
		double  sum=0;
		for(int i=1; i <= n; i++){
			sum+=1/i;
		}
		return sum;
	}
	
	static double backwardHarmonicSum(int n){
		double  sum=0;
		for(int i=n; i > 0; i--){
			sum+=1/i;
		}
		return sum;
	}

	
}
