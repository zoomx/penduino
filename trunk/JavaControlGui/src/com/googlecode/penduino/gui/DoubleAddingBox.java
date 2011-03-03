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

import com.googlecode.penduino.model.PidVars;
import com.googlecode.penduino.model.Robot;

@SuppressWarnings("serial")
public class DoubleAddingBox extends JPanel implements ActionListener {

	private PidVars pidVar;

	private double value = 0.00;

	private JButton addOne = new JButton("+1");
	private JButton addTenth = new JButton("+0.1");
	private JButton addHundreth = new JButton("+0.01");
	private JButton takeOne = new JButton("-1");
	private JButton takeTenth = new JButton("-0.1");
	private JButton takeHundreth = new JButton("-0.01");

	private JLabel valueBox = new JLabel("" + value);

	Dimension buttonSize = new Dimension(40, 25);
	Insets insets = new Insets(1, 1, 1, 1);

	public DoubleAddingBox(PidVars pidVar) {
		TitledBorder title;
		title = BorderFactory.createTitledBorder(pidVar.getName());
		this.setBorder(title);

		valueBox.setPreferredSize(new Dimension(55,30));
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
	}

	private void setUpButton(JButton button) {
		button.setPreferredSize(buttonSize);
		button.setMargin(insets);
		button.setEnabled(false);
		button.addActionListener(this);
	}

	public void enable(double value) {
		addOne.setEnabled(true);
		addTenth.setEnabled(true);
		addHundreth.setEnabled(true);
		takeOne.setEnabled(true);
		takeTenth.setEnabled(true);
		takeHundreth.setEnabled(true);
		this.value = value;
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

}
