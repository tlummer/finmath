package com.timlummer.ex3;

public class ex3 {

	public static void main(String[] args) {
		
		
	double a = 0;
	double b = 8;
	int m = 1000;
		
	MCIntegral mc = new MCIntegral(a,b,m);
	
	System.out.println("Integral Value = " + mc.value());
	System.out.println("STD = " + mc.STD());
	}
}
