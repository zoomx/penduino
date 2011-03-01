package com.googlecode.penduino.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.googlecode.penduino.Director;
import com.googlecode.penduino.serial.ArduinoSerialConnection;

@SuppressWarnings("serial")
public class SerialChooserPanel extends JPanel implements ActionListener{
	
	private final JComboBox comboBox;
	private final String defaultChoice = "Please Select A Serial Port";

	public SerialChooserPanel(){
		this.setBackground(Color.BLACK);
 
		List<String> names = ArduinoSerialConnection.getPortNamesAvailable();
		names.add(0, defaultChoice);
		String[] array = new String[0];
		comboBox = new JComboBox(names.toArray(array));
		//comboBox.setSelectedIndex(4);
		comboBox.addActionListener(this);
		this.add(comboBox);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String choice = (String)comboBox.getSelectedItem();
		if(!choice.equals(defaultChoice)){
			Director.INSTANCE.createNewArduinoSerialConnection(choice);
		}
	}
}
