package com.googlecode.penduino;

public interface PidTuningChangedListener {
	
	public void pGainChangedEvent(double newPGain);
	public void iGainChangedEvent(double newIGain);
	public void dGainChangedEvent(double newDGain);
}
