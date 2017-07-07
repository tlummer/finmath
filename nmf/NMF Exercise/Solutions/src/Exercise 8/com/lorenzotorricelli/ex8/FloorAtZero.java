package com.lorenzotorricelli.ex8;




import net.finmath.stochastic.RandomVariableInterface;

public class FloorAtZero implements TruncationFunction{

	@Override
	public RandomVariableInterface evaluate(RandomVariableInterface x) {
		return x.floor(0.0);
	}

}
