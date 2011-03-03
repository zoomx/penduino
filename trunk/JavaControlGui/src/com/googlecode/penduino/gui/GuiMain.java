package com.googlecode.penduino.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class GuiMain {

	public static void main(String[] args) {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(400, 400);
		
		//TODO: set an on close action to close serial connection!!!
		return frame;
	}
}
