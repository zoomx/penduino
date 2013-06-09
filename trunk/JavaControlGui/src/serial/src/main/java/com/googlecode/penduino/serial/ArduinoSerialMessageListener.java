package com.googlecode.penduino.serial;

public interface ArduinoSerialMessageListener {

	void processSerialMessage(final String messageIn);

}
