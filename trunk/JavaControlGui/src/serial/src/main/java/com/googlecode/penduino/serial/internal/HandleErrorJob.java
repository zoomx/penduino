package com.googlecode.penduino.serial.internal;

import com.googlecode.penduino.serial.ArduinoSerialErrorHandler;

public class HandleErrorJob implements Runnable {

	private final ArduinoSerialErrorHandler handler;
	private final Exception e;

	public HandleErrorJob(final ArduinoSerialErrorHandler handler,
			final Exception e) {
		this.handler = handler;
		this.e = e;
	}

	@Override
	public void run() {
		handler.handleError(e);
	}

}
