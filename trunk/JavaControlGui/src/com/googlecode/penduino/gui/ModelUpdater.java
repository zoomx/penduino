package com.googlecode.penduino.gui;

import com.googlecode.penduino.model.Robot;
import com.googlecode.penduino.serial.ArduinoSerialMessageHandler;

public class ModelUpdater extends ArduinoSerialMessageHandler {

	private static final String telemetryPattern 
		= "-??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)?? -??(\\d+?)(.\\d+?)??";
	private static final String doubleMatchPattern = "-??(\\d+?)(.\\d+?)??";
	
	@Override
	public void processSerialMessage(String message) {
		if (this.isDoubleParsable(message)) {
			Robot.INSTANCE.setPitch(Double.parseDouble(message));
		}
	}

	private boolean isDoubleParsable(String message) {
		return message.matches(doubleMatchPattern);
	}

	
	/*
	 * TODO: When I take in a telemetry matching pattern split into 4 strings
	 * compare the pid string to ones stored here. Any difference and i should
	 * tell the model to update and save that string here as the new string representation
	 * 
	 */
	
}
