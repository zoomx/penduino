/* 
 * File:   SerialControlledPid.cpp
 * Author: gee7
 * 
 * Created on 17 December 2010, 19:33
 */

#include <math.h>
#include <avr/eeprom.h>

#include "SerialControlledPid.h"
#include "WProgram.h"

SerialControlledPid::SerialControlledPid(int pGainAddr, int iGainAddr, int dGainAddr) {
  _pGainAddr = pGainAddr;
  _iGainAddr = iGainAddr;
  _dGainAddr = dGainAddr;
  
  _maxOut = 99999;
  _minOut = -99999;
  
  eeprom_read_block(&_pGain, &_pGainAddr, sizeof(double));
  eeprom_read_block(&_iGain, &_iGainAddr, sizeof(double));
  eeprom_read_block(&_dGain, &_dGainAddr, sizeof(double));

	if(isnan(_pGain)==1 || isinf(_pGain)!=0){
		_pGain = 0;
	}
	
	if(isnan(_iGain)==1 || isinf(_iGain)!=0){
		_iGain = 0;
	}
	
	if(isnan(_dGain)==1 || isinf(_dGain)!=0){
		_dGain  = 0;
	}
  
  _errorSum = 0;
  _lastError = 0;
}

double SerialControlledPid::getP(double error){
    return trim(_pGain * error);
}

double SerialControlledPid::getPI(double error){
    double value = getP(error) + integral(error);
    return trim(value);
}

double SerialControlledPid::getPID(double error){
    double value = getP(error) + integral(error) + derivative(error);
	return trim(value);
}

double SerialControlledPid::integral(double error){
    //MUST ADD A BOUNDARY FOR ERRORSUMM

    _errorSum+= error;
    return _iGain * _errorSum;
}

double SerialControlledPid::derivative(double error){
    double deriv = error - _lastError;
    _lastError = error;
    return deriv * _dGain;
}

void SerialControlledPid::setPGain(double pGain){
  _pGain = pGain;
 eeprom_write_block(&_pGain, &_pGainAddr, sizeof(double));
  _errorSum = 0;
  _lastError = 0;
}

void SerialControlledPid::setIGain(double iGain){
  _iGain = iGain;
  eeprom_write_block(&_iGain, &_iGainAddr, sizeof(double));
  _errorSum = 0;
  _lastError = 0;
}

void SerialControlledPid::setDGain(double dGain){
  _dGain = dGain;
eeprom_write_block(&_dGain, &_dGainAddr, sizeof(double));
  _errorSum = 0;
  _lastError = 0;
}

void SerialControlledPid::setMaxOut(double maxOut){
  _maxOut = maxOut;
}

void SerialControlledPid::setMinOut(double minOut){
  _minOut = minOut;
}

double SerialControlledPid::trim(double value){
    if(value > _maxOut){
        value = _maxOut;
    } else if(value < _minOut){
        value = _minOut;
    }
    return value;
}

double SerialControlledPid::getPGain(){
	return _pGain;
}

double SerialControlledPid::getIGain(){
	return _iGain;
}

double SerialControlledPid::getDGain(){
	return _dGain;
}

void SerialControlledPid::readSerialCommands(){
	if(Serial.available() != 0){
		char first = Serial.read();
		double gain = 0.00;
		switch(first){
			case 'P':
 				gain = processSerial(_pGain);
				setPGain(gain);
				break;
			case 'I':
				gain = processSerial(_iGain);
				setIGain(gain);
				break;
			case 'D':
				gain = processSerial(_dGain);
				setDGain(gain);
				break;
			default :;
    		}
	}
}

double SerialControlledPid::processSerial(double valueIn){
	delay(1);
	double total = valueIn;
	if(Serial.available() != 0){
		char value = Serial.read();
		switch(value){
			case '+':
				total += 1;
      				break;
			case '>':
				total += 0.1;
				break;
			case '}':
				total += 0.01;
				break;
			case '-':
				total -= 1;
				break;
			case '<':
				total -= 0.1;
				break;
			case '{':
				total -= 0.01;
				break;
			case 'R':
				total =0;
			default:;
      				//Do nothing;
    			}
	}
	return total;
}

