package com.lorenzotorricelli.solex4;

public abstract class MeanConfidenceInterval {
	RandomVariable randomVariable;
	int sampleSize;
	public abstract double getLowerBoundConfidenceInterval( double level); // one abstract method. The confidence interval is Specific of the limit theorem
	public abstract double getUpperBoundConfidenceInterval(double level);
	public double frequenceOfInterval( int numberOfDrawings, double level){  
		double sum=0;
		double mean=randomVariable.getMean();
		for(int i=0; i<numberOfDrawings; i++){
			if(mean> getLowerBoundConfidenceInterval(level) 
					&& mean<getUpperBoundConfidenceInterval( level) )
				sum++;
		}  
		return sum/sampleSize;
	}
};

