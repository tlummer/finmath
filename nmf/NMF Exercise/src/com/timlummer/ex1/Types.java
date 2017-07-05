package com.timlummer.ex1;

public class Types {

	public static void main(String[] args) {
	
		System.out.println("Excercise 1");
		
		double one = 1.0;
		double nan = Double.NaN;
		
		System.out.println("one == Double.NaN:");
		System.out.println(one == Double.NaN);
		
		System.out.println("one < Double.NaN:");
		System.out.println(one < Double.NaN);
		
		System.out.println("one < Double.POSITIVE INFINITY:");
		System.out.println( one < Double.POSITIVE_INFINITY);
		
		System.out.println("nan ! = Double.NaN:");
		System.out.println( nan!= Double.NaN);
		
		System.out.println("Test Function:");
		
		System.out.println(Double.isNaN(one));
		System.out.println(Double.isNaN(0.0/0.0));
	
	
		System.out.println("");
		System.out.println("Excercise 2");
		System.out.println("");
		
		//double x = 3*0.1;
		float x_0 = 0.3f;
		
		System.out.println("x 0 = 0.3:");
		System.out.println(0.3 == x_0);
		
		System.out.println("Error");
		
		System.out.println("");
		System.out.println("Excercise 3");
		System.out.println("");
		
		System.out.println(CosineByTaylor(1,5));
		System.out.println(Math.cos(1));
		
		System.out.println(CosineByTaylor(7,10));
		System.out.println(Math.cos(7));
		
		System.out.println("");
		System.out.println("Excercise 4");
		System.out.println("");
		
		int N = 100000;
		
		System.out.println("Foward: "+ harmonicSumFoward(N));
		System.out.println("Backward: "+ harmonicSumBackward(N));
		
	}
	
	private static double CosineByTaylor(double x,int n){
		
		double CosApprox = 0;
				
		for(int i= 0; i< n +1 ;i++){
			
			CosApprox += Math.pow(-1,i) * Math.pow(x,2*i)/(factorial(2*i));
		}
		
		return CosApprox;		
	}
	
	public static double factorial(int n) { //a functional version. Use double gets a higher numerical overflow threshold
		if (n == 0)
			return 1;
		return n * factorial(n - 1);
	}
	
	public static double harmonicSumFoward(int n){
		double sum = 0;
		
		for(int i = 1;i<n+1;i++){
			
			sum += 1.0 / i; 
		}
		
		return sum;
	}
	
	public static double harmonicSumBackward(int n){
		double sum = 0;
		
		for(int i = n;i>0;i--){
			sum += 1.0/i; 
		}
		
		return sum;
	}
	
	
}
