package com.googlecode.penduino.gui.pid;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.googlecode.penduino.core.model.PidGainVariable;

@SuppressWarnings("serial")
public class PidControlPanel extends JPanel {

	public PidControlPanel(){
		this.add(new DoubleAddingBox(PidGainVariable.P));
		this.add(new DoubleAddingBox(PidGainVariable.I));
		this.add(new DoubleAddingBox(PidGainVariable.D));
		this.setPreferredSize(new Dimension(470, 220));
	}
	
	
}
