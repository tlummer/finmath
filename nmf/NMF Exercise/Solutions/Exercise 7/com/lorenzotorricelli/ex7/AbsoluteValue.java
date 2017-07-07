package com.lorenzotorricelli.ex7;

import net.finmath.stochastic.RandomVariableInterface;

public class AbsoluteValue implements TruncationFunction{

	@Override
	public RandomVariableInterface evaluate(RandomVariableInterface x) {
		// TODO Auto-generated method stub
		return x.abs();
	}

}
