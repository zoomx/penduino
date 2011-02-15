/* 
 * File:   IRDistance.cpp
 * Author: Gavin Esberger
 * 
 * Created on 14 December 2010, 22:03
 */

#include "IRDistance.h"
#include "WProgram.h"

IRDistance::IRDistance(int pin){
	_pin = pin;
}

double IRDistance::getDistance(int scans){
	int volts = getMeanVolts(scans);
	return convertVoltageToCM(volts);
}

double IRDistance::convertVoltageToCM(int voltage){
	return 12343.85 * pow(voltage,-1.15);
}

int IRDistance::getMeanVolts(int scans){
	int reading;
	int voltage = 0;
	int min = 2000;
	int max = 0;
	for(int i = 0; i<scans+2; i++){
		reading = analogRead(_pin);
		voltage+= reading;
		min = min(min,reading);
		max = (max,reading);
	}
	return (voltage - min - max)/scans;
}
