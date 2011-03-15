package com.googlecode.penduino.gui;

import com.googlecode.penduino.PidTuningChangedListener;
import com.googlecode.penduino.model.PidVars;
import com.googlecode.penduino.model.Robot;
import com.googlecode.penduino.serial.ArduinoSerialMessageHandler;

public class ModelUpdater extends ArduinoSerialMessageHandler  implements PidTuningChangedListener{

	private static final String telemetryPattern 
		= "-??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)??";
	private static final String doubleMatchPattern = "-??(\\d+?)(.\\d+?)??";
	
	private String pGain;
	private String iGain;
	private String dGain;
	
	@Override
	public void processSerialMessage(String message) {
		if (this.isDoubleParsable(message)) {
			Robot.INSTANCE.setPitch(Double.parseDouble(message));
		} else if(matchesTelemtryPattern(message)){
			String[] doubles = message.split(" ");
			if(doubles.length == 4){
				Robot.INSTANCE.setPitch(Double.parseDouble(doubles[0]));
				if(!doubles[1].equals(pGain)){
					PidVars.P.setValue(Double.parseDouble(doubles[1]));
					Robot.INSTANCE.updatePidVar(PidVars.P);
					pGain = doubles[1];
				} else if(!doubles[2].equals(iGain)){
					PidVars.I.setValue(Double.parseDouble(doubles[2]));
					Robot.INSTANCE.updatePidVar(PidVars.I);
					iGain = doubles[2];
				} else if(!doubles[3].equals(dGain)){
					PidVars.D.setValue(Double.parseDouble(doubles[3]));
					Robot.INSTANCE.updatePidVar(PidVars.D);
					dGain = doubles[3];
				}
			}
		}
	}

	private boolean isDoubleParsable(String message) {
		return message.matches(doubleMatchPattern);
	}
	
	private boolean matchesTelemtryPattern(String message){
		return message.matches(telemetryPattern);
	}

	@Override
	public void pGainChangedEvent(double changeBy) {
		
	}

	@Override
	public void iGainChangedEvent(double changeBy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dGainChangedEvent(double changeBy) {
		// TODO Auto-generated method stub
		
	}
	
}
