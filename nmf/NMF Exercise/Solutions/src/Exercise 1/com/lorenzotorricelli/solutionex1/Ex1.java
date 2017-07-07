package com.lorenzotorricelli.solutionex1;

public class Ex1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double one = 1;
		double nan = Double.NaN;

		System.out.println("Exercise 1 \n");

		System.out.println("The statement one == Double.NaN is " + equal(one, Double.NaN));
		System.out.println("The statement one < Double.NaN is " + greater(Double.NaN, one));
		System.out.println("The statement one < Double.POSITIVE_INFINITY is " + greater(Double.POSITIVE_INFINITY, one));
		System.out.println("The statement nan != Double.NaN is " + differentFrom(nan, Double.NaN)); 
		
		System.out.println("\n");

		System.out.println("Exercise 2 \n");

		float x0 = 3.0f * 0.1f; // double...
		float x = 0.3f;
		System.out.println(x == x0);
		double epsilon = 1.0; //doubel...
		int exponent = 0;

		while (x0 + epsilon > x && x > x0 - epsilon) { 
//			 the biggest epsilon such that 
//			it is true that !x-x0!>epsilon is
//			(close enough to) the smallest epsilon 
			//such that it is false that !x-x0!<epsilon!! (can only decrease epsilon as a power of two..not increase it)  
			epsilon /= 2.0;
			exponent++;
		}
		System.out.println("The biggest power n such that x+1/2^n is different from x is:  " + exponent 
				+ " , for which 1/2n equals " + epsilon);

		System.out.println("\n");

		System.out.println("Exercise 3 \n");

		double value1 = 1;
		double value2 = 7;
		int order = 5;

		System.out.println("The machine value of cosine at " + value1 + " is " + Math.cos(value1)
		+ "; that of the McLaurin series of order " + order + " is " + cosineByTaylor(value1, order));

		System.out.println("The machine value of cosine at " + value2 + " is " + Math.cos(value2)
		+ "; that of the McLaurin series of order " + order + " is "
		+ cosineByTaylor(value2, order));

		System.out.println("The machine value of cosine at " + value2 + " is " + Math.cos(value2)
		+ "; that of the McLaurin series of order " + order + " applying the reduction to the natural phase domain is "
		+ cosineByTaylor(cosineReduction(value2), order));



		System.out.println("\n");

		System.out.println("Exercise 4 \n");

		int harmonicSumOrder = 1000000;

		System.out.println("The forward harmonic sum of order " + harmonicSumOrder + " is "
				+ harmonicSumForward(harmonicSumOrder));
		System.out.println("The backward harmonic sum of order " + harmonicSumOrder + " is "
				+ harmonicSumBackward(harmonicSumOrder)); //roundings differ if the cumulated sum at 0<i<n differ!
	}

	static boolean equal(double a, double b) {
		return a == b;
	}

	static boolean differentFrom(double a, double b) {
		return a != b;
	}

	static boolean greater(double a, double b) {
		return a > b;
	}

	static double cosineByTaylor(double x, int n) {
		double taylorApproximation = 0;
		if (x % 2 * Math.PI == 0)
			return 1;
		if (x % 2 * Math.PI == 1) //we now this already!! Speeds up the spproximation AND takes care for the discontinuity problem when performing the reduction
			//of R to the interval [0,Pi]
			return -1;

		for (int i = 0; i < (n + 1); i++)
			taylorApproximation += Math.pow(-1, i) / (factorial(2 * i)) * Math.pow(x * x, i);
		return taylorApproximation;
	}

	public static double factorial(int n) { //a functional version. Use double gets a higher numerical overflow threshold
		if (n == 0)
			return 1;
		return n * factorial(n - 1);
	}  

	public static double cosineReduction(double d) {
		return d % (Math.PI);  // I COULD reduce to the interval [0, 2 pi], but for any given Taylor order and type used in the factorial method,
		//this would result in a worse series approximation. Note that there is no discontinuity problem due to the separate handling of the multiples of PI in the approximation.
	}

	public static double harmonicSumForward(int n) {
		double sum = 0;
		for (int i = 1; i < n + 1; i++)
			sum += (double) (1.0 / i);
		return sum;
	}

	public static double harmonicSumBackward(int n) {
		double sum = 0;
		for (int i = n; i > 0; i--)
			sum +=(double) (1.0 / i);
		return sum;
	}

}
