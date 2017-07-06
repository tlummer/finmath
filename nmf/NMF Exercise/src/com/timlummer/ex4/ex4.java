package com.timlummer.ex4;

public class ex4 {

	public static void main(String[] args) {
	
		System.out.println("Exp Verteilung");
	
		ExponentialRandomVariable Exp = new ExponentialRandomVariable(0.5);
		NormalRandomVariable NV = new NormalRandomVariable(0.0 , 1.0);
		
		System.out.println("Sample Mean" + Exp.getSampleMean(1000));
		System.out.println("Sample STD"+ Exp.getSampleStdDeviation(1000));
		
		System.out.println(" Mean" + Exp.getMean());
		System.out.println(" STD"+ Exp.getStdDeviation());
		
		
		System.out.println("Normal Verteilung");
		
		System.out.println("Sample Mean" + NV.getSampleMean(1000));
		System.out.println("Sample STD"+ NV.getSampleStdDeviation(1000));
		
		System.out.println(" Mean" + NV.getMean());
		System.out.println(" STD"+ NV.getStdDeviation());
		

	}

}
