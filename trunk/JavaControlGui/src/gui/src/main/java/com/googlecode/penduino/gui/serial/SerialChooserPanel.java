package com.googlecode.penduino.gui.serial;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.googlecode.penduino.serial.ArduinoSerialConnectionFactory;
import com.googlecode.penduino.serial.exceptions.PortInUseException;

@SuppressWarnings("serial")
public class SerialChooserPanel extends JPanel implements ActionListener{
	
	private final JComboBox comboBox;
	private final String defaultChoice = "Please Select A Serial Port";

	public SerialChooserPanel(){
		this.setBackground(Color.BLACK);
 
		List<String> names = ArduinoSerialConnectionFactory.getPortNamesAvailable();
		names.add(0, defaultChoice);
		String[] array = new String[0];
		comboBox = new JComboBox(names.toArray(array));
		comboBox.addActionListener(this);
		this.add(comboBox);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String choice = (String)comboBox.getSelectedItem();
		if(!choice.equals(defaultChoice)){
			try {
				ArduinoSerialConnectionFactory.INSTANCE.openDefaultArduinoSerialConnection(choice);
			} catch (PortInUseException e1) {
				JOptionPane.showMessageDialog(null,
						"You are unable to use this port as it is already in use",
						"Port in Use", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
