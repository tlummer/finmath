package com.timlummer.ex3;

public class MCIntegral {

	private double[] Simulations;
	private double a;
	private double b;
	
	
	MCIntegral (double from,double to,int Simulations){
		
		this.a = from;
		this.b = to;
		
		this.SinMCInt(Simulations);
		
	}
	
	
	private void SinMCInt( int m){
		
		this.Simulations = new double[m]; 
		
		for (int i = 0;i<m;i++){
			double x = this.a + (this.b-this.a) * Math.random();
				Simulations[i] =1.0/Math.pow(x,1.0/3);
		}
		
	}
			
	public double value() {
		
		double sum = 0;
		
		for(int i = 0; i< Simulations.length;i++){
			sum += Simulations[i];
		}
		
		return sum* (this.b- this.a) / (Simulations.length);
		
				
	}
	
	public double STD(){
		
		double mean = this.value()/(this.b- this.a);
		double sum = 0.0;
		
		
		for(int i = 0; i< Simulations.length;i++){
			sum += (mean-Simulations[i])*(mean-Simulations[i]);
		}
		
		return Math.sqrt(sum/(Simulations.length-1));
		
		
		
	}

}
