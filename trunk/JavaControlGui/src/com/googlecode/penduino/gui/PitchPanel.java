package com.googlecode.penduino.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.googlecode.penduino.PitchChangeListener;
import com.googlecode.penduino.model.Robot;

@SuppressWarnings("serial")
public class PitchPanel extends JPanel implements PitchChangeListener{
	
	JLabel pitch = new JLabel("No data yet");
	
	double pitchVal = 0;
	
	int height = 300;
	int width = 400;
	int lineLength = height-50;
	
	int revCentreX = width/2;
	int revCentreY = height;

	public PitchPanel(){
		Robot.INSTANCE.addPitchChangeListener(this);
		this.setSize(height, width);
		this.add(pitch);
	}
	
	@Override
	public void pitchChangeEvent(double newPitch) {
		pitch.setText("Pitch is " + newPitch);
		pitchVal = angleToRadians(newPitch);
		this.repaint();
	}
	
	private double angleToRadians(double angle){
		return angle * (Math.PI/180);
	}
	
	@Override

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.clearRect(0, height-lineLength, width, height);
		g.setColor(Color.RED);
		g.drawLine(revCentreX, revCentreX, revCentreX+calcXPoint(pitchVal), revCentreY-calcYPoint(pitchVal));
	}
	
	private int calcXPoint(double pitch){
		double d = Math.sin(pitch)*lineLength;
		return(int)d;
	}
	
	private int calcYPoint(double pitch){
		double d = Math.cos(pitch)*lineLength;
		return (int)d;
	}

}
