package com.googlecode.penduino.serial.internal;

import com.googlecode.penduino.serial.ArduinoSerialMessageListener;

public class NotifyListenerJob implements Runnable {

	private final ArduinoSerialMessageListener listener;
	private final String message;

	public NotifyListenerJob(final ArduinoSerialMessageListener listener,
			final String message) {
		this.listener = listener;
		this.message = message;
	}

	@Override
	public void run() {
		listener.processSerialMessage(message);
	}

}
