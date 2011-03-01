package com.googlecode.penduino.tests;

import com.googlecode.penduino.serial.ArduinoSerialMessageHandler;

public class ConvertToDouble extends ArduinoSerialMessageHandler{

	@Override
	public void processSerialMessage(String message) {
		double d = Double.parseDouble(message);
		System.out.println("The double recieved was " + d
				+ " which when multiplied by 2 is " + d * 2);
	}



}
