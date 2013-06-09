/* 
 * File:   IRDistance.h
 * Author: Gavin Esberger
 *
 * Created on 14 December 2010, 22:03
 */

#ifndef _IRDISTANCE_H
#define	_IRDISTANCE_H

class IRDistance {
public:
    IRDistance(int pin);
    double getDistance(int scans);
    int getMeanVolts(int scans);
    virtual double convertVoltageToCM(int voltage) = 0;
protected:
    int _pin;

};


#endif	/* _IRDISTANCE_H */

