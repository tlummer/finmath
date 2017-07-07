package com.lorenzotorricelli.solex5;


import com.lorenzotorricelli.solex4.RandomVariable;

public class Histogram {


	public static double[] buildPdfHistogram(RandomVariable randomVariable, 
			double leftBound, double rightBound,
			int numberOfBins,
			int numberOfsimulations) throws Exception{
		double[] frequencies=new double[numberOfBins];
		double binSize=(rightBound-leftBound)/(numberOfBins) ;
		double value=0.0;	
		for(int simulationIndex=0; simulationIndex<numberOfsimulations; simulationIndex++){
			value=randomVariable.generate();
			for(int binIndex=0; binIndex<numberOfBins-1; binIndex++){
				if(leftBound+binIndex*binSize < value 
						&&  value < leftBound+(binIndex+1)*binSize ){
					frequencies[binIndex]+=1.0/numberOfsimulations; 
					break;
				}
			}

		}
		return frequencies;
	}

}
