package com.googlecode.penduino.serial.internal;

import com.googlecode.penduino.serial.ArduinoSerialErrorHandler;

public class ErrOutErrorHandler implements ArduinoSerialErrorHandler {

	@Override
	public void handleError(final Exception e) {
		e.printStackTrace();
	}

}
