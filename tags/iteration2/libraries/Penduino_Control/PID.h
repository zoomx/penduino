/* 
 * File:   PID.h
 * Author: gavin
 *
 * Created on 17 December 2010, 19:33
 */

#ifndef _PID_H
#define	_PID_H

class PID {
public:
    PID();

    void setPGain(double pGain);
    void setIGain(double iGain);
    void setDGain(double dGain);
    void setMaxOut(double maxOut);
    void setMinOut(double minOut);

    double getPID(double error);
    double getPI(double error);
    double getP(double error);

private:
    double pGain;
    double iGain;
    double dGain ;
    double _maxOut;
    double _minOut;
    double errorSum;
    double lastError;
    double integral(double setPoint);
    double derivative(double setPoint);
    double trim(double value);

};

#endif	/* _PID_H */
