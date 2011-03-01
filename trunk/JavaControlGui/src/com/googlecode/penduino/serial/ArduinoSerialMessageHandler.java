package com.googlecode.penduino.serial;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ArduinoSerialMessageHandler implements Runnable{


	private Queue<String> messages = new ConcurrentLinkedQueue<String>();
	private Thread t;

	public ArduinoSerialMessageHandler() {
		t = new Thread(this);
		t.start();
	}

	public final void arduinoSerialMessageEvent(String messageIn) {
		messages.add(messageIn);
	}

	@Override
	public void run() {
		while (true) {
			if (!messages.isEmpty()) {
				processSerialMessage(messages.remove());
			} else {
				Thread.yield();
			}
		}
	}
	
	public abstract void processSerialMessage(String message);
}
