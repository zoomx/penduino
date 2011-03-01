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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
