/* 
 * File:   PID.cpp
 * Author: gavin
 * 
 * Created on 17 December 2010, 19:33
 */

#include "PID.h"

PID::PID() {
    _maxOut = 99999;
    _minOut = -99999;
    pGain = 0;
   iGain = 0;
    dGain = 0;
   errorSum = 0;
   lastError = 0;
}

double PID::getP(double error){
    return trim(pGain * error);
}

double PID::getPI(double error){
    double value = getP(error) + integral(error);
    return trim(value);
}

double PID::getPID(double error){
    double value = getP(error) + integral(error) + derivative(error);
	return trim(value);
}

double PID::integral(double error){
    //MUST ADD A BOUNDARY FOR ERRORSUMM

    errorSum+= error;
    return iGain * errorSum;
}

double PID::derivative(double error){
    double deriv = error - lastError;
    lastError = error;
    return deriv * dGain;
}

void PID::setPGain(double pGain){
this->pGain = pGain;
}

void PID::setIGain(double iGain){
this->iGain = iGain;
}

void PID::setDGain(double dGain){
this->dGain = dGain;
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



