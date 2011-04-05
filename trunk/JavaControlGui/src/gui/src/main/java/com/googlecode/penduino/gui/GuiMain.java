package com.googlecode.penduino.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.googlecode.penduino.core.ModelUpdater;
import com.googlecode.penduino.core.PidSerialRequester;
import com.googlecode.penduino.core.model.Robot;
import com.googlecode.penduino.gui.pid.PidControlPanel;
import com.googlecode.penduino.gui.pitch.PitchPanel;
import com.googlecode.penduino.gui.serial.SerialChooserPanel;
import com.googlecode.penduino.serial.ArduinoSerialConnectionFactory;

public class GuiMain {

	public static void main(String[] args) {
		
		initialise();
		
		JFrame frame = createFrame();
		
		SerialChooserPanel scp = new SerialChooserPanel();
		frame.add("North", scp);
		PitchPanel pitch = new PitchPanel();
		pitch.setLayout(new BoxLayout(pitch, BoxLayout.PAGE_AXIS));
		frame.add("Center", pitch);
		PidControlPanel pid = new PidControlPanel();
		frame.add("South", pid);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	private static JFrame createFrame(){
		JFrame frame = new JFrame();
		frame.setVisible(false);
		frame.setTitle("Penduino Telemetry");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				ArduinoSerialConnectionFactory.INSTANCE.close();
				System.exit(0);
			}
		});
		
		//TODO: set an on close action to close serial connection!!!
		
		return frame;
	}
	
	private static void initialise(){
		PidSerialRequester req = new PidSerialRequester();
		Robot.INSTANCE.registerPidGainController(req);
		ArduinoSerialConnectionFactory.INSTANCE.registerMessageCreator(req);
		ArduinoSerialConnectionFactory.INSTANCE.registerMessageHandler(new ModelUpdater());
	}
}
