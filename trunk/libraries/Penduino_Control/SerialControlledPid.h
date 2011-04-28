/* 
 * File:   SerialControlledPid.h
 * Author: gee7
 *
 * Created on 17 December 2010, 19:33
 */

#ifndef _SERIALCONTROLLEDPID_H
#define	_SERIALCONTROLLEDPID_H

class SerialControlledPid {
public:
    SerialControlledPid(int pGainAddr, int iGainAddr, int dGainAddr);

    void setPGain(double pGain);
    void setIGain(double iGain);
    void setDGain(double dGain);
    void setMaxOut(double maxOut);
    void setMinOut(double minOut);

    double getPID(double error);
    double getPI(double error);
    double getP(double error);

	double getPGain();
	double getIGain();
	double getDGain();

	void readSerialCommands();

private:
    int _pGainAddr;
    int _iGainAddr;
    int _dGainAddr;
    double _pGain;
    double _iGain;
    double _dGain ;
    double _maxOut;
    double _minOut;
    double _errorSum;
    double _lastError;
    double integral(double setPoint);
    double derivative(double setPoint);
    double trim(double value);
	double processSerial(double valueIn);

};

#endif	/* _SERIALCONTROLLEDPID_H */

