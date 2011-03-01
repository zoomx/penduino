package com.googlecode.penduino.gui;

import javax.swing.JFrame;

public class GuiMain {

	public static void main(String[] args) {
		JFrame frame = createFrame();
		SerialChooserPanel scp = new SerialChooserPanel();
		frame.add("North", scp);
		PitchPanel pitch = new PitchPanel();
		frame.add("Center", pitch);
		//frame.pack();
		frame.setVisible(true);
	}
	
	
	private static JFrame createFrame(){
		JFrame frame = new JFrame();
		frame.setVisible(false);
		frame.setTitle("Penduino Telemetry");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		return frame;
	}
}
