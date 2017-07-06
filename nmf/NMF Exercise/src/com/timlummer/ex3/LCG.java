package com.timlummer.ex3;

import java.util.ArrayList;

public class LCG {

	private long m=(long) Math.pow(2, 48);
	private long a=25214903917L;
	private long c=11L;
	
	private ArrayList<Long> randomNumbers=new ArrayList<Long>();	
	
	
	LCG (long seed){
		randomNumbers.add(seed);
	}
	
	private void generate(int numbers){	
		for(int indexOfInteger=0; indexOfInteger<numbers; indexOfInteger++){
			long newNumber=( (a*randomNumbers.get(indexOfInteger)+c)% m);  
			randomNumbers.add(newNumber);		
		}
	}
	
	public  ArrayList<Long> RandomNumber(int numbers){
		this.generate(numbers);
		return randomNumbers;
	}
	
}
