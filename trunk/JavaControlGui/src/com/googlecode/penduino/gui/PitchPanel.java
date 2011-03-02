package com.googlecode.penduino.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.googlecode.penduino.PitchChangeListener;
import com.googlecode.penduino.model.Robot;

@SuppressWarnings("serial")
public class PitchPanel extends JPanel implements PitchChangeListener{
	
	JLabel pitch = new JLabel("No data yet");
	PitchRevCounter rev = new PitchRevCounter();
	
	public PitchPanel(){
		Robot.INSTANCE.addPitchChangeListener(this);
		this.add(pitch);
		pitch.setAlignmentX(CENTER_ALIGNMENT);
		this.add(rev);
		rev.setPreferredSize(new Dimension(300,150));
		}
	
	@Override
	public void pitchChangeEvent(double newPitch) {
		pitch.setText("Pitch is " + newPitch);
		rev.setPitchDegrees(newPitch);
		pitch.repaint();
		rev.repaint();
	}

	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

}
