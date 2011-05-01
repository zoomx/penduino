package com.googlecode.penduino.serial;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/*
 * Default constructor on purpose
 */
class ArduinoSerialConnection implements SerialPortEventListener { 

	private final SerialPort serialPort;
	private static final int TIME_OUT = 2000;
	private final BufferedInputStream input;
	private final OutputStream output;
	private final byte[] bufferedBytes;
	private int totalBytesLength = 0;
	
	private final byte terminateByte;

	private Collection<ArduinoSerialMessageHandler> messageHandlers = new ArrayList<ArduinoSerialMessageHandler>();

	//TODO This needs tidying up and adding baud rate etc
	
	public ArduinoSerialConnection(String portName, int bufferSize, int dataRate, byte terminateByte)
			throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, IOException,
			TooManyListenersException {
		
		this.terminateByte = terminateByte;
		
		CommPortIdentifier portId = CommPortIdentifier
				.getPortIdentifier(portName);
		serialPort = (SerialPort) portId.open(this.getClass().getName(),
				TIME_OUT);

		serialPort.setSerialPortParams(dataRate, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

		input = new BufferedInputStream(serialPort.getInputStream());
		output = serialPort.getOutputStream();

		bufferedBytes = new byte[bufferSize];

		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
	}

	@Override
	public synchronized void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				for (int i = 0; i< input.available(); i++) {
					int b = input.read();
					
					if (b != terminateByte) {
						this.bufferedBytes[totalBytesLength++]=(byte) b;
					} else{
						
						this.dealWithAFullMessage(new String(bufferedBytes, 0, totalBytesLength, "ASCII"));
						totalBytesLength = 0;
					}
				}

			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	private void dealWithAFullMessage(String msg) {
		for (ArduinoSerialMessageHandler msgListener : messageHandlers) {
			msgListener.arduinoSerialMessageEvent(msg);
		}
	}

	public void addSerialMessageHandler(ArduinoSerialMessageHandler asml) {
		this.messageHandlers.add(asml);
	}
	
	public static List<String> getPortNamesAvailable(){
		@SuppressWarnings("rawtypes")
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		List<String> portNames = new ArrayList<String>();
		while(portEnum.hasMoreElements()){
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			portNames.add(currPortId.getName());
		}
		return portNames;
	}
	
	public void sendOverSerial(String msg){
		try {
			output.write(msg.getBytes("ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
