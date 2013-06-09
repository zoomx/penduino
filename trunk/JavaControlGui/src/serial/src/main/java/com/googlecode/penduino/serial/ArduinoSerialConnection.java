package com.googlecode.penduino.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.googlecode.penduino.serial.internal.ErrOutErrorHandler;
import com.googlecode.penduino.serial.internal.HandleErrorJob;
import com.googlecode.penduino.serial.internal.NotifyListenerJob;

public class ArduinoSerialConnection implements SerialPortEventListener {

	private static final String CHARSET_NAME = "ASCII";
	private static final int TIME_OUT = 2000;
	private static final int BACKGROUND_THREAD_COUNT = 8;

	private final SerialPort serialPort;
	private final BufferedInputStream input;
	private final OutputStream output;

	private final byte[] bufferedBytes;
	private final byte terminateByte;
	private int totalBytesLength = 0;

	private final Collection<ArduinoSerialMessageListener> messageListeners = new ArrayList<ArduinoSerialMessageListener>();
	private final ArduinoSerialErrorHandler errorHandler;

	private final Lock serialReadLock = new ReentrantLock();

	private final ExecutorService executor = Executors
			.newFixedThreadPool(BACKGROUND_THREAD_COUNT);

	private ArduinoSerialConnection(final CommPortIdentifier commPort,
			final int bufferSize, final int dataRate, final byte terminateByte,
			final ArduinoSerialErrorHandler errorHandler)
			throws PortInUseException, UnsupportedCommOperationException,
			IOException, TooManyListenersException {
		this.errorHandler = errorHandler;
		this.terminateByte = terminateByte;

		serialPort = (SerialPort) commPort.open(this.getClass().getName(),
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
	public void serialEvent(final SerialPortEvent event) {
		this.serialReadLock.lock();
		try {
			attemptProcessSerialEvent(event);
		} catch (final IOException e) {
			handleSerialProcessingError(e);
		} finally {
			this.serialReadLock.unlock();
		}
	}

	private void attemptProcessSerialEvent(final SerialPortEvent event)
			throws IOException {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			for (int i = 0; i < input.available(); i++) {
				int b;
				b = input.read();
				if (b != terminateByte) {
					this.bufferedBytes[totalBytesLength++] = (byte) b;
				} else {
					this.notifyListeners(new String(bufferedBytes, 0,
							totalBytesLength, CHARSET_NAME));
					totalBytesLength = 0;
				}
			}
		}
	}

	/*
	 * TODO how would create a handler to stop processing message and would that
	 * work if it error-ed during a shut down. Might be better to move back the
	 * previous by passing in the error handler once the connection has been
	 * create. Still nto entirely sure about it though
	 */
	private void handleSerialProcessingError(final Exception e) {
		executor.execute(new HandleErrorJob(errorHandler, e));
	}

	private void notifyListeners(final String msg) {
		for (final ArduinoSerialMessageListener listener : messageListeners) {
			executor.execute(new NotifyListenerJob(listener, msg));
		}
	}

	public void addSerialMessageListener(
			final ArduinoSerialMessageListener listener) {
		this.messageListeners.add(listener);
	}

	public void sendOverSerial(final String msg) throws IOException {
		output.write(msg.getBytes(CHARSET_NAME));
	}

	/*
	 * From the Arduino playground
	 * 
	 * The serial port's close method should be called from a method that is
	 * synchronized with the serialEvent method, which will prevent any
	 * exceptions from appearing during program shutdown.
	 */
	public void close() {
		this.serialReadLock.lock();
		try {
			if (serialPort != null) {
				serialPort.removeEventListener();
			}
			if (input != null) {
				input.close();
			}
		} catch (final IOException e) {
			handleSerialProcessingError(e);
		} finally {
			this.serialReadLock.unlock();
		}
		executor.shutdownNow();
	}

	public static List<CommPortIdentifier> getPortNamesAvailable() {
		final Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		final List<CommPortIdentifier> ports = new ArrayList<CommPortIdentifier>();
		while (portEnum.hasMoreElements()) {
			ports.add((CommPortIdentifier) portEnum.nextElement());
		}
		return ports;
	}

	public class Builder {
		private final CommPortIdentifier commPort;
		private int bufferSize = 150;
		private int baudRate = 9600;
		private byte terminatingByte = (byte) 10;
		private ArduinoSerialErrorHandler errorHandler = new ErrOutErrorHandler();

		public ArduinoSerialConnection build() throws PortInUseException,
				UnsupportedCommOperationException, IOException,
				TooManyListenersException {
			return new ArduinoSerialConnection(commPort, bufferSize, baudRate,
					terminatingByte, errorHandler);
		}

		public Builder(final CommPortIdentifier commPort) {
			this.commPort = commPort;
		}

		public Builder baudRate(final int baudRate) {
			this.baudRate = baudRate;
			return this;
		}

		public Builder baudRate(final BaudRate baudRate) {
			this.baudRate = baudRate.getIntValue();
			return this;
		}

		public Builder bufferSize(final int bufferSize) {
			this.bufferSize = bufferSize;
			return this;
		}

		public Builder terminatingByte(final byte terminatingByte) {
			this.terminatingByte = terminatingByte;
			return this;
		}

		public Builder errorHandler(final ArduinoSerialErrorHandler errorHandler) {
			this.errorHandler = errorHandler;
			return this;
		}

	}
}
