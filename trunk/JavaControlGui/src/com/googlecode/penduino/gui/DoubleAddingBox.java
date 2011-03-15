package com.googlecode.penduino.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.googlecode.penduino.model.PidVarUpdateListener;
import com.googlecode.penduino.model.PidVars;
import com.googlecode.penduino.model.Robot;

@SuppressWarnings("serial")
public class DoubleAddingBox extends JPanel implements ActionListener,
		PidVarUpdateListener {

	private PidVars pidVar;

	private JButton addOne = new JButton("+1");
	private JButton addTenth = new JButton("+0.1");
	private JButton addHundreth = new JButton("+0.01");
	private JButton takeOne = new JButton("-1");
	private JButton takeTenth = new JButton("-0.1");
	private JButton takeHundreth = new JButton("-0.01");

	private JLabel valueBox = new JLabel("0.00");

	Dimension buttonSize = new Dimension(40, 25);
	Insets insets = new Insets(1, 1, 1, 1);
	
	private boolean enabled = false;

	public DoubleAddingBox(PidVars pidVar) {
		this.pidVar = pidVar;
		TitledBorder title;
		title = BorderFactory.createTitledBorder(pidVar.getName());
		this.setBorder(title);

		valueBox.setPreferredSize(new Dimension(55, 30));
		valueBox.setHorizontalAlignment(JLabel.CENTER);

		this.setUpButton(addOne);
		this.setUpButton(addTenth);
		this.setUpButton(addHundreth);
		this.setUpButton(takeOne);
		this.setUpButton(takeTenth);
		this.setUpButton(takeHundreth);

		this.add(takeOne);
		this.add(takeTenth);
		this.add(takeHundreth);

		this.add(valueBox);

		this.add(addOne);
		this.add(addTenth);
		this.add(addHundreth);

		this.setEnabled(false);

		Robot.INSTANCE.addPidVarUpdateListener(this);
	}

	private void setUpButton(JButton button) {
		button.setPreferredSize(buttonSize);
		button.setMargin(insets);
		button.setEnabled(false);
		button.addActionListener(this);
	}

	@Override
	public void setEnabled(boolean bool) {
		addOne.setEnabled(bool);
		addTenth.setEnabled(bool);
		addHundreth.setEnabled(bool);
		takeOne.setEnabled(bool);
		takeTenth.setEnabled(bool);
		takeHundreth.setEnabled(bool);
		enabled = bool;
	}
	
	@Override
	public boolean isEnabled(){
		return enabled;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addOne)) {
			double value = Robot.INSTANCE.getPidVar(pidVar);
			value += 1;
			pidVar.setValue(value);
			Robot.INSTANCE.setPidVar(pidVar);
		} else if (e.getSource().equals(addTenth)) {
			double value = Robot.INSTANCE.getPidVar(pidVar);
			value += 0.1;
			pidVar.setValue(value);
			Robot.INSTANCE.setPidVar(pidVar);
		} else if (e.getSource().equals(addHundreth)) {
			double value = Robot.INSTANCE.getPidVar(pidVar);
			value += 0.01;
			pidVar.setValue(value);
			Robot.INSTANCE.setPidVar(pidVar);
		} else if (e.getSource().equals(takeOne)) {
			double value = Robot.INSTANCE.getPidVar(pidVar);
			value -= 1;
			pidVar.setValue(value);
			Robot.INSTANCE.setPidVar(pidVar);
		} else if (e.getSource().equals(takeTenth)) {
			double value = Robot.INSTANCE.getPidVar(pidVar);
			value -= 0.1;
			pidVar.setValue(value);
			Robot.INSTANCE.setPidVar(pidVar);
		} else if (e.getSource().equals(takeHundreth)) {
			double value = Robot.INSTANCE.getPidVar(pidVar);
			value -= 1;
			pidVar.setValue(value);
			Robot.INSTANCE.setPidVar(pidVar);
		}
	}

	@Override
	public void updatePidVar(PidVars var) {
		if (var.equals(this.pidVar)) {
			if (!this.isEnabled()) {
				this.setEnabled(true);
			}
			valueBox.setText(var.getValue() + "");
			valueBox.repaint();
		}

	}

}
