package com.googlecode.penduino.core.model;

import java.util.ArrayList;
import java.util.Collection;

import com.googlecode.penduino.core.events.PidGainChangedEvent;
import com.googlecode.penduino.core.events.PitchChangedEvent;
import com.googlecode.penduino.core.interfaces.PidGainController;
import com.googlecode.penduino.core.interfaces.PidTuningListener;
import com.googlecode.penduino.core.interfaces.PitchChangeListener;

public enum Robot {

	INSTANCE;
	
	
	private Collection<PidGainController> gainControllers = new ArrayList<PidGainController>();
	private Collection<PitchChangeListener> pitchListeners = new ArrayList<PitchChangeListener>();
	private Collection<PidTuningListener> pidListeners = new ArrayList<PidTuningListener>();
	
	public void requestPidGainChange(PidGainVariable pgv, PidChangeValue pcv){
		for(PidGainController controller : gainControllers){
			controller.changePidGain(pgv, pcv);
		}
	}
	
	public void setPitch(PitchChangedEvent pitch){
		for(PitchChangeListener listener : pitchListeners){
			listener.onPitchChangeEvent(pitch);
		}
	}
	
	public void setPidGain(PidGainChangedEvent event){
		for(PidTuningListener listener : pidListeners){
			listener.onPidGainChangeEvent(event);
		}
	}
	
	public void registerPidGainController(PidGainController controller){
		gainControllers.add(controller);
	}
	
	public void registerPitchChangeListener(PitchChangeListener listener){
		pitchListeners.add(listener);
	}

	public void registerPidTuningListener(PidTuningListener listener){
		pidListeners.add(listener);
	}
}
