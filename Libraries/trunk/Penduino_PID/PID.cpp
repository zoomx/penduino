/* 
 * File:   PID.cpp
 * Author: gavin
 * 
 * Created on 17 December 2010, 19:33
 */

#include <math.h>
#include <avr/eeprom.h>

#include "PID.h"
#include "WProgram.h"

PID::PID(int pGainAddr, int iGainAddr, int dGainAddr) {
  _pGainAddr = pGainAddr;
  _iGainAddr = iGainAddr;
  _dGainAddr = dGainAddr;
  
  _maxOut = 99999;
  _minOut = -99999;
  
  eeprom_read_block(&_pGain, &_pGainAddr, sizeof(double));
  eeprom_read_block(&_iGain, &_iGainAddr, sizeof(double));
  eeprom_read_block(&_dGain, &_dGainAddr, sizeof(double));

	if(isnan(_pGain)!=0 || isinf(_pGain) != 0){
		_pGain = 0;
	}
	
	if(isnan(_pGain)!=0 || isinf(_pGain) != 0){
		_iGain = 0;
	}
	
	if(isnan(_pGain)!=0 || isinf(_pGain) != 0){
		_dGain  = 0;
	}
  
  _errorSum = 0;
  _lastError = 0;
}

double PID::getP(double error){
    return trim(proportional(error));
}

double PID::getPI(double error){
    double value = proportional(error) + integral(error);
    return trim(value);
}

double PID::getPID(double error){
    double value = proportional(error) + integral(error) + derivative(error);
	return trim(value);
}

double PID::proportional(double error){
	return _pGain * error;
}

double PID::integral(double error){
    //MUST ADD A BOUNDARY FOR ERRORSUMM

    _errorSum+= error;
    return _iGain * _errorSum;
}

double PID::derivative(double error){
    double deriv = error - _lastError;
    _lastError = error;
    return deriv * _dGain;
}

void PID::setPGain(double pGain){
  _pGain = pGain;
 eeprom_write_block(&_pGain, &_pGainAddr, sizeof(double));
  _errorSum = 0;
  _lastError = 0;
}

void PID::setIGain(double iGain){
  _iGain = iGain;
  eeprom_write_block(&_iGain, &_iGainAddr, sizeof(double));
  _errorSum = 0;
  _lastError = 0;
}

void PID::setDGain(double dGain){
  _dGain = dGain;
eeprom_write_block(&_dGain, &_dGainAddr, sizeof(double));
  _errorSum = 0;
  _lastError = 0;
}

void PID::setMaxOut(double maxOut){
  _maxOut = maxOut;
}

void PID::setMinOut(double minOut){
  _minOut = minOut;
}

double PID::trim(double value){
    if(value > _maxOut){
        value = _maxOut;
    } else if(value < _minOut){
        value = _minOut;
    }
    return value;
}

double PID::getPGain(){
	return _pGain;
}

double PID::getIGain(){
	return _iGain;
}

double PID::getDGain(){
	return _dGain;
}

