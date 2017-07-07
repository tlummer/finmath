package com.lorenzotorricelli.ex11;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.lorenzotorricelli.solex4.NormalRandomVariable;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.time.TimeDiscretization;

public class BrownianBridge {
       private double initialValue;
       private double initialTime;
	
       private double finalValue;
       private double finalTime;
       
       private TimeDiscretization times;
       
       private int numberOfSimulations;
   
       
       private RandomVariable[] paths;
       private double [][] standardNormalIncrements; 
       
  	      private int exponentOfTwo;
       
       
       public BrownianBridge(double initialValue, double initialTime, double finalValue, double finalTime, int exponentOfTwo, int numberOfSimulations){
    	   this.initialTime=initialTime;
    	   this.initialValue= initialValue;
    	   this.finalTime=finalTime;
    	   this.finalValue=finalValue;
    	   this.exponentOfTwo=exponentOfTwo;
    	   this.numberOfSimulations=numberOfSimulations;
    	   this.times=new TimeDiscretization(initialTime, (int)Math.pow(2, exponentOfTwo), (finalTime-initialTime)/(Math.pow(2, exponentOfTwo))); //if you don´t cast to int the second argument the compiler thinks you are calling the vararg constructor :))
           
    	   
    	   NormalRandomVariable normal= new NormalRandomVariable(0.0, 1.0);
    	
    	   standardNormalIncrements=new double[times.getNumberOfTimes()][numberOfSimulations];
    			   
    	   for(int simulationIndex=0; simulationIndex<numberOfSimulations; simulationIndex++) {
    		   for(int timeIndex=0; timeIndex< times.getNumberOfTimes(); timeIndex++ ){
    			   standardNormalIncrements[timeIndex][simulationIndex]=normal.generate();
    	   }
    	   }   
       }
       //overloaded constructor. Gets a matrix of increments as an input to assign to the field
       public BrownianBridge(double initialValue, double initialTime, 
    		   double finalValue, double finalTime, int exponentOfTwo, int numberOfSimulations,
    		   double[][] standardNormalIncrements ){
    	   this.initialTime=initialTime;
    	   this.initialValue= initialValue;
    	   this.finalTime=finalTime;
    	   this.finalValue=finalValue;
    	   this.exponentOfTwo=exponentOfTwo;
    	   this.numberOfSimulations=numberOfSimulations;
    	   this.times=new TimeDiscretization(initialTime, (int)Math.pow(2, exponentOfTwo), 
    			   (finalTime-initialTime)/(Math.pow(2, exponentOfTwo))); //if you don´t cast to int the second argument the compiler thinks you are calling the vararg constructor :))
           this.standardNormalIncrements=standardNormalIncrements;
    	   
    	   }
    	      
       
       
       
       
       void generateBrownianBridge(){
    	   paths=new RandomVariable[times.getNumberOfTimes()]; //lazy initialisation
    	   int newPointIncrement=times.getNumberOfTimeSteps();  // increment to set the new point; h is an int even though it gets divided by two!
    	 
    	   double timeGap=(finalTime-initialTime);  //time gap size at evry iteration:  Deltah
    	   double numberOfLevelIterations=1; //diadic maximum index doubling everytime to produce the new points : j
    	   
    	   int  previousPointIterator=0;
    	   int  currentPointIterator=0;
    	   int   nextPointIterator=0;
    	   
    	   double mean=0.0;
    	   double variance=0.0;
 		  
    	   
    	   double[][] pathsVector=new double[times.getNumberOfTimes()][numberOfSimulations];
   	   
    	//  double[] randomVariates=new double[times.getNumberOfTimes()];
    	   
    
    	   
    	   for(int realisationIndex=0; realisationIndex< numberOfSimulations; realisationIndex++){  //loop over all the required simulations
    	 
    	   pathsVector[0][realisationIndex]=initialValue;
    	   pathsVector[times.getNumberOfTimes()-1][realisationIndex]=finalValue;
    			    		   
    	   for(int powerOfTwoIterator=0; powerOfTwoIterator< exponentOfTwo; powerOfTwoIterator++){  //loops through all the  diadic levels
    		   previousPointIterator=0;  //reset iterators with the right increment value for the new points to be added
    		   currentPointIterator=newPointIncrement/2;
    		   nextPointIterator=newPointIncrement;
    		   for(int intervalIndex=0; intervalIndex< numberOfLevelIterations; intervalIndex++){
    			   //loops all the intervals at the current diadic level
    			   mean=(pathsVector[previousPointIterator][realisationIndex]+pathsVector[nextPointIterator][realisationIndex])/2;
    			   variance=timeGap/2;
    			   pathsVector[currentPointIterator][realisationIndex]
    					   =mean+Math.sqrt(variance)*standardNormalIncrements[currentPointIterator][realisationIndex]; 
    			   
    			   previousPointIterator+=newPointIncrement;  //djourn iterators to move on the next interval iterators with the right increment value for the new points to be added
        		   currentPointIterator+=newPointIncrement;
        		   nextPointIterator+=newPointIncrement;
    		   }
    		   numberOfLevelIterations*=2;  //adjourn for the next diadic partition level
    		   timeGap/=2;
    		   newPointIncrement/=2;
    	   }
    		   
    	   }
    	    // wrapping in random variable
    	  
    	   paths[0]=new RandomVariable(initialTime, initialValue);
    	   
    	  for(int timeIndex=1; timeIndex < times.getNumberOfTimes(); timeIndex++ ){
    		  paths[timeIndex]=new RandomVariable(timeIndex*times.getTimeStep(timeIndex-1), pathsVector[timeIndex]);
    	}
    	   
    	
       }

 	  double[] getPath (int pathNumber){                 //getter for the path
 			 double[] pathI=new double[times.getNumberOfTimes()];
 			 for (int timeIndex=0; timeIndex<pathI.length; timeIndex++){
 				 pathI[timeIndex]=paths[timeIndex].get(pathNumber);
 			 }
 			 return pathI;
 			}
 	  
 	 void getPrintablePath(int realisation){  //print method to format for plotting
		 NumberFormat formatDec2 = new DecimalFormat("0.0000");
			
		 double[] printable=getPath(realisation);
		 for(int i=0; i<printable.length; i++){
			 System.out.println(formatDec2.format(printable[i]) + "\t");
			 
		 }
		 
	 }

	 TimeDiscretization getTimeDiscretization(){
		 return times;
	 }
	 
	 double[][] getStandardNormalIncrements(){
		 return standardNormalIncrements;
	 }
}
       
	
	
	
