package com.timlummer.ex7;

import net.finmath.time.TimeDiscretization;

public class ex7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	double startTime = 0.0;
	double DeltaT = 0.01;
	double EndTime = 1.0;
	
	int Paths = 10000;
	
	double IniVal = 1.0;
	
	double my = 0.1;
	double sigma = 0.3;
	
	EulerMaruyamBlackScholes EMBS;
	
	int TimeSteps =100;
	
	TimeDiscretization Time = new TimeDiscretization(startTime, TimeSteps,DeltaT);
	
	System.out.println(Time.getNumberOfTimes());
	
	EMBS = new EulerMaruyamBlackScholes(Time, my, sigma, IniVal, Paths, 1);
	
	EMBS.generate();
	
	System.out.println("Analytical value of E[X_T]:=" + IniVal*Math.exp(my*EndTime));
	System.out.println("Analytical value of Var[X_T]:=" + IniVal*IniVal*Math.exp(2*my*EndTime)*(Math.exp(sigma*sigma*EndTime)-1));
	System.out.println("Analytical value of E[X^Delta_n]:=" + IniVal*Math.pow(1+my*DeltaT , Paths));
	System.out.println("Analytical value of Var[X^Delta_n]:=" + (IniVal*IniVal*Math.pow((1+my*DeltaT)*(1+my*DeltaT)+sigma*sigma*DeltaT,Paths)
			-  IniVal*IniVal*Math.pow((1+my*DeltaT)*(1+my*DeltaT) , Paths) )  );
	
	System.out.println("MC value of E[X_T]:=" + EMBS.GetAverage());
	System.out.println("MC value of Var[X_T]:=" + EMBS.GetVariance());
	System.out.println("MC value of E[X^Delta_n]:=" + IniVal*Math.pow(1+my*DeltaT , Paths));
	System.out.println("MC value of Var[X^Delta_n]:=" + (IniVal*IniVal*Math.pow((1+my*DeltaT)*(1+my*DeltaT)+sigma*sigma*DeltaT,Paths)
			-  IniVal*IniVal*Math.pow((1+my*DeltaT)*(1+my*DeltaT) , Paths) )  );
		
	}

}
