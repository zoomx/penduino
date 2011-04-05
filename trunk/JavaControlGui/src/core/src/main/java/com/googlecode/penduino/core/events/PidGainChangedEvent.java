package com.googlecode.penduino.core.events;

import com.googlecode.penduino.core.model.PidGainVariable;

public class PidGainChangedEvent {

	private final String s;
	private final double d;
	private final PidGainVariable p;
	
	public PidGainChangedEvent(PidGainVariable p, String s){
		this.s = s;
		d = Double.parseDouble(s);
		this.p = p;
	}
	
	public PidGainVariable getPidGainVariable(){
		return p;
	}
	
	public String getValueAsString(){
		return s;
	}
	
	public double getValueAsDouble(){
		return d;
	}
	
}
