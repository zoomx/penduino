package com.googlecode.penduino.core.interfaces;

import com.googlecode.penduino.core.events.PitchChangedEvent;

public interface PitchChangeListener {
	
	public void onPitchChangeEvent(PitchChangedEvent event);
}
