package com.googlecode.penduino.core.model;

import java.util.ArrayList;
import java.util.Collection;

public enum PidChangeValue {

	MINUS_ONE('-',"-1"), MINUS_ONE_TENTH('<',"-0.1"), MINUS_ONE_HUNDRETH('{',"-0.01"),
	PLUS_ONE('+',"+1"), PLUS_ONE_TENTH('>',"+0.1"), PLUS_ONE_HUNDRETH('}',"+0.01"),
	RESET('R',"Reset");
	
	private final char serialRepresentation;
	private final String name;
	
	private static Collection<PidChangeValue> minusVals = new ArrayList<PidChangeValue>();
	private static Collection<PidChangeValue> plusVals = new ArrayList<PidChangeValue>();
	private static Collection<PidChangeValue> specialVals = new ArrayList<PidChangeValue>();
	
	static{
		minusVals.add(MINUS_ONE);
		minusVals.add(MINUS_ONE_TENTH);
		minusVals.add(MINUS_ONE_HUNDRETH);
		
		plusVals.add(PLUS_ONE);
		plusVals.add(PLUS_ONE_TENTH);
		plusVals.add(PLUS_ONE_HUNDRETH);
		
		specialVals.add(RESET);
	}
	
	
	private PidChangeValue(char serialRepresentation, String name) {
		this.name = name;
		this.serialRepresentation = serialRepresentation;
	}
	
	public char getSerialRepresentation(){
		return serialRepresentation;
	}
	
	public String getName(){
		return name;
	}
	
	public static Collection<PidChangeValue> getMinusValues(){
		return minusVals;
	}
	
	public static Collection<PidChangeValue> getPlusValues(){
		return plusVals;
	}
	
	public static Collection<PidChangeValue> getSpecialValues(){
		return specialVals;
	}
}
