package com.googlecode.penduino.core;

import com.googlecode.penduino.core.events.PidGainChangedEvent;
import com.googlecode.penduino.core.events.PitchChangedEvent;
import com.googlecode.penduino.core.model.PidGainVariable;
import com.googlecode.penduino.core.model.Robot;
import com.googlecode.penduino.serial.ArduinoSerialMessageHandler;

public class ModelUpdater extends ArduinoSerialMessageHandler{

	private static final String telemetryPattern 
		= "-??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)??";
	
	private String pGain;
	private String iGain;
	private String dGain;
	
	@Override
	public void processSerialMessage(String message) {
		if(matchesTelemtryPattern(message)){
			String[] doubles = message.split(" ");
			if(doubles.length == 4){
				Robot.INSTANCE.setPitch(new PitchChangedEvent(doubles[0]));
				if(!doubles[1].equals(pGain)){
					pGain = doubles[1];
					Robot.INSTANCE.setPidGain(new PidGainChangedEvent(PidGainVariable.P, pGain));
				} else if(!doubles[2].equals(iGain)){
					iGain = doubles[2];
					Robot.INSTANCE.setPidGain(new PidGainChangedEvent(PidGainVariable.I, iGain));
				} else if(!doubles[3].equals(dGain)){
					dGain = doubles[3];
					Robot.INSTANCE.setPidGain(new PidGainChangedEvent(PidGainVariable.D, dGain));
				}
			}
		}
	}
	
	private boolean matchesTelemtryPattern(String message){
		return message.matches(telemetryPattern);
	}
}
