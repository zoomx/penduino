package com.googlecode.penduino.gui.connections;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.googlecode.penduino.core.model.ConnectionInUseException;
import com.googlecode.penduino.core.model.Robot;

@SuppressWarnings("serial")
public class ConnectionChooserPanel extends JPanel implements ActionListener{
	
	private final JComboBox comboBox;
	private final String defaultChoice = "Please Select A Connection";

	public ConnectionChooserPanel(){
		this.setBackground(Color.BLACK);
 
		List<String> names = Robot.getAvailibleConnectionsList();
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
				Robot.INSTANCE.startRobotConnection(choice);
			} catch (ConnectionInUseException e1) {
				JOptionPane.showMessageDialog(null,
						"You are unable to use this connection as it is already in use",
						"Connection in Use", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
}
