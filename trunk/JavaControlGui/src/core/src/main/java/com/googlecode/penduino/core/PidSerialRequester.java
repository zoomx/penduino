package com.googlecode.penduino.core;

import com.googlecode.penduino.core.interfaces.PidGainController;
import com.googlecode.penduino.core.model.PidChangeValue;
import com.googlecode.penduino.core.model.PidGainVariable;
import com.googlecode.penduino.serial.ArduinoSerialMessageCreator;

public class PidSerialRequester extends ArduinoSerialMessageCreator implements
		PidGainController {

	public void changePidGain(PidGainVariable pgv, PidChangeValue pcv) {
		this.sendSerialMessage(pgv.toString()+pcv.getSerialRepresentation());
	}

}
