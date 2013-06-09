package com.googlecode.penduino.core.interfaces;

import com.googlecode.penduino.core.model.PidChangeValue;
import com.googlecode.penduino.core.model.PidGainVariable;

public interface PidGainController {

	public void changePidGain(PidGainVariable pgv, PidChangeValue pcv);
}
