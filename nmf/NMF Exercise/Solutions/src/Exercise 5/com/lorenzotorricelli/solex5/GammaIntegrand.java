package com.lorenzotorricelli.solex5;

public class GammaIntegrand implements Function<Double> {
  private double alpha;
	GammaIntegrand(double alpha){this.alpha=alpha;
	}
  public Double evaluate(Double x){
     return Math.exp(-x)*Math.pow(x, alpha-1.0);
  }
}
