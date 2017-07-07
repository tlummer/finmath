package com.lorenzotorricelli.ex11;

import java.util.Arrays;

public class Ex11 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		    double initialValue=0.0;
	        double initialTime=0.0;
		
	        double finalValue=0.0;
	        double finalTime=1.0;
	       
	        double finalValueAsymmetric=1.0;
		       
	        
	        int numberOfSimulations=1;
	        
	        int samplePath=0;
	        
	        int exponentOfTwo=4;

	        BrownianBridge bbSymmetric=
	        		new BrownianBridge(initialValue, 
	        				initialTime, finalValue,  finalTime, exponentOfTwo, numberOfSimulations);
	        BrownianBridge bbAsymmetric= new BrownianBridge(initialValue,  initialTime, finalValueAsymmetric,  finalTime, exponentOfTwo, numberOfSimulations, bbSymmetric.getStandardNormalIncrements() );   // the asymmetric b is constructed strting from the same underlying normal realisations of the symmetric ones but its mean and variances will be different!
		        
	        
	        
	        
	        bbSymmetric.generateBrownianBridge();

	        
	        bbAsymmetric.generateBrownianBridge();
	        
	        System.out.println( bbSymmetric.getTimeDiscretization());
	        System.out.println("\n");
	        System.out.println("A sample path of the Symmetric Brownian Bridge: " + Arrays.toString(bbSymmetric.getPath(samplePath)));
	        System.out.println("A sample path of the Asymmetric Brownian Bridge: " + Arrays.toString(bbAsymmetric.getPath(samplePath)));
	        
	       bbSymmetric.getPrintablePath(samplePath);
	       System.out.println("\n");
	       bbAsymmetric.getPrintablePath(samplePath);
         
	         
	        
	        
	}
	
	

}
