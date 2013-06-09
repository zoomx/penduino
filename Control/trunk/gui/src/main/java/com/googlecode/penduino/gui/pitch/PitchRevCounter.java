package com.googlecode.penduino.gui.pitch;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class PitchRevCounter extends JComponent {

	private double pitchRadians = 0;
	private int height = 0;
	private int width = 0;
	private int revCentreX;
	private int revCentreY;
	private int lineEndX;
	private int lineEndY;
	private int lineLength;
	private static final int intervalLength = 10;
	
	
	public PitchRevCounter(){
		super();
	}
	
	public void setPitchDegrees(double pitch){
		this.pitchRadians = angleToRadians(pitch);
	}
	
	private double angleToRadians(double angle){
		return angle * (Math.PI/180);
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setUpVars();
		drawRevBackground(g);
		drawRevPin(g);
		g.setColor(Color.BLACK);
		g.drawLine(revCentreX-lineLength,revCentreY,revCentreX+lineLength, revCentreY);
	}
	
	private void drawRevBackground(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(revCentreX-lineLength, 25, lineLength*2, lineLength*2);
		g.setColor(Color.BLACK);
		
		g.drawLine(revCentreX, revCentreY, revCentreX, revCentreY-lineLength);
		
		drawRevIncrement(g, 15);
		drawRevIncrement(g, 30);
		drawRevIncrement(g, 45);
		drawRevIncrement(g, 60);
		drawRevIncrement(g, 75);
		
		g.drawOval(revCentreX-lineLength, 25, lineLength*2, lineLength*2);
		g.drawLine(revCentreX-lineLength,revCentreY,revCentreX+lineLength, revCentreY);
		
		g.setColor(Color.WHITE);
		
		g.fillOval(revCentreX-lineLength+intervalLength, 25+intervalLength, 
				(lineLength-intervalLength)*2, lineLength*2-intervalLength);
		
		g.clearRect(revCentreX-lineLength-1, revCentreY+1, (lineLength+1)*2, lineLength);
		
	}
	
	private void drawRevIncrement(Graphics g, double angle){
		double tenDegrees = angleToRadians(angle);
		int angleX10 = (int)calcXPoint(tenDegrees);
		int angleY10 = (int)calcYPoint(tenDegrees);
		g.drawLine(revCentreX, revCentreY, revCentreX+angleX10, revCentreY-angleY10);
		g.drawLine(revCentreX, revCentreY, revCentreX-angleX10, revCentreY-angleY10);
	}
	
	private void drawRevPin(Graphics g){
		g.setColor(Color.RED);
		g.drawLine(revCentreX, revCentreY, lineEndX, lineEndY);
		g.drawLine(revCentreX-1, revCentreY, lineEndX, lineEndY);
		g.drawLine(revCentreX+1, revCentreY, lineEndX, lineEndY);
		g.setColor(Color.BLACK);
		g.drawLine(revCentreX-2, revCentreY, lineEndX, lineEndY);
		g.drawLine(revCentreX+2, revCentreY, lineEndX, lineEndY);
	}
	
	private void setUpVars(){
		height = this.getHeight();
		width = this.getWidth();
		revCentreX = width/2;
		revCentreY = height - 25;
		lineLength = height - 50;
		lineEndX = revCentreX+calcXPoint(pitchRadians);
		lineEndY = revCentreY-calcYPoint(pitchRadians);
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
