package com.lorenzotorricelli.solex5;



public class Trapezoid {
	public static double trapezoidMethod(Function<Double> f, double leftPoint, double rightPoint, int numberOfPoints ){
		double step=(rightPoint-leftPoint)/numberOfPoints;
		double previous=leftPoint;
		double current;
		double sum=f.evaluate(leftPoint);
				for(int index=0; index<numberOfPoints; index++){
					current=f.evaluate(previous+index*step);
					
					sum+=current;
					previous=current;					
				}
		return sum*step/2;
	}
}




