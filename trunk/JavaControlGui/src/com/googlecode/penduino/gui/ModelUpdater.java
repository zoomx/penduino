package com.googlecode.penduino.gui;

import com.googlecode.penduino.model.Robot;
import com.googlecode.penduino.serial.ArduinoSerialMessageHandler;

public class ModelUpdater extends ArduinoSerialMessageHandler {

	@Override
	public void processSerialMessage(String message) {
		if (this.isDoubleParsable(message)) {
			Robot.INSTANCE.setPitch(Double.parseDouble(message));
		}
	}

	private boolean isDoubleParsable(String message) {
		return message.matches("-??(\\d+?)(.\\d+?)??");
	}

}
