package com.lorenzotorricelli.solex4;

public class NormalRandomVariable implements RandomVariable{
	private double mu;
	private double sigma;
	private int orderOfErf=10;


	public NormalRandomVariable(double mu, double sigma){ //Constructor
		this.mu=mu;
		this.sigma=sigma;
	}

	public double getMu(){ //getter
		return mu;
	}

	public double getSigma(){ //getter
		return sigma;
	}



	@Override 
	public double densityFunction(double x){
		return Math.exp(-x*x/2)/Math.sqrt(2*Math.PI);
	}   //stdev and mu not involved (standard normal)


@Override
	public double cdfFunction(double x){
		return 0.5*(1+errorFunction((x-mu)/(Math.sqrt(2)*sigma)));
	}   

	double errorFunction(double x){ //using taylor expansion of erf; from Abramowitz and stegun 7.1.5
		double product=x;
		double factorial=1;
		double sum=x;
		for(int i=1;i<=orderOfErf; i++){
			factorial*=i;
			product*=x*x;
			sum+=parity(i)*product/(factorial*(2*i+1));
		}
		return sum*2/Math.sqrt(Math.PI);
	}

	private double parity(int n){
		if(n%2==0) return 1;
		return -1;
	}


	@Override 
	public double quantileFunction(double x){
		if(x>0.5)
			return  sigma*complementaryQuantileFunction(1-x)+mu;
		return -sigma*complementaryQuantileFunction(x)+mu;
	}   

	private double complementaryQuantileFunction(double x){
		//from Abramowitz and Stegun 26.2.23; called by the method above, i.e. this is private: implementation, not interface
		final double C0=2.515517;
		final double C1=0.802853;
		final double C2=0.010328;
		final double D1=1.432788;
		final double D2=0.189269;
		final double D3=0.001308;
		double t=Math.sqrt(-2.0*Math.log(x));

		return t-(C0+C1*t+C2*t*t)/(1+D1*t+D2*t*t+D3*t*t*t); 
	}




	@Override
	public double generate(){  //generation
		return quantileFunction(Math.random());
	}	

	  public double generateAR(){  //generation
	         
	        double u,y;
	         
	        ExponentialRandomVariable e=
	        		new ExponentialRandomVariable(1.0);
	        do{
	             
	        u=Math.random();
	        y=e.generate();
	     
	 
	        } while(u> Math.exp(-(y-1)*(y-1)/2)) ; 
	        if(Math.random()<0.5)
	        return sigma*y+mu;
	        else return -sigma*y+mu;
	    }   
	      

	public Pair<Double, Double> generateBoxMuller(){ //Multiple return using a generic class
		double u=Math.random();
		double v=Math.random();
		return  new Pair<Double, Double>(sigma*Math.sqrt(-2.0*Math.log(u))*Math.cos(2*Math.PI*v)+mu ,
				sigma*Math.sqrt(-2.0*Math.log(u))*Math.sin(2*Math.PI*v)+mu);
	}


	public Pair<Double, Double> generateBivariateNormal(){  //generation
		return  new Pair<Double, Double>(generate(), generate());
	}	



	public Pair<Double, Double> generateARBoxMuller(){
		double u,v,r,s;
		do{
			u=2*Math.random()-1;
			v=2*Math.random()-1;
			r=u*u+v*v;
		} while(r>1);
		s=Math.sqrt(-2.0*Math.log(r)/r);
		return new Pair<Double, Double>(sigma*u*s+mu , sigma*v*s+mu);

	}





	@Override
	public double getStdDeviation(){ //Pure delegation within a class. NOT useless! getSigma and getMu are specific parameter getters
		//while this are interface implementing mehods! (they only happen to be trivial because of the Normal parametirsation
		return getSigma();
	}


	@Override
	public double getMean(){ 
		return getMu();
	}



	@Override
	public double getSampleMean(int n){
		double sm=0;
		for(int i=0; i<n; i++)
			sm+=generate();
		return sm/n;
	}


	@Override
	public double getParamterUpperBoundConfidenceInterval(double level, //In the normal case parameter=mean
			MeanConfidenceInterval confidenceIntervalCalculator) {
		return confidenceIntervalCalculator.getUpperBoundConfidenceInterval(level);  //How do you call this Java construct?
	}

	@Override
	public double getParamterLoweerBoundConfidenceInterval(double level,
			MeanConfidenceInterval confidenceIntervalCalculator) {
		return confidenceIntervalCalculator.getLowerBoundConfidenceInterval(level);  //How do you call this Java construct?


	}

	@Override
	public double getSampleStdDeviation(int n) {

		double average = getMean();


		double sum = 0.0;
		for(int i=0; i<n; i++) {
			double simulation=generate();
			double value	= (simulation - average)*( simulation - average); // - errorOfSum;
			sum+= value;
		}
		return Math.sqrt(sum/ (double)(n-1));
	}







}

	
	
