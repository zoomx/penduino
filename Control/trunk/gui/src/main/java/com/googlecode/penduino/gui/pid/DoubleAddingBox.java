package com.googlecode.penduino.gui.pid;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.googlecode.penduino.core.events.PidGainChangedEvent;
import com.googlecode.penduino.core.interfaces.PidTuningListener;
import com.googlecode.penduino.core.model.PidChangeValue;
import com.googlecode.penduino.core.model.PidGainVariable;
import com.googlecode.penduino.core.model.Robot;

@SuppressWarnings("serial")
public class DoubleAddingBox extends JPanel implements ActionListener,
		PidTuningListener {

	private static final Dimension buttonSize = new Dimension(50, 25);
	private static final Insets insets = new Insets(1, 1, 1, 1);
	
	private final PidGainVariable pidVar;

	private JLabel valueBox = new JLabel("0.00");
	private Collection<JButton> buttons = new ArrayList<JButton>();
	private boolean enabled = false;

	public DoubleAddingBox(PidGainVariable pidVar) {
		this.pidVar = pidVar;
		TitledBorder title;
		title = BorderFactory.createTitledBorder(pidVar.getName());
		this.setBorder(title);

		valueBox.setPreferredSize(new Dimension(55, 30));
		valueBox.setHorizontalAlignment(JLabel.CENTER);

		for(PidChangeValue val : PidChangeValue.getMinusValues()){
			PidControlButton button = new PidControlButton(pidVar, val);
			this.setUpButton(button,true);
		}
		
		
		this.add(valueBox);

		for(PidChangeValue val : PidChangeValue.getPlusValues()){
			PidControlButton button = new PidControlButton(pidVar, val);
			this.setUpButton(button,true);
		}
		
		JLabel space = new JLabel("   ");
		this.add(space);
		
		for(PidChangeValue val : PidChangeValue.getSpecialValues()){
			PidControlButton button = new PidControlButton(pidVar, val);
			this.setUpButton(button,false);
		}

		Robot.INSTANCE.registerPidTuningListener(this);
		this.setEnabled(false);
	}

	private void setUpButton(JButton button, boolean resize) {
		if(resize){
			button.setPreferredSize(buttonSize);
		}
		button.setMargin(insets);
		button.addActionListener(this);
		button.setEnabled(false);
		this.add(button);
		buttons.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof PidControlButton){
			PidControlButton button = (PidControlButton)e.getSource();
			Robot.INSTANCE.requestPidGainChange(button.getPidGainVariable(), button.getPidChangeValue());
		}
	}

	@Override
	public void onPidGainChangeEvent(PidGainChangedEvent event) {
		if(!this.enabled){
			this.setEnabled(true);
		}
		if (event.getPidGainVariable().equals(this.pidVar)) {
			valueBox.setText(event.getValueAsString());
			valueBox.repaint();
		}
	}
	
	@Override
	public void setEnabled(boolean isEnabled){
		this.enabled = isEnabled;
		for(JButton button : buttons){
			button.setEnabled(isEnabled);
		}
	}

}
