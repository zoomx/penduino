package com.googlecode.penduino.core.interfaces;

import com.googlecode.penduino.core.events.PidGainChangedEvent;

public interface PidTuningListener {

	public void onPidGainChangeEvent(PidGainChangedEvent event);
	
}
