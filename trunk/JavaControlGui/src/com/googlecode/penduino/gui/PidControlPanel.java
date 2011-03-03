package com.googlecode.penduino.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.googlecode.penduino.model.PidVars;

public class PidControlPanel extends JPanel {

	public PidControlPanel(){
		this.add(new DoubleAddingBox(PidVars.P));
		this.add(new DoubleAddingBox(PidVars.I));
		this.add(new DoubleAddingBox(PidVars.D));
		this.setPreferredSize(new Dimension(370, 220));
		
	}
	
	
}
