package com.googlecode.penduino.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.penduino.core.ModelUpdater;
import com.googlecode.penduino.core.PidSerialRequester;
import com.googlecode.penduino.core.events.PidGainChangedEvent;
import com.googlecode.penduino.core.events.PitchChangedEvent;
import com.googlecode.penduino.core.interfaces.PidGainController;
import com.googlecode.penduino.core.interfaces.PidTuningListener;
import com.googlecode.penduino.core.interfaces.PitchChangeListener;
import com.googlecode.penduino.serial.ArduinoSerialConnectionFactory;
import com.googlecode.penduino.serial.exceptions.PortInUseException;

public enum Robot {

	INSTANCE;
	
	private Robot(){
		PidSerialRequester req = new PidSerialRequester();
		gainController = req;
		ArduinoSerialConnectionFactory.INSTANCE.registerMessageCreator(req);
		ArduinoSerialConnectionFactory.INSTANCE.registerMessageHandler(new ModelUpdater());	
	}
	
	
	private PidGainController gainController;
	private Collection<PitchChangeListener> pitchListeners = new ArrayList<PitchChangeListener>();
	private Collection<PidTuningListener> pidListeners = new ArrayList<PidTuningListener>();
	
	public void requestPidGainChange(PidGainVariable pgv, PidChangeValue pcv){
			gainController.changePidGain(pgv, pcv);
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
	
	public void registerPitchChangeListener(PitchChangeListener listener){
		pitchListeners.add(listener);
	}

	public void registerPidTuningListener(PidTuningListener listener){
		pidListeners.add(listener);
	}
	
	public static List<String> getAvailibleConnectionsList(){
		return ArduinoSerialConnectionFactory.getPortNamesAvailable();
	}
	
	public void startRobotConnection(String connectionName) throws ConnectionInUseException{
		try {
			ArduinoSerialConnectionFactory.INSTANCE.openDefaultArduinoSerialConnection(connectionName);
		} catch (PortInUseException e) {
			throw new ConnectionInUseException();
		}
	}
	
	public void closeRobotConnection(){
		ArduinoSerialConnectionFactory.INSTANCE.close();
	}
}
