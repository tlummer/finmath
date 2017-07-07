package com.lorenzotorricelli.ex3sol;

import java.util.ArrayList;

public class LCG {

	private ArrayList<Long> randomNumbers=new ArrayList<Long>();

	private Long seed;
	private long modulus=(long) Math.pow(2, 48);
	private long a=25214903917L;
	private long c=11L;

	LCG(long seed){   
		this.seed=seed;
	}

	void generate(int numberOfIntegers) throws Exception  {  //Instnce call Implementation
		if(seed==null){
			throw new NullPointerException();
		}
		else{
			randomNumbers.add(seed);
			for(int indexOfInteger=0; indexOfInteger<numberOfIntegers; indexOfInteger++){
				Long congruence=( (a*randomNumbers.get(indexOfInteger)+c)% modulus);  
				randomNumbers.add(congruence);  //autoboxing
			} 
		}	
	}

	static LCG staticGenerate(int numberOfIntegers, long seed) {  //Impementation using a factory method
		LCG linearCongruentialGenerator= new LCG(seed);
		try {
			linearCongruentialGenerator.generate(numberOfIntegers);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return linearCongruentialGenerator;
	}

	ArrayList<Long> getRandomNumberSequence(){
		return randomNumbers;
	}
}
