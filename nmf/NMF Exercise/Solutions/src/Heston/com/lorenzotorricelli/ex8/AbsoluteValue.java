package com.lorenzotorricelli.ex8;

import net.finmath.stochastic.RandomVariableInterface;

public class AbsoluteValue implements TruncationFunction{

	@Override
	public RandomVariableInterface evaluate(RandomVariableInterface x) {
		return x.abs();
	}

}
