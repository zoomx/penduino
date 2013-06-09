package com.googlecode.penduino.gui.pid;

import javax.swing.JButton;

import com.googlecode.penduino.core.model.PidChangeValue;
import com.googlecode.penduino.core.model.PidGainVariable;

@SuppressWarnings("serial")
public class PidControlButton extends JButton {
	
	private PidGainVariable pgv;
	private PidChangeValue pcv;

	public PidControlButton(PidGainVariable pgv, PidChangeValue pcv){
		super(pcv.getName());
		this.pcv = pcv;
		this.pgv = pgv;
	}
	
	public PidGainVariable getPidGainVariable(){
		return pgv;
	}
	
	public PidChangeValue getPidChangeValue(){
		return pcv;
	}
	
}
