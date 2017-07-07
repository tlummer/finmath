package com.lorenzotorricelli.ex10;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.lorenzotorricelli.solex4.ExponentialRandomVariable;
import com.lorenzotorricelli.solex4.NormalRandomVariable;

import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.stochastic.RandomVariableInterface;

public class ExponentialSum extends JumpProcess{

	private ArrayList<Double> jumpRealisations;  //Maybe one could put these in another interface extending Jumpr Process isnce are commeon fields of mehtods a and b
	private ArrayList<Double> jumpTimes;   //lazy initialisation
	
	public ExponentialSum(double lambda, double  mu, double jumpMean,  double jumpStdv, double initialValue ,double  finalTime,int numberOfTimeSteps, int numberOfRealisations){
		super(lambda,  mu, jumpMean,  jumpStdv,  finalTime, initialValue, numberOfTimeSteps, numberOfRealisations);  	  
	}

	public void generate(){  
		
		jumpRealisations=new ArrayList<Double>();
		jumpTimes=new ArrayList<Double>();  //lazy initialisation
				
		double elapsedTime;
		double currentJumpTime;
		double jump;

		double last=times.getTime(times.getNumberOfTimeSteps()); //get final time



		path[0]=new RandomVariable(0.0, initialValue); //in this method having path as a RandomVariabl  is not very useful because all the random component are just a single double

		ExponentialRandomVariable e =new ExponentialRandomVariable(lambda); //exponential random variable generator

		elapsedTime=e.generate(); //first exponential

		while(elapsedTime <  last ) { 
			jumpTimes.add(elapsedTime); // .add(double d) adds an element to he list
			elapsedTime+=e.generate();
		} 


		jumpTimes.add(last); //dummy jump, so that the hasnext() control condtion on the while lump for jumps work

		final Iterator<Double> jumpTimesIterator=jumpTimes.iterator();




		currentJumpTime=jumpTimesIterator.next();


		for(int timeIndex=1; timeIndex<times.getNumberOfTimes(); timeIndex++){
			path[timeIndex]=path[timeIndex-1].add(new RandomVariable(times.getTime(timeIndex), mu*times.getTimeStep(timeIndex-1))); //create a random variable that also keeps track of time progression, otherwise just used add(mu*times.getTimeStep…...)


			NormalRandomVariable jumpRv=new NormalRandomVariable(jumpMean, jumpStdv);

			while(currentJumpTime < times.getTime(timeIndex)  && jumpTimesIterator.hasNext()){
				jump=jumpRv.generate();  //generate jump			     
				path[timeIndex]=path[timeIndex].add(jump);   // adds it to the path. here path has always dimension 1
				jumpRealisations.add(jump); //appends it to the jump list
				currentJumpTime=jumpTimesIterator.next();

			}



		}


	}




	ArrayList<Double> getJumpTimes(){   //arrayLists of jumps and jump times
		return jumpTimes;
	}
	ArrayList<Double> getJumps(){
		return jumpRealisations;
	}


	double[] getPath(int i){
		double[] pathI=new double[times.getNumberOfTimes()];
		for (int timeIndex=0; timeIndex<pathI.length; timeIndex++){
			pathI[timeIndex]=path[timeIndex].get(i);
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
	 
	 double getFinalValue(int pathNumber){
			return path[times.getNumberOfTimes()-1].get(pathNumber);
		}
		

}
