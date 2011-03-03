package com.googlecode.penduino.model;

public enum PidVars {

	P("Proportional"),I("Integral"),D("Derivative");
	
	private double value;
	private final String name;
	
	PidVars(String name){
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	
}
