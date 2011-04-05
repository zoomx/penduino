package com.googlecode.penduino.serial;

public abstract class ArduinoSerialMessageCreator {

	private ArduinoSerialConnection connection;
	
	
	public final void setConnection(ArduinoSerialConnection connection){
		this.connection = connection;
	}
	
	public final void sendSerialMessage(String message){
		if(connection != null){
			connection.sendOverSerial(message);
		}
	}
}
