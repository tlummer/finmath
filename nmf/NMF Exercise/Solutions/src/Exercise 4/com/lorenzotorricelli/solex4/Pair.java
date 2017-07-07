package com.lorenzotorricelli.solex4;

public class Pair<T,S> {
	T firstValue;
	S  secondValue;
	public T getFirstValue() {
		return firstValue;
	}

	public Pair(T firstValue, S secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public S getSecondValue() {
		return secondValue;
	}



}
