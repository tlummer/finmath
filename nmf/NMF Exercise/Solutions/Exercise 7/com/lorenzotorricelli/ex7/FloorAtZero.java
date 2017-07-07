package com.lorenzotorricelli.ex7;




import net.finmath.stochastic.RandomVariableInterface;

public class FloorAtZero implements TruncationFunction{

	@Override
	public RandomVariableInterface evaluate(RandomVariableInterface x) {
		// TODO Auto-generated method stub
		return x.floor(0.0);
	}

}
