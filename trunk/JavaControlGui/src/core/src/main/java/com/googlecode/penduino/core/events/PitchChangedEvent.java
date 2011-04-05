package com.googlecode.penduino.core.events;

public class PitchChangedEvent {

	private final String s;
	private final double d;
	
	public PitchChangedEvent(String s){
		this.s = s;
		d = Double.parseDouble(s);
	}
	
	public String getPitchAsString(){
		return s;
	}
	
	public double getPitchAsDouble(){
		return d;
	}
}
