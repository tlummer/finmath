package com.lorenzotorricelli.ex9sol;

import java.util.Map;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.model.AbstractModel;
import net.finmath.montecarlo.model.AbstractModelInterface;
import net.finmath.stochastic.RandomVariableInterface;

public class DisplacedNormalModel extends AbstractModel{
	private final double initialValue;
	private final double riskFreeRate;		// Actually the same as the drift (which is not stochastic)
	private final double a;
	private final double b;
	
	/*
	 * The interface definition requires that we provide the initial value, the drift and the volatility in terms of random variables.
	 * We construct the corresponding random variables here and will return (immutable) references to them.
	 */
	private RandomVariableInterface[]	initialValueVector	= new RandomVariableInterface[1];
	private RandomVariableInterface		drift;
	private RandomVariableInterface		volatilityOnPaths;

	/**
	 * Create a Monte-Carlo simulation using given time discretization.
	 * 
	 * @param initialValue Spot value.
	 * @param riskFreeRate The risk free rate.
	 * @param volatility The log volatility.
	 */
	public DisplacedNormalModel(
			double initialValue,
			double riskFreeRate,
			double a,
			double b) {
		super();

		this.initialValue	= initialValue;
		this.riskFreeRate	= riskFreeRate;
		this.a	=a;
		this.b=b;
	}

	@Override
	public RandomVariableInterface[] getInitialState() {
		// The initial value and the drift are not Log-transformed! 
		if(initialValueVector[0] == null) 	initialValueVector[0] = getRandomVariableForConstant(initialValue);
		
		return initialValueVector;
	}

	@Override
	public RandomVariableInterface[] getDrift(int timeIndex, RandomVariableInterface[] realizationAtTimeIndex, RandomVariableInterface[] realizationPredictor) {
	RandomVariableInterface[] drift = new RandomVariableInterface[realizationAtTimeIndex.length];
	for(int componentIndex = 0; componentIndex<realizationAtTimeIndex.length; componentIndex++) {
	drift[componentIndex] = realizationAtTimeIndex[componentIndex].mult(riskFreeRate);
	}
	return drift;
	}

	@Override
	public RandomVariableInterface[] getFactorLoading(int timeIndex, int component, RandomVariableInterface[] realizationAtTimeIndex) {   //These methods have been modified from the BS model, since we no longer
		//can pass a constant time and space independent volatility and drift. This is because of the mixed-normal log-normal structure in the diffusion coefficient
	RandomVariableInterface[] volatilityOnPaths = new RandomVariableInterface[realizationAtTimeIndex.length];
	for(int componentIndex = 0; componentIndex<realizationAtTimeIndex.length; componentIndex++) {
		volatilityOnPaths[componentIndex] = realizationAtTimeIndex[componentIndex].mult(a).add(b);
		}
		return volatilityOnPaths;
		}

	@Override
	public RandomVariableInterface applyStateSpaceTransform(int componentIndex, RandomVariableInterface randomVariable) {
	return randomVariable;  //now we return the identity;  we are this time modeling directly the process, not the log-process like in the lognormal model
	}
	
	@Override
	public RandomVariableInterface getNumeraire(double time) {
		double numeraireValue = Math.exp(riskFreeRate * time);

		return getRandomVariableForConstant(numeraireValue);
	}

	@Override
	public int getNumberOfComponents() {
		return 1;
	}

	public RandomVariableInterface getRandomVariableForConstant(double value) {
		return getProcess().getBrownianMotion().getRandomVariableForConstant(value);
	}

	@Override
	public String toString() {
		return super.toString() + "\n" +
				"BlackScholesModel:\n" +
				"  initial value...:" + initialValue + "\n" +
				"  risk free rate..:" + riskFreeRate + "\n" +
				"  a.......:" + a +
		        "  b.......:" + b;
		
	}

	/**
	 * Returns the risk free rate parameter of this model.
	 *
	 * @return Returns the riskFreeRate.
	 */
	public double getRiskFreeRate() {
		return riskFreeRate;
	}

	/**
	 * Returns the volatility parameter of this model.
	 * 
	 * @return Returns the volatility.
	 */
	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	@Override
	public AbstractModelInterface getCloneWithModifiedData(Map<String, Object> dataModified)
			throws CalculationException {
		// TODO Auto-generated method stub
		return null;
	}
}
