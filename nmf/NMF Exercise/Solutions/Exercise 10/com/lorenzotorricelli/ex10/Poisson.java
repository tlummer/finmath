package com.lorenzotorricelli.ex10;

public class Poisson  {
	        double lambda;
	        
	        Poisson(double lambda){
	        	this.lambda=lambda;
	        }
	public int generatePoisson() {
		   
		  double p = 1.0;
		  int k = 0;

		  do {
		    k++;                           
		    p *= Math.random();
		  } while (p > Math.exp(-lambda));   

		  return k - 1;  // a poisson random variable takin value k means that we had to to simulate
		}

}
