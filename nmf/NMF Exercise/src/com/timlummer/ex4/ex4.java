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
		
		System.out.println("");
		
		System.out.println("Excercise 2");
		
		double lambda = 0.2;
		int  sample = 1000;
		int test = 10000;
		
				RandomVariable exp2 = new ExponentialRandomVariable(lambda);
		
		MeanConfidenceIntegral CLT = new CLTConfidenceIntegral(exp2, sample);
		MeanConfidenceIntegral Cheb = new ChebyshevConfidenceIntegral(exp2, sample);
		
		System.out.println("");
		
		System.out.println("CLT 0.95 Test: "+ CLT.TestConfidenceInterval(0.95, test));
		System.out.println("Cheb 0.95 Test: "+ Cheb.TestConfidenceInterval(0.95, test));
		
		double my = 2.0;
		
		NormalRandomVariable NormalDist = new NormalRandomVariable(my, 1.0);
		
		double sums = 0.0;
		
		for(int i=0; i<test; i++){
			
			double[]X = NormalDist.generateBoxMuller();
			
			if(X[0] <my && X[1]<my){		
				sums++;
			}
			
				
		}
		
		System.out.println("Box muller: " + sums/test);
		
		sums = 0.0;
		
		for(int i=0; i<test; i++){
			
			double[]X = NormalDist.generateBivariateNormal();
			
			if(X[0] <my && X[1]<my){		
				sums++;
			}
			
				
		}
		
		System.out.println("BiVariate " + sums/test);
		
	}

}
