package com.googlecode.penduino.tests;

import com.googlecode.penduino.serial.ArduinoSerialMessageHandler;

public class DummySerialMessageListener extends ArduinoSerialMessageHandler {


	@Override
	public void processSerialMessage(String message) {
		System.out.print("The Dummy says you received the following: ");
		System.out.println(message);
	}

}
