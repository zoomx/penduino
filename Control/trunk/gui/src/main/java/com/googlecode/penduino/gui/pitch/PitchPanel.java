package com.googlecode.penduino.gui.pitch;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.googlecode.penduino.core.events.PitchChangedEvent;
import com.googlecode.penduino.core.interfaces.PitchChangeListener;
import com.googlecode.penduino.core.model.Robot;

@SuppressWarnings("serial")
public class PitchPanel extends JPanel implements PitchChangeListener{
	
	JLabel pitch = new JLabel("No data yet");
	PitchRevCounter rev = new PitchRevCounter();
	
	public PitchPanel(){
		Robot.INSTANCE.registerPitchChangeListener(this);
		this.add(pitch);
		pitch.setAlignmentX(CENTER_ALIGNMENT);
		this.add(rev);
		rev.setPreferredSize(new Dimension(300,270));
	}

	@Override
	public void onPitchChangeEvent(PitchChangedEvent event) {
		pitch.setText(event.getPitchAsString());
		rev.setPitchDegrees(event.getPitchAsDouble());
		rev.repaint();
	}

}
