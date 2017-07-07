package com.lorenzotorricelli.ex8;

import net.finmath.stochastic.RandomVariableInterface;

public interface EulerSimulatorInterface {

	void generateEuler();

	double getDrift();

	double getSpotPrice();
    
	RandomVariableInterface getFinalPrice();
	
}
