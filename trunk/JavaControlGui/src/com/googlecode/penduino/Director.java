package com.googlecode.penduino;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.TooManyListenersException;

import com.googlecode.penduino.gui.ModelUpdater;
import com.googlecode.penduino.serial.ArduinoSerialConnection;

public enum Director {

	INSTANCE;
	
	private ArduinoSerialConnection connection = null;

	//TODO Need to handler exceptions better. For example port in use should bring up an error box
	//in the gui no just crash
	public void createNewArduinoSerialConnection(String portName){
		try {
			if(connection!= null){
				connection.close();
			}
			connection = new ArduinoSerialConnection(portName, 150);
			
			//Could hold a list of objects that get added as handlers
			//which are registered on main.
			connection.addSerialMessageHandler(new ModelUpdater());
			
			
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
	}


}
