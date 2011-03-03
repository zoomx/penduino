package com.googlecode.penduino.model;

import java.util.ArrayList;
import java.util.Collection;

import com.googlecode.penduino.PidTuningChangedListener;
import com.googlecode.penduino.PitchChangeListener;

public enum Robot {
	
	INSTANCE;
	
	private double pitch;
	private double pGain;
	private double iGain;
	private double dGain;
	
	private Collection<PitchChangeListener> pitchChangeListeners = 
		new ArrayList<PitchChangeListener>();
	private Collection<PidTuningChangedListener> pidTuningChangeListeners =
		new ArrayList<PidTuningChangedListener>();
	
	public void initialise(double pitch, double pGain, double iGain, double dGain){
		this.pitch = pitch;
		this.pGain = pGain;
		this.iGain = iGain;
		this.dGain = dGain;
	}
	
	public void addPitchChangeListener(PitchChangeListener pcl){
		pitchChangeListeners.add(pcl);
	}
	
	public void addPidTuningChangeListener(PidTuningChangedListener pidTCL){
		pidTuningChangeListeners.add(pidTCL);
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
		for(PitchChangeListener pcl : pitchChangeListeners){
			pcl.pitchChangeEvent(pitch);
		}
	}

	public double getpGain() {
		return pGain;
	}

	public void setpGain(double pGain) {
		this.pGain = pGain;
		PidVars.P.setValue(pGain);
		for(PidTuningChangedListener ptcl : pidTuningChangeListeners){
			ptcl.pGainChangedEvent(pGain);
		}
	}

	public double getiGain() {
		return iGain;
	}

	public void setiGain(double iGain) {
		this.iGain = iGain;
		for(PidTuningChangedListener ptcl : pidTuningChangeListeners){
			ptcl.iGainChangedEvent(iGain);
		}
	}

	public double getdGain() {
		return dGain;
	}

	public void setdGain(double dGain) {
		this.dGain = dGain;
		for(PidTuningChangedListener ptcl : pidTuningChangeListeners){
			ptcl.dGainChangedEvent(dGain);
		}
	}
	
	public void setPidVar(PidVars var){
		if(var.equals(PidVars.P)){
			this.setpGain(var.getValue());
		} else if(var.equals(PidVars.I)){
			this.setiGain(var.getValue());
		} else {
			this.setdGain(var.getValue());
		}
	}
	
	public double getPidVar(PidVars var){
		if(var.equals(PidVars.P)){
			return this.getpGain();
		} else if(var.equals(PidVars.I)){
			return this.getiGain();
		} else {
			return this.getdGain();
		}
	}
	
	//TODO some kind of update thing for getting data from the actual robot
}
