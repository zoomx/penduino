package com.googlecode.penduino.core.model;

public enum PidGainVariable {

	P("Proportional"),I("Integral"),D("Derivative");
	
	private final String name;
	
	private PidGainVariable(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
