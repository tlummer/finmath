package com.lorenzotorricelli.ex10;
import java.util.ArrayList;

import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.TimeDiscretization;

public abstract class JumpProcess {
	  TimeDiscretization times;
	  int numberOfRealisations;
	  double mu;
	  double lambda;
	  RandomVariableInterface path[]=null;
	  RandomVariableInterface Jumps[]=null;
	  ArrayList<Double>[] jumpTimes;  //an array of Arraylists; an array of dinamically allocated arrays. One dimension is the jump times length
	ArrayList<Double>[] jumpRealisations;  //an array of Arraylists; an array of dinamically allocated arrays. One dimension is the jump times length

      double initialValue;
      double jumpMean;
      double jumpStdv;

      
    	 
      
      public JumpProcess(double lambda, double mu, double jumpMean, double jumpStdv, double initialValue, double finalTime, int numberOfTimeSteps, int numberOfRealisations){ //abstract constructor
   	   this.lambda=lambda;
   	   this.mu=mu;
   	   this.initialValue=initialValue;
   	   double deltaT=(double) (finalTime/numberOfTimeSteps);
   	   times=new TimeDiscretization(0.0,  numberOfTimeSteps, deltaT );
   	   this.path=new RandomVariable[numberOfTimeSteps+1];
   	   this.jumpMean=jumpMean;
   	   this.jumpStdv=jumpStdv;
   	   this.numberOfRealisations=numberOfRealisations;
      }
      
      abstract void generate();
      
      abstract double[] getPath(int i);
      abstract void getPrintablePath(int realisation);
      
      
}
