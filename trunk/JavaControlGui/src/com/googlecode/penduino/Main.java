package com.googlecode.penduino;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.TooManyListenersException;

import com.googlecode.penduino.serial.ArduinoSerialConnection;
import com.googlecode.penduino.tests.ConvertToDouble;
import com.googlecode.penduino.tests.DummySerialMessageListener;
import com.googlecode.penduino.tests.SerialTest;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//SerialTest main = new SerialTest();
		//main.initialize();
		//System.out.println("Started");
		
		
		try {
			ArduinoSerialConnection connection = new ArduinoSerialConnection("COM4", 150);
			//DummySerialMessageListener dummy = new DummySerialMessageListener();
			ConvertToDouble dummy = new ConvertToDouble();
			connection.addSerialMessageHandler(dummy);
			System.out.println("Started Arduino Serial Connection");
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
