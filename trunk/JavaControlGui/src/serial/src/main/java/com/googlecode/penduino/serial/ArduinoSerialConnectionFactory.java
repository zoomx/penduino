package com.googlecode.penduino.serial;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TooManyListenersException;

public enum ArduinoSerialConnectionFactory {
	
	INSTANCE;

	private ArduinoSerialConnection connection = null;
	private Collection<ArduinoSerialMessageHandler> handlers = new ArrayList<ArduinoSerialMessageHandler>();
	private Collection<ArduinoSerialMessageCreator> creators = new ArrayList<ArduinoSerialMessageCreator>();
	
	
	public void openDefaultArduinoSerialConnection(String portName) throws com.googlecode.penduino.serial.exceptions.PortInUseException{
		try {
			if(connection!= null){
				connection.close();
			}
			connection = new ArduinoSerialConnection(portName, 150, 9600, (byte)10);
			
			for(ArduinoSerialMessageHandler handler : handlers){
				connection.addSerialMessageHandler(handler);
			}
			
			for(ArduinoSerialMessageCreator creator : creators){
				creator.setConnection(connection);
			}
			
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		}  catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			throw new com.googlecode.penduino.serial.exceptions.PortInUseException();
		}
	}
	
	public void registerMessageHandler(ArduinoSerialMessageHandler handler){
		if(connection != null){
			connection.addSerialMessageHandler(handler);
		}
		handlers.add(handler);
	}
	
	public void registerMessageCreator(ArduinoSerialMessageCreator creator){
		if(connection != null){
			creator.setConnection(connection);
		}
		creators.add(creator);
	}
	
	public static List<String> getPortNamesAvailable(){
		return ArduinoSerialConnection.getPortNamesAvailable();
	}
	
	public void close(){
		if(connection!= null){
			connection.close();
			connection = null;
		}
	}
	
}