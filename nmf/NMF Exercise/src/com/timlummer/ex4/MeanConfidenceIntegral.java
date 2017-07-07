package com.timlummer.ex4;

public abstract class MeanConfidenceIntegral {
	int sample;
	RandomVariable randomVariable;
	
	double getUpperBoundConfidenceInterval(double level){
		return level;
	}
	
	double getLowerBoundConfidenceInterval(double level){
		return level;
	}
	
	double TestConfidenceInterval(double level,int NumberOfTests){
		double sum=0;
		double mean=randomVariable.getMean();
				
		for(int i=0; i<NumberOfTests; i++){
			if(mean> getLowerBoundConfidenceInterval(level) 
					&& mean<getUpperBoundConfidenceInterval( level) )
				sum++;
		}  
		return sum/NumberOfTests;
	}
		
}
